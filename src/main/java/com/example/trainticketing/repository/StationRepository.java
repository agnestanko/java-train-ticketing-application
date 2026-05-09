package com.example.trainticketing.repository;

import com.example.trainticketing.model.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationRepository {
    private final List<Station> stations = new ArrayList<>();

    public void addStation(Station station) {
        stations.add(station);
    }

    public void removeStation(Station station) {
        stations.remove(station);
    }

    public List<Station> getAllStations() {
        return stations;
    }

    public Optional<Station> findById(String stationId) {
        return stations.stream()
                .filter(station -> station.getStationId().equalsIgnoreCase(stationId))
                .findFirst();
    }

    public Optional<Station> findByName(String name) {
        return stations.stream()
                .filter(station -> station.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}