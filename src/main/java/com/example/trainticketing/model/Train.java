package com.example.trainticketing.model;

public class Train {
    private String trainId;
    private String trainName;
    private int capacity;
    private Route route;
    private Schedule schedule;
    private int delayMinutes;

    public Train(String trainId, String trainName, int capacity, Route route, Schedule schedule) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.capacity = capacity;
        this.route = route;
        this.schedule = schedule;
        this.delayMinutes = 0;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public int getDelayMinutes() {
        return delayMinutes;
    }

    public void setDelayMinutes(int delayMinutes) {
        this.delayMinutes = delayMinutes;
    }

    public boolean hasDelay() {
        return delayMinutes > 0;
    }

    @Override
    public String toString() {
        return trainId + " - " + trainName
                + ", capacity: " + capacity
                + ", route: " + route.getRouteName()
                + ", " + schedule
                + ", delay: " + delayMinutes + " minutes";
    }
}