package com.janko.expertise.DatastoreLoader.constants;

import java.util.HashMap;
import java.util.Map;

public class BQQueries {

    public static final String SELECT_DISTINCT_SERIAL_NUMBERS = "SELECT DISTINCT sn FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_ALL = "SELECT * FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_MIN_WORK_HOUR = "SELECT MIN(ttl_work_h) as min FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_MAX_WORK_HOUR = "SELECT MAX(ttl_work_h) as max FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_MIN_ENGINE_LOAD = "SELECT MIN(engn_load) as minimumEngineLoad FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_AVG_ENGINE_LOAD = "SELECT AVG(engn_load) as averageEngineLoad FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_MAX_ENGINE_LOAD = "SELECT MAX(engn_load) as maximumEngineLoad FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_MIN_FUEL_CONSUMPTION = "SELECT MIN(fuel_cnsmptn) as minimumFuelConsumption FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_AVG_FUEL_CONSUMPTION = "SELECT AVG(fuel_cnsmptn) as averageFuelConsumption FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_MAX_FUEL_CONSUMPTION = "SELECT MAX(fuel_cnsmptn) as maximumFuelConsumption FROM claas_telematics_dataset.tlmtcs_tbl";
    public static Map<String, String> queries;

    public static String selectQuery(String constantQuery, String column, String value) {
        return constantQuery + " WHERE " + column + "='" + value + "'";
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    /**
     * Map<String,String> holding Query as the value and short description as a key.
     */
    static {
        queries = new HashMap<>();
        queries.put(DatastoreConstants.MINIMUM_FUEL_CONSUMPTION, SELECT_MIN_FUEL_CONSUMPTION);
        queries.put(DatastoreConstants.AVERAGE_FUEL_CONSUMPTION, SELECT_AVG_FUEL_CONSUMPTION);
        queries.put(DatastoreConstants.MAXIMUM_FUEL_CONSUMPTION, SELECT_MAX_FUEL_CONSUMPTION);
        queries.put(DatastoreConstants.MINIMUM_ENGINE_LOAD, SELECT_MIN_ENGINE_LOAD);
        queries.put(DatastoreConstants.AVERAGE_ENGINE_LOAD, SELECT_AVG_ENGINE_LOAD);
        queries.put(DatastoreConstants.MAXIMUM_ENGINE_LOAD, SELECT_MAX_ENGINE_LOAD);
    }
}
