package com.janko.expertise.DatastoreLoader.model;

import java.util.List;

public class CoordinatesResponse {

    private List<GPSCoordinate> gpsCoordinatesList;

    public CoordinatesResponse(List<GPSCoordinate> gpsCoordinatesList) {
        this.gpsCoordinatesList = gpsCoordinatesList;
    }

    public List<GPSCoordinate> getGpsCoordinatesList() {
        return gpsCoordinatesList;
    }

    public void setGpsCoordinatesList(List<GPSCoordinate> gpsCoordinatesList) {
        this.gpsCoordinatesList = gpsCoordinatesList;
    }

    @Override
    public String toString() {
        return "CoordinatesResponse{" +
                "gpsCoordinatesList=" + gpsCoordinatesList +
                '}';
    }
}
