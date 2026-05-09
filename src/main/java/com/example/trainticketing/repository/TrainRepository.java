package com.example.trainticketing.repository;

import com.example.trainticketing.model.Train;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainRepository {
    private final List<Train> trains = new ArrayList<>();

    public void addTrain(Train train) {
        trains.add(train);
    }

    public void removeTrain(Train train) {
        trains.remove(train);
    }

    public List<Train> getAllTrains() {
        return trains;
    }

    public Optional<Train> findById(String trainId) {
        return trains.stream()
                .filter(train -> train.getTrainId().equalsIgnoreCase(trainId))
                .findFirst();
    }

    public List<Train> findTrainsBetweenStations(String departureStation, String arrivalStation) {
        return trains.stream()
                .filter(train -> train.getRoute().hasDirectConnection(departureStation, arrivalStation))
                .toList();
    }
}