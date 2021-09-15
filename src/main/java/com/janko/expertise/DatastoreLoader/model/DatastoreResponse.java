package com.janko.expertise.DatastoreLoader.model;

import java.util.Map;

public class DatastoreResponse {

    private String serialNumber;
    private Double centerLatitude;
    private Double centerLongitude;
    private Double minimumFuelConsumption;
    private Double averageFuelConsumption;
    private Double maximumFuelConsumption;
    private Double minimumEngineLoad;
    private Double averageEngineLoad;
    private Double maximumEngineLoad;
    private Double minimumEngineSpeed;
    private Double averageEngineSpeed;
    private Double maximumEngineSpeed;
    private Double totalWorkingHours;
    private Double totalWorkingHoursMadeThatDay;

    public DatastoreResponse() {
    }

    public DatastoreResponse(String serialNumber, Double centerLatitude, Double centerLongitude, Double minimumFuelConsumption, Double averageFuelConsumption, Double maximumFuelConsumption, Double minimumEngineLoad, Double averageEngineLoad, Double maximumEngineLoad, Double minimumEngineSpeed, Double averageEngineSpeed, Double maximumEngineSpeed, Double totalWorkingHours, Double totalWorkingHoursMadeThatDay) {
        this.serialNumber = serialNumber;
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.minimumFuelConsumption = minimumFuelConsumption;
        this.averageFuelConsumption = averageFuelConsumption;
        this.maximumFuelConsumption = maximumFuelConsumption;
        this.minimumEngineLoad = minimumEngineLoad;
        this.averageEngineLoad = averageEngineLoad;
        this.maximumEngineLoad = maximumEngineLoad;
        this.minimumEngineSpeed = minimumEngineSpeed;
        this.averageEngineSpeed = averageEngineSpeed;
        this.maximumEngineSpeed = maximumEngineSpeed;
        this.totalWorkingHours = totalWorkingHours;
        this.totalWorkingHoursMadeThatDay = totalWorkingHoursMadeThatDay;
    }

    public Double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(Double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public Double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(Double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Double getMinimumFuelConsumption() {
        return minimumFuelConsumption;
    }

    public void setMinimumFuelConsumption(Double minimumFuelConsumption) {
        this.minimumFuelConsumption = minimumFuelConsumption;
    }

    public Double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(Double averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public Double getMaximumFuelConsumption() {
        return maximumFuelConsumption;
    }

    public void setMaximumFuelConsumption(Double maximumFuelConsumption) {
        this.maximumFuelConsumption = maximumFuelConsumption;
    }

    public Double getMinimumEngineLoad() {
        return minimumEngineLoad;
    }

    public void setMinimumEngineLoad(Double minimumEngineLoad) {
        this.minimumEngineLoad = minimumEngineLoad;
    }

    public Double getAverageEngineLoad() {
        return averageEngineLoad;
    }

    public void setAverageEngineLoad(Double averageEngineLoad) {
        this.averageEngineLoad = averageEngineLoad;
    }

    public Double getMaximumEngineLoad() {
        return maximumEngineLoad;
    }

    public void setMaximumEngineLoad(Double maximumEngineLoad) {
        this.maximumEngineLoad = maximumEngineLoad;
    }

    public Double getMinimumEngineSpeed() {
        return minimumEngineSpeed;
    }

    public void setMinimumEngineSpeed(Double minimumEngineSpeed) {
        this.minimumEngineSpeed = minimumEngineSpeed;
    }

    public Double getAverageEngineSpeed() {
        return averageEngineSpeed;
    }

    public void setAverageEngineSpeed(Double averageEngineSpeed) {
        this.averageEngineSpeed = averageEngineSpeed;
    }

    public Double getMaximumEngineSpeed() {
        return maximumEngineSpeed;
    }

    public void setMaximumEngineSpeed(Double maximumEngineSpeed) {
        this.maximumEngineSpeed = maximumEngineSpeed;
    }

    public Double getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(Double totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public Double getTotalWorkingHoursMadeThatDay() {
        return totalWorkingHoursMadeThatDay;
    }

    public void setTotalWorkingHoursMadeThatDay(Double totalWorkingHoursMadeThatDay) {
        this.totalWorkingHoursMadeThatDay = totalWorkingHoursMadeThatDay;
    }

    @Override
    public String toString() {
        return "DatastoreResponse{" +
                "serialNumber='" + serialNumber + '\'' +
                ", minimumFuelConsumption=" + minimumFuelConsumption +
                ", averageFuelConsumption=" + averageFuelConsumption +
                ", maximumFuelConsumption=" + maximumFuelConsumption +
                ", minimumEngineLoad=" + minimumEngineLoad +
                ", averageEngineLoad=" + averageEngineLoad +
                ", maximumEngineLoad=" + maximumEngineLoad +
                ", totalWorkingHours=" + totalWorkingHours +
                ", totalWorkingHoursMadeThatDay=" + totalWorkingHoursMadeThatDay +
                '}';
    }
}
