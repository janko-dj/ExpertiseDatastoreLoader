package com.janko.expertise.DatastoreLoader.cache;

import com.janko.expertise.DatastoreLoader.model.GPSCoordinate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GPSCCache {

    private final Map<String, List<GPSCoordinate>> gpsCoordinates;

    public GPSCCache() {
        this.gpsCoordinates = new HashMap<>();
    }

    public GPSCCache(Map<String, List<GPSCoordinate>> gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }

    public Map<String, List<GPSCoordinate>> getGpsCoordinates() {
        return gpsCoordinates;
    }

    public void addGpsCoordinates(String sn, List<GPSCoordinate> gpsCoordinates) {
        this.gpsCoordinates.put(sn, gpsCoordinates);
    }

    @Override
    public String toString() {
        return "GPSCCache{" +
                "gpsCoordinates=" + gpsCoordinates +
                '}';
    }
}
