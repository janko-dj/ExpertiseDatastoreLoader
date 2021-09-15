package com.janko.expertise.DatastoreLoader.util;

import com.google.cloud.datastore.Entity;
import com.janko.expertise.DatastoreLoader.constants.DatastoreConstants;
import com.janko.expertise.DatastoreLoader.model.DatastoreResponse;

public class ResponseTransformer {

    public static DatastoreResponse transformResponse(Entity entity, String sn) {
        DatastoreResponse datastoreResponse = new DatastoreResponse();
        datastoreResponse.setCenterLatitude(entity.getDouble(DatastoreConstants.MAP_CENTER_LATITUDE));
        datastoreResponse.setCenterLongitude(entity.getDouble(DatastoreConstants.MAP_CENTER_LONGITUDE));
        datastoreResponse.setSerialNumber(sn);
        datastoreResponse.setMinimumFuelConsumption(entity.getDouble(DatastoreConstants.MINIMUM_FUEL_CONSUMPTION));
        datastoreResponse.setAverageFuelConsumption(entity.getDouble(DatastoreConstants.AVERAGE_FUEL_CONSUMPTION));
        datastoreResponse.setMaximumFuelConsumption(entity.getDouble(DatastoreConstants.MAXIMUM_FUEL_CONSUMPTION));
        datastoreResponse.setMinimumEngineLoad(entity.getDouble(DatastoreConstants.MINIMUM_ENGINE_LOAD));
        datastoreResponse.setAverageEngineLoad(entity.getDouble(DatastoreConstants.AVERAGE_ENGINE_LOAD));
        datastoreResponse.setMaximumEngineLoad(entity.getDouble(DatastoreConstants.MAXIMUM_ENGINE_LOAD));
        datastoreResponse.setMinimumEngineSpeed(entity.getDouble(DatastoreConstants.MINIMUM_ENGINE_SPEED));
        datastoreResponse.setAverageEngineSpeed(entity.getDouble(DatastoreConstants.AVERAGE_ENGINE_SPEED));
        datastoreResponse.setMaximumEngineSpeed(entity.getDouble(DatastoreConstants.MAXIMUM_ENGINE_SPEED));
        datastoreResponse.setTotalWorkingHours(entity.getDouble(DatastoreConstants.TOTAL_WORKING_HOURS));
        datastoreResponse.setTotalWorkingHoursMadeThatDay(entity.getDouble(DatastoreConstants.TOTAL_WORKING_HOURS_THAT_DAY));
        return datastoreResponse;
    }
}
