package com.example.trainticketing.model;

public class Train {
    private String trainId;
    private String trainName;
    private String trainType;
    private int capacity;
    private double ticketPrice;
    private Route route;
    private Schedule schedule;
    private int delayMinutes;

    public Train(String trainId, String trainName, String trainType, int capacity, double ticketPrice, Route route, Schedule schedule) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.trainType = trainType;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
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

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
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
                + " [" + trainType + "]"
                + ", capacity: " + capacity
                + ", price: " + String.format("%.2f", ticketPrice) + " RON"
                + ", route: " + route.getRouteName()
                + ", " + schedule
                + ", delay: " + delayMinutes + " minutes";
    }
}