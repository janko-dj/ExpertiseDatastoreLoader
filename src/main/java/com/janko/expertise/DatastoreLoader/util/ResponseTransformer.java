package com.janko.expertise.DatastoreLoader.util;

import com.google.cloud.datastore.Entity;
import com.janko.expertise.DatastoreLoader.constants.DatastoreConstants;
import com.janko.expertise.DatastoreLoader.model.DatastoreResponse;

public class ResponseTransformer {

    public static DatastoreResponse transformResponse(Entity entity, String sn) {
        DatastoreResponse datastoreResponse = new DatastoreResponse();
            datastoreResponse.setSerialNumber(sn);
            datastoreResponse.setMinimumFuelConsumption(Double
                    .valueOf(entity.getString(DatastoreConstants.MINIMUM_FUEL_CONSUMPTION)));
            datastoreResponse.setAverageFuelConsumption(Double
                    .valueOf(entity.getString(DatastoreConstants.AVERAGE_FUEL_CONSUMPTION)));
            datastoreResponse.setMaximumFuelConsumption(Double
                    .valueOf(entity.getString(DatastoreConstants.MAXIMUM_FUEL_CONSUMPTION)));
            datastoreResponse.setMinimumEngineLoad(Double
                    .valueOf(entity.getString(DatastoreConstants.MINIMUM_ENGINE_LOAD)));
            datastoreResponse.setAverageEngineLoad(Double
                    .valueOf(entity.getString(DatastoreConstants.AVERAGE_ENGINE_LOAD)));
            datastoreResponse.setMaximumEngineLoad(Double
                    .valueOf(entity.getString(DatastoreConstants.MAXIMUM_ENGINE_LOAD)));
            datastoreResponse.setTotalWorkingHours(Double
                    .valueOf(entity.getString(DatastoreConstants.TOTAL_WORKING_HOURS)));
            datastoreResponse.setTotalWorkingHoursMadeThatDay(Double
                    .valueOf(entity.getString(DatastoreConstants.TOTAL_WORKING_HOURS_THAT_DAY)));
        return datastoreResponse;
    }
}
