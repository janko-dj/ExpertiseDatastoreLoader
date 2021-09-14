package com.janko.expertise.DatastoreLoader.loader;

import com.google.cloud.bigquery.*;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.janko.expertise.DatastoreLoader.cache.GPSCCache;
import com.janko.expertise.DatastoreLoader.cache.SNCache;
import com.janko.expertise.DatastoreLoader.constants.BQQueries;
import com.janko.expertise.DatastoreLoader.constants.DatastoreConstants;
import com.janko.expertise.DatastoreLoader.model.GPSCoordinate;
import com.janko.expertise.DatastoreLoader.service.ServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class BigQueryDataRetriever {

    private final Logger logger = LoggerFactory.getLogger(BigQueryDataRetriever.class);

    private final BigQuery bigquery;
    private final ServiceInterface datastoreService;
    private final SNCache snCache;
    private final GPSCCache gpscCache;
    private Map<String, Double> datastoreKeyValueMap;


    @Autowired
    public BigQueryDataRetriever(BigQuery bigquery, ServiceInterface datastoreService, SNCache snCache, GPSCCache gpscCache) {
        this.bigquery = bigquery;
        this.datastoreService = datastoreService;
        this.snCache = snCache;
        this.gpscCache = gpscCache;
        this.datastoreKeyValueMap = new HashMap<>();
    }

    //add retryable
    @Scheduled(fixedRateString = "${scheduleTime}")
    public void getDataFromBigQueryAndUploadToDatastore() throws InterruptedException {
        TableResult distinctSNResults = null;
        List<String> serialNumbers = new LinkedList<>();
        try {
            logger.info("Calling BQ with query: {}", BQQueries.SELECT_DISTINCT_SERIAL_NUMBERS);
            distinctSNResults = getResult(BQQueries.SELECT_DISTINCT_SERIAL_NUMBERS);
        } catch (InterruptedException e) {
            logger.error("Error calling: {}", BQQueries.SELECT_DISTINCT_SERIAL_NUMBERS);
            throw new InterruptedException();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        for (FieldValueList row : distinctSNResults.iterateAll()) {

            String sn = row.get("sn").getStringValue();
            serialNumbers.add(sn);
            logger.info("Submitting task to the thread pool!");
            executorService.submit(() -> {
                try {
                    bigqueryToDatastore(sn);
                } catch (InterruptedException e) {
                    logger.error("ERROR executing task, thread interrupted!!!");
                    e.printStackTrace();
                }
            });
        }
        snCache.setSnList(serialNumbers);
    }

    private void bigqueryToDatastore(String sn) throws InterruptedException {
        logger.info("{} started executing the task", Thread.currentThread().getName());

        List<GPSCoordinate> gpsCoordinates = new ArrayList<>();
        Map<String, List<GPSCoordinate>> machineCoordinatesMap = new HashMap<>();

        TableResult minWorkHour = getResult(BQQueries.selectQuery(BQQueries.SELECT_MIN_WORK_HOUR, "sn", sn));
        TableResult maxWorkHour = getResult(BQQueries.selectQuery(BQQueries.SELECT_MAX_WORK_HOUR, "sn", sn));
        TableResult minLongitude = getResult(BQQueries.selectQuery(BQQueries.SELECT_MIN_GPS_LONGITUDE, "sn", sn));
        TableResult maxLongitude = getResult(BQQueries.selectQuery(BQQueries.SELECT_MAX_GPS_LONGITUDE, "sn", sn));
        TableResult minLatitude = getResult(BQQueries.selectQuery(BQQueries.SELECT_MIN_GPS_LATITUDE, "sn", sn));
        TableResult maxLatitude = getResult(BQQueries.selectQuery(BQQueries.SELECT_MAX_GPS_LATITUDE, "sn", sn));

        TableResult gpsCoordinatesResult = getResult(BQQueries.selectQuery(BQQueries.SELECT_COORDINATES, "sn", sn));
        for (FieldValueList row : gpsCoordinatesResult.iterateAll()) {

            gpsCoordinates.add(new GPSCoordinate(Double.valueOf(row.get("gps_lat").getStringValue()),
                    Double.valueOf(row.get("gps_long").getStringValue())));
        }
        gpscCache.addGpsCoordinates(sn, gpsCoordinates);

        for (String descriptionKey : BQQueries.queries.keySet()) {
            TableResult result = getResult(BQQueries.selectQuery(BQQueries.queries.get(descriptionKey), "sn", sn));
            Double value = getDoubleValue(result, descriptionKey);
            datastoreKeyValueMap.put(descriptionKey, value);
        }
        Double totalWorkingHours = getDoubleValue(maxWorkHour, "max");
        Double totalWorkingHoursMadeThatDay = totalWorkingHours - getDoubleValue(minWorkHour, "min");
        Double centerLongitude = (getDoubleValue(maxLongitude, "max") + getDoubleValue(minLongitude, "min")) / 2;
        Double centerLatitude = (getDoubleValue(maxLatitude, "max") + getDoubleValue(minLatitude, "min")) / 2;

        Key taskKey = datastoreService.getTaskKey("claas", sn);

        Entity task = Entity.newBuilder(taskKey)
                .set(DatastoreConstants.TOTAL_WORKING_HOURS, totalWorkingHours.toString())
                .set(DatastoreConstants.TOTAL_WORKING_HOURS_THAT_DAY, totalWorkingHoursMadeThatDay.toString())
                .set(DatastoreConstants.MAP_CENTER_LATITUDE, centerLatitude.toString())
                .set(DatastoreConstants.MAP_CENTER_LONGITUDE, centerLongitude.toString())
                .set(DatastoreConstants.MINIMUM_ENGINE_LOAD, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_ENGINE_LOAD).toString())
                .set(DatastoreConstants.AVERAGE_ENGINE_LOAD, datastoreKeyValueMap.get(DatastoreConstants.AVERAGE_ENGINE_LOAD).toString())
                .set(DatastoreConstants.MAXIMUM_ENGINE_LOAD, datastoreKeyValueMap.get(DatastoreConstants.MAXIMUM_ENGINE_LOAD).toString())
                .set(DatastoreConstants.MINIMUM_FUEL_CONSUMPTION, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_FUEL_CONSUMPTION).toString())
                .set(DatastoreConstants.AVERAGE_FUEL_CONSUMPTION, datastoreKeyValueMap.get(DatastoreConstants.AVERAGE_FUEL_CONSUMPTION).toString())
                .set(DatastoreConstants.MAXIMUM_FUEL_CONSUMPTION, datastoreKeyValueMap.get(DatastoreConstants.MAXIMUM_FUEL_CONSUMPTION).toString())
                .set(DatastoreConstants.MINIMUM_ENGINE_SPEED, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_ENGINE_SPEED).toString())
                .set(DatastoreConstants.AVERAGE_ENGINE_SPEED, datastoreKeyValueMap.get(DatastoreConstants.AVERAGE_ENGINE_SPEED).toString())
                .set(DatastoreConstants.MAXIMUM_ENGINE_SPEED, datastoreKeyValueMap.get(DatastoreConstants.MAXIMUM_ENGINE_SPEED).toString())
                .set(DatastoreConstants.MINIMUM_SPEED_FRONT_PTO, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_SPEED_FRONT_PTO).toString())
                .set(DatastoreConstants.AVERAGE_SPEED_FRONT_PTO, datastoreKeyValueMap.get(DatastoreConstants.AVERAGE_SPEED_FRONT_PTO).toString())
                .set(DatastoreConstants.MAXIMUM_SPEED_FRONT_PTO, datastoreKeyValueMap.get(DatastoreConstants.MAXIMUM_SPEED_FRONT_PTO).toString())
                .set(DatastoreConstants.MINIMUM_SPEED_REAR_PTO, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_SPEED_REAR_PTO).toString())
                .set(DatastoreConstants.AVERAGE_SPEED_REAR_PTO, datastoreKeyValueMap.get(DatastoreConstants.AVERAGE_SPEED_REAR_PTO).toString())
                .set(DatastoreConstants.MAXIMUM_SPEED_REAR_PTO, datastoreKeyValueMap.get(DatastoreConstants.MAXIMUM_SPEED_REAR_PTO).toString())
                .build();

        datastoreService.putEntity(task);
        logger.info("{} finished execution.\n{} uploaded to the Datastore", Thread.currentThread().getName(), task);
    }

    private double getDoubleValue(TableResult result, String key) {
        return result.iterateAll().iterator().next().get(key).getDoubleValue();
    }

    private TableResult getResult(String query) throws InterruptedException {
        QueryJobConfiguration getDistinctSNJob = QueryJobConfiguration.newBuilder(
                query)
                .setUseLegacySql(false)
                .build();
        Job queryDistinctSNJob = bigquery.create(JobInfo.newBuilder(getDistinctSNJob).setJobId(getRandomJobId()).build());
        queryDistinctSNJob = queryDistinctSNJob.waitFor();
        return queryDistinctSNJob.getQueryResults();
    }

    private JobId getRandomJobId() {
        return JobId.of(UUID.randomUUID().toString());
    }
}
