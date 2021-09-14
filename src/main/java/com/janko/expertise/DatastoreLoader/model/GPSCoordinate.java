package com.janko.expertise.DatastoreLoader.model;

public class GPSCoordinate {
    private Double lat;
    private Double lng;

    public GPSCoordinate() {
    }

    public GPSCoordinate(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "GPSCoordinate{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
