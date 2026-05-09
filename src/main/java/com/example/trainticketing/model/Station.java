package com.example.trainticketing.model;

public class Station {
    private String stationId;
    private String name;
    private String city;

    public Station(String stationId, String name, String city) {
        this.stationId = stationId;
        this.name = name;
        this.city = city;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return name + " (" + city + ")";
    }
}