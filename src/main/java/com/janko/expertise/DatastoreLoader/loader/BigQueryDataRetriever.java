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
        logger.info("{} started executing the task for serial number {}", Thread.currentThread().getName(), sn);
        long startTime = System.currentTimeMillis();

        List<GPSCoordinate> gpsCoordinates = new ArrayList<>();

        TableResult gpsCoordinatesResult = getResult(BQQueries.selectQuery(BQQueries.SELECT_COORDINATES, "sn", sn, true));
        for (FieldValueList row : gpsCoordinatesResult.iterateAll()) {

            gpsCoordinates.add(new GPSCoordinate(Double.valueOf(row.get("gps_lat").getStringValue()),
                    Double.valueOf(row.get("gps_long").getStringValue())));
        }
        gpscCache.addGpsCoordinates(sn, gpsCoordinates);

        for (String descriptionKey : BQQueries.queries.keySet()) {
            TableResult result = getResult(BQQueries.selectQuery(BQQueries.queries.get(descriptionKey), "sn", sn, false));
            Double value = getDoubleValue(result, descriptionKey);
            System.out.println(BQQueries.queries.get(descriptionKey));
            System.out.println(descriptionKey + "  " + value);
            datastoreKeyValueMap.put(descriptionKey, value);
        }
        TableResult allResults = getResult(BQQueries.selectQuery(BQQueries.SELECT_NEEDED_VALUES, "sn", sn, false));
        FieldValueList fieldValues = allResults.iterateAll().iterator().next();

        Double totalWorkingHours = fieldValues.get(5).getDoubleValue();
        Double totalWorkingHoursMadeThatDay = totalWorkingHours - fieldValues.get(4).getDoubleValue();
        Double centerLongitude = (fieldValues.get(1).getDoubleValue() + fieldValues.get(0).getDoubleValue()) / 2;
        Double centerLatitude = (fieldValues.get(3).getDoubleValue() + fieldValues.get(2).getDoubleValue()) / 2;

        Key taskKey = datastoreService.getTaskKey("claas", sn);

        Entity task = Entity.newBuilder(taskKey)
                .set(DatastoreConstants.TOTAL_WORKING_HOURS, totalWorkingHours)
                .set(DatastoreConstants.TOTAL_WORKING_HOURS_THAT_DAY, totalWorkingHoursMadeThatDay)
                .set(DatastoreConstants.MAP_CENTER_LATITUDE, centerLatitude)
                .set(DatastoreConstants.MAP_CENTER_LONGITUDE, centerLongitude)
                .set(DatastoreConstants.MINIMUM_ENGINE_LOAD, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_ENGINE_LOAD))
                .set(DatastoreConstants.AVERAGE_ENGINE_LOAD, fieldValues.get(6).getDoubleValue())
                .set(DatastoreConstants.MAXIMUM_ENGINE_LOAD, fieldValues.get(7).getDoubleValue())
                .set(DatastoreConstants.MINIMUM_FUEL_CONSUMPTION, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_FUEL_CONSUMPTION))
                .set(DatastoreConstants.AVERAGE_FUEL_CONSUMPTION, fieldValues.get(8).getDoubleValue())
                .set(DatastoreConstants.MAXIMUM_FUEL_CONSUMPTION, fieldValues.get(9).getDoubleValue())
                .set(DatastoreConstants.MINIMUM_ENGINE_SPEED, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_ENGINE_SPEED))
                .set(DatastoreConstants.AVERAGE_ENGINE_SPEED, fieldValues.get(10).getDoubleValue())
                .set(DatastoreConstants.MAXIMUM_ENGINE_SPEED, fieldValues.get(11).getDoubleValue())
                .set(DatastoreConstants.MINIMUM_SPEED_FRONT_PTO, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_SPEED_FRONT_PTO))
                .set(DatastoreConstants.AVERAGE_SPEED_FRONT_PTO, fieldValues.get(12).getDoubleValue())
                .set(DatastoreConstants.MAXIMUM_SPEED_FRONT_PTO, fieldValues.get(13).getDoubleValue())
                .set(DatastoreConstants.MINIMUM_SPEED_REAR_PTO, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_SPEED_REAR_PTO))
                .set(DatastoreConstants.AVERAGE_SPEED_REAR_PTO, fieldValues.get(14).getDoubleValue())
                .set(DatastoreConstants.MAXIMUM_SPEED_REAR_PTO, fieldValues.get(15).getDoubleValue())
                .build();

        datastoreService.putEntity(task);
        Long methodExecutionTime = System.currentTimeMillis() - startTime;
        logger.info("{} finished execution.\nFinished in {} milliseconds", Thread.currentThread().getName(), methodExecutionTime);
    }

    private double getDoubleValue(TableResult result, String key) {
        return result.iterateAll().iterator().next().get(key).getDoubleValue();
    }

    private TableResult getResult(String query) throws InterruptedException {
        logger.info("Query called: {}", query);
        QueryJobConfiguration getDistinctSNJob = QueryJobConfiguration.newBuilder(
                query)
                .setUseLegacySql(false)
                .build();
        Job queryDistinctSNJob = bigquery.create(JobInfo.newBuilder(getDistinctSNJob).setJobId(getRandomJobId()).build());
        queryDistinctSNJob = queryDistinctSNJob.waitFor();
        if (queryDistinctSNJob == null) {
            logger.error("Job no longer exists");
            throw new RuntimeException("Job no longer exists");
        } else if (queryDistinctSNJob.getStatus().getError() != null) {
            logger.error("Job failed!");
            throw new RuntimeException(queryDistinctSNJob.getStatus().getError().toString());
        }
        return queryDistinctSNJob.getQueryResults();
    }

    private JobId getRandomJobId() {
        return JobId.of(UUID.randomUUID().toString());
    }
}
