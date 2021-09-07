package com.janko.expertise.DatastoreLoader.constants;

import java.util.ArrayList;
import java.util.List;

public class DatastoreConstants {
    public static final String MINIMUM_FUEL_CONSUMPTION = "minimumFuelConsumption";
    public static final String AVERAGE_FUEL_CONSUMPTION = "averageFuelConsumption";
    public static final String MAXIMUM_FUEL_CONSUMPTION = "maximumFuelConsumption";
    public static final String MINIMUM_ENGINE_LOAD = "minimumEngineLoad";
    public static final String AVERAGE_ENGINE_LOAD = "averageEngineLoad";
    public static final String MAXIMUM_ENGINE_LOAD = "maximumEngineLoad";
    public static final String TOTAL_WORKING_HOURS = "totalWorkingHours";
    public static final String TOTAL_WORKING_HOURS_THAT_DAY = "totalWorkingHoursMadeThatDay";
    public static final List<String> listOfDatastoreConstants;

    static {
        listOfDatastoreConstants  = new ArrayList<>();
        listOfDatastoreConstants.add(MINIMUM_FUEL_CONSUMPTION);
        listOfDatastoreConstants.add(AVERAGE_FUEL_CONSUMPTION);
        listOfDatastoreConstants.add(MAXIMUM_FUEL_CONSUMPTION);
        listOfDatastoreConstants.add(MINIMUM_ENGINE_LOAD);
        listOfDatastoreConstants.add(AVERAGE_ENGINE_LOAD);
        listOfDatastoreConstants.add(MAXIMUM_ENGINE_LOAD);
        listOfDatastoreConstants.add(TOTAL_WORKING_HOURS);
        listOfDatastoreConstants.add(TOTAL_WORKING_HOURS_THAT_DAY);
    }
}
