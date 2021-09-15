package com.janko.expertise.DatastoreLoader.constants;

import java.util.HashMap;
import java.util.Map;

public class BQQueries {

    public static final String SELECT_DISTINCT_SERIAL_NUMBERS = "SELECT DISTINCT sn FROM claas_telematics_dataset.tlmtcs_tbl";
    public static final String SELECT_COORDINATES = "SELECT gps_lat,gps_long,date FROM claas_telematics_dataset.tlmtcs_tbl WHERE ";
    public static final String SELECT_MIN_ENGINE_LOAD = "SELECT MIN(engn_load) as minimumEngineLoad FROM claas_telematics_dataset.tlmtcs_tbl WHERE engn_load > 0 AND ";
    public static final String SELECT_MIN_FUEL_CONSUMPTION = "SELECT MIN(fuel_cnsmptn) as minimumFuelConsumption FROM claas_telematics_dataset.tlmtcs_tbl WHERE fuel_cnsmptn > 0 AND ";
    public static final String SELECT_MIN_ENGINE_SPEED = "SELECT MIN(engn_spd) as minimumEngineSpeed FROM claas_telematics_dataset.tlmtcs_tbl WHERE engn_spd > 0 AND ";
    public static final String SELECT_MIN_SPEED_FRONT_PTO = "SELECT MIN(spd_front_pto) as minimumSpeedFrontPTO FROM claas_telematics_dataset.tlmtcs_tbl WHERE spd_front_pto > 0 AND ";
    public static final String SELECT_MIN_SPEED_REAR_PTO = "SELECT MIN(spd_rear_pto) as minimumSpeedRearPTO FROM claas_telematics_dataset.tlmtcs_tbl WHERE spd_rear_pto > 0 AND ";
    public static final String SELECT_NEEDED_VALUES = "SELECT " +
            "min(gps_long)," +
            "max(gps_long)," +
            "min(gps_lat)," +
            "max(gps_lat)," +
            "min(ttl_work_h)," +
            "max(ttl_work_h)," +
            "avg(engn_load)," +
            "max(engn_load)," +
            "avg(fuel_cnsmptn)," +
            "max(fuel_cnsmptn)," +
            "avg(engn_spd)," +
            "max(engn_spd)," +
            "avg(spd_front_pto)," +
            "max(spd_front_pto)," +
            "avg(spd_rear_pto)," +
            "max(spd_rear_pto) " +
            "FROM claas_telematics_dataset.tlmtcs_tbl WHERE ";
    public static Map<String, String> queries;

    public static String selectQuery(String constantQuery, String column, String value, boolean getCoordinates) {
        if (getCoordinates) {
            return constantQuery + column + "='" + value + "'" + " ORDER BY date DESC";
        }
        return constantQuery + column + "='" + value + "'";
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
        queries.put(DatastoreConstants.MINIMUM_ENGINE_LOAD, SELECT_MIN_ENGINE_LOAD);
        queries.put(DatastoreConstants.MINIMUM_ENGINE_SPEED, SELECT_MIN_ENGINE_SPEED);
        queries.put(DatastoreConstants.MINIMUM_SPEED_FRONT_PTO, SELECT_MIN_SPEED_FRONT_PTO);
        queries.put(DatastoreConstants.MINIMUM_SPEED_REAR_PTO, SELECT_MIN_SPEED_REAR_PTO);
    }
}
