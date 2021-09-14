package com.janko.expertise.DatastoreLoader.constants;

import java.util.HashMap;
import java.util.Map;

public class BQQueries {

    public static final String SELECT_DISTINCT_SERIAL_NUMBERS = "SELECT DISTINCT sn FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_COORDINATES = "SELECT gps_lat,gps_long,date FROM ";
    public static final String SELECT_MIN_GPS_LONGITUDE = "SELECT MIN(gps_long) as min FROM ";
    public static final String SELECT_MAX_GPS_LONGITUDE = "SELECT MAX(gps_long) as max FROM ";
    public static final String SELECT_MIN_GPS_LATITUDE = "SELECT MIN(gps_lat) as min FROM ";
    public static final String SELECT_MAX_GPS_LATITUDE = "SELECT MAX(gps_lat) as max FROM ";
    public static final String SELECT_MIN_WORK_HOUR = "SELECT MIN(ttl_work_h) as min FROM ";
    public static final String SELECT_MAX_WORK_HOUR = "SELECT MAX(ttl_work_h) as max FROM ";
    public static final String SELECT_MIN_ENGINE_LOAD = "SELECT MIN(engn_load) as minimumEngineLoad FROM ";
    public static final String SELECT_AVG_ENGINE_LOAD = "SELECT AVG(engn_load) as averageEngineLoad FROM ";
    public static final String SELECT_MAX_ENGINE_LOAD = "SELECT MAX(engn_load) as maximumEngineLoad FROM ";
    public static final String SELECT_MIN_FUEL_CONSUMPTION = "SELECT MIN(fuel_cnsmptn) as minimumFuelConsumption FROM ";
    public static final String SELECT_AVG_FUEL_CONSUMPTION = "SELECT AVG(fuel_cnsmptn) as averageFuelConsumption FROM ";
    public static final String SELECT_MAX_FUEL_CONSUMPTION = "SELECT MAX(fuel_cnsmptn) as maximumFuelConsumption FROM ";
    public static final String SELECT_MIN_ENGINE_SPEED = "SELECT MIN(engn_spd) as minimumEngineSpeed FROM ";
    public static final String SELECT_AVG_ENGINE_SPEED = "SELECT AVG(engn_spd) as averageEngineSpeed FROM ";
    public static final String SELECT_MAX_ENGINE_SPEED = "SELECT MAX(engn_spd) as maximumEngineSpeed FROM ";
    public static final String SELECT_MIN_SPEED_FRONT_PTO = "SELECT MIN(spd_front_pto) as minimumSpeedFrontPTO FROM ";
    public static final String SELECT_AVG_SPEED_FRONT_PTO = "SELECT AVG(spd_front_pto) as averageSpeedFrontPTO FROM ";
    public static final String SELECT_MAX_SPEED_FRONT_PTO = "SELECT MAX(spd_front_pto) as maximumSpeedFrontPTO FROM ";
    public static final String SELECT_MIN_SPEED_REAR_PTO = "SELECT MIN(spd_rear_pto) as minimumSpeedRearPTO FROM ";
    public static final String SELECT_AVG_SPEED_REAR_PTO = "SELECT AVG(spd_rear_pto) as averageSpeedRearPTO FROM ";
    public static final String SELECT_MAX_SPEED_REAR_PTO = "SELECT MAX(spd_rear_pto) as maximumSpeedRearPTO FROM ";
    public static Map<String, String> queries;

    public static String selectQuery(String constantQuery, String column, String value) {
        return constantQuery + "(SELECT * FROM claas_telematics_dataset.tlmtcs_tbl_date ORDER BY date DESC)" + " WHERE " + column + "='" + value + "'";
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
        queries.put(DatastoreConstants.MINIMUM_ENGINE_SPEED, SELECT_MIN_ENGINE_SPEED);
        queries.put(DatastoreConstants.AVERAGE_ENGINE_SPEED, SELECT_AVG_ENGINE_SPEED);
        queries.put(DatastoreConstants.MAXIMUM_ENGINE_SPEED, SELECT_MAX_ENGINE_SPEED);
        queries.put(DatastoreConstants.MINIMUM_SPEED_FRONT_PTO, SELECT_MIN_SPEED_FRONT_PTO);
        queries.put(DatastoreConstants.AVERAGE_SPEED_FRONT_PTO, SELECT_AVG_SPEED_FRONT_PTO);
        queries.put(DatastoreConstants.MAXIMUM_SPEED_FRONT_PTO, SELECT_MAX_SPEED_FRONT_PTO);
        queries.put(DatastoreConstants.MINIMUM_SPEED_REAR_PTO, SELECT_MIN_SPEED_REAR_PTO);
        queries.put(DatastoreConstants.AVERAGE_SPEED_REAR_PTO, SELECT_AVG_SPEED_REAR_PTO);
        queries.put(DatastoreConstants.MAXIMUM_SPEED_REAR_PTO, SELECT_MAX_SPEED_REAR_PTO);
    }
}
