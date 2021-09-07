package com.janko.expertise.DatastoreLoader.model;

public class DatastoreResponse {

    private String serialNumber;
    private Double minimumFuelConsumption;
    private Double averageFuelConsumption;
    private Double maximumFuelConsumption;
    private Double minimumEngineLoad;
    private Double averageEngineLoad;
    private Double maximumEngineLoad;
    private Double totalWorkingHours;
    private Double totalWorkingHoursMadeThatDay;

    public DatastoreResponse() {
    }

    public DatastoreResponse(String serialNumber, Double minimumFuelConsumption, Double averageFuelConsumption,
                             Double maximumFuelConsumption, Double minimumEngineLoad, Double averageEngineLoad,
                             Double maximumEngineLoad, Double totalWorkingHours, Double totalWorkingHoursMadeThatDay) {
        this.serialNumber = serialNumber;
        this.minimumFuelConsumption = minimumFuelConsumption;
        this.averageFuelConsumption = averageFuelConsumption;
        this.maximumFuelConsumption = maximumFuelConsumption;
        this.minimumEngineLoad = minimumEngineLoad;
        this.averageEngineLoad = averageEngineLoad;
        this.maximumEngineLoad = maximumEngineLoad;
        this.totalWorkingHours = totalWorkingHours;
        this.totalWorkingHoursMadeThatDay = totalWorkingHoursMadeThatDay;
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
}
