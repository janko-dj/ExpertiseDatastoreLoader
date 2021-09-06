package com.janko.expertise.DatastoreLoader.loader;

import com.google.cloud.bigquery.*;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.janko.expertise.DatastoreLoader.constants.BQQueries;
import com.janko.expertise.DatastoreLoader.constants.DatastoreConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class BigQueryDataRetriever {

    private final BigQuery bigquery;
    private final Datastore datastore;
    private Map<String, Double> datastoreKeyValueMap;

    @Autowired
    public BigQueryDataRetriever(BigQuery bigquery, Datastore datastore) {
        this.bigquery = bigquery;
        this.datastore = datastore;
        this.datastoreKeyValueMap = new HashMap<>();
    }

    @Scheduled(fixedRateString = "${scheduleTime}")
    public void getDataFromBigQueryAndUploadToDatastore() throws InterruptedException {
        TableResult distinctSNResults = getResult(BQQueries.SELECT_DISTINCT_SERIAL_NUMBERS);
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        for (FieldValueList row : distinctSNResults.iterateAll()) {

            String sn = row.get("sn").getStringValue();
            executorService.submit(() -> {
                try {
                    bigqueryToDatastore(sn);
                } catch (InterruptedException e) {
                    //log
                    e.printStackTrace();
                }
            });
        }
    }

    private void bigqueryToDatastore(String sn) throws InterruptedException {
        //log + timestamp
        TableResult minWorkHour = getResult(BQQueries.selectQuery(BQQueries.SELECT_MIN_WORK_HOUR, "sn", sn));
        TableResult maxWorkHour = getResult(BQQueries.selectQuery(BQQueries.SELECT_MAX_WORK_HOUR, "sn", sn));

        for (String descriptionKey : BQQueries.queries.keySet()) {
            TableResult result = getResult(BQQueries.selectQuery(BQQueries.queries.get(descriptionKey), "sn", sn));
            Double value = getDoubleValue(result, descriptionKey);
            System.out.println(descriptionKey + ": " + value);
            datastoreKeyValueMap.put(descriptionKey, value);
        }
        Double totalWorkingHours = getDoubleValue(maxWorkHour, "max");
        Double totalWorkingHoursMadeThatDay = totalWorkingHours - getDoubleValue(minWorkHour, "min");

        System.out.println("Total Work hour: " + totalWorkingHours);
        System.out.println("Number of Work hours that day: " + totalWorkingHoursMadeThatDay);
        System.out.println(sn);

        Key taskKey = datastore.newKeyFactory().setKind("claas").newKey(sn);

        Entity task = Entity.newBuilder(taskKey)
                .set("totalWorkingHours", totalWorkingHours.toString())
                .set("totalWorkingHoursMadeThatDay", totalWorkingHoursMadeThatDay.toString())
                .set(DatastoreConstants.MINIMUM_ENGINE_LOAD, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_ENGINE_LOAD).toString())
                .set(DatastoreConstants.AVERAGE_ENGINE_LOAD, datastoreKeyValueMap.get(DatastoreConstants.AVERAGE_ENGINE_LOAD).toString())
                .set(DatastoreConstants.MAXIMUM_ENGINE_LOAD, datastoreKeyValueMap.get(DatastoreConstants.MAXIMUM_ENGINE_LOAD).toString())
                .set(DatastoreConstants.MINIMUM_FUEL_CONSUMPTION, datastoreKeyValueMap.get(DatastoreConstants.MINIMUM_FUEL_CONSUMPTION).toString())
                .set(DatastoreConstants.AVERAGE_FUEL_CONSUMPTION, datastoreKeyValueMap.get(DatastoreConstants.AVERAGE_FUEL_CONSUMPTION).toString())
                .set(DatastoreConstants.MAXIMUM_FUEL_CONSUMPTION, datastoreKeyValueMap.get(DatastoreConstants.MAXIMUM_FUEL_CONSUMPTION).toString())
                .build();

        datastore.put(task);
        //log + timestamp
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
