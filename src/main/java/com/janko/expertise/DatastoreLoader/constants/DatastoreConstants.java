package com.janko.expertise.DatastoreLoader.constants;

import java.util.ArrayList;
import java.util.List;

public class DatastoreConstants {
    public static final String TOTAL_WORKING_HOURS = "totalWorkingHours";
    public static final String TOTAL_WORKING_HOURS_THAT_DAY = "totalWorkingHoursMadeThatDay";
    public static final String MINIMUM_FUEL_CONSUMPTION = "minimumFuelConsumption";
    public static final String AVERAGE_FUEL_CONSUMPTION = "averageFuelConsumption";
    public static final String MAXIMUM_FUEL_CONSUMPTION = "maximumFuelConsumption";
    public static final String MINIMUM_ENGINE_LOAD = "minimumEngineLoad";
    public static final String AVERAGE_ENGINE_LOAD = "averageEngineLoad";
    public static final String MAXIMUM_ENGINE_LOAD = "maximumEngineLoad";
    public static final String MINIMUM_ENGINE_SPEED = "minimumEngineSpeed";
    public static final String AVERAGE_ENGINE_SPEED = "averageEngineSpeed";
    public static final String MAXIMUM_ENGINE_SPEED = "maximumEngineSpeed";
    public static final String MINIMUM_SPEED_FRONT_PTO = "minimumSpeedFrontPTO";
    public static final String AVERAGE_SPEED_FRONT_PTO = "averageSpeedFrontPTO";
    public static final String MAXIMUM_SPEED_FRONT_PTO = "maximumSpeedFrontPTO";
    public static final String MINIMUM_SPEED_REAR_PTO = "minimumSpeedRearPTO";
    public static final String AVERAGE_SPEED_REAR_PTO = "averageSpeedRearPTO";
    public static final String MAXIMUM_SPEED_REAR_PTO = "maximumSpeedRearPTO";
    public static final String MAP_CENTER_LATITUDE = "mapCenterLongitude";
    public static final String MAP_CENTER_LONGITUDE = "mapCenterLatitude";
    public static final List<String> listOfDatastoreConstants;

    static {
        listOfDatastoreConstants  = new ArrayList<>();
        listOfDatastoreConstants.add(MINIMUM_FUEL_CONSUMPTION);
        listOfDatastoreConstants.add(MINIMUM_ENGINE_LOAD);
        listOfDatastoreConstants.add(MINIMUM_ENGINE_SPEED);
        listOfDatastoreConstants.add(MINIMUM_SPEED_FRONT_PTO);
        listOfDatastoreConstants.add(MINIMUM_SPEED_REAR_PTO);
    }
}
