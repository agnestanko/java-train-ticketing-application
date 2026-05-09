package com.example.trainticketing.model;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private String routeId;
    private String routeName;
    private List<Station> stations;

    public Route(String routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.stations = new ArrayList<>();
    }

    public Route(String routeId, String routeName, List<Station> stations) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.stations = stations;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public void removeStation(Station station) {
        stations.remove(station);
    }

    public boolean containsStation(String stationName) {
        return stations.stream()
                .anyMatch(station -> station.getName().equalsIgnoreCase(stationName));
    }

    public int getStationIndex(String stationName) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equalsIgnoreCase(stationName)) {
                return i;
            }
        }
        return -1;
    }

    public boolean hasDirectConnection(String departureStation, String arrivalStation) {
        int departureIndex = getStationIndex(departureStation);
        int arrivalIndex = getStationIndex(arrivalStation);

        return departureIndex != -1
                && arrivalIndex != -1
                && departureIndex < arrivalIndex;
    }

    @Override
    public String toString() {
        return routeName + " " + stations;
    }
}