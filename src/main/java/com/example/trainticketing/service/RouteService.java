package com.example.trainticketing.service;

import com.example.trainticketing.model.Train;
import com.example.trainticketing.repository.TrainRepository;

import java.util.List;

public class RouteService {
    private final TrainRepository trainRepository;

    public RouteService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public List<Train> searchDirectTrains(String departureStation, String arrivalStation) {
        if (departureStation == null || departureStation.isBlank()) {
            throw new IllegalArgumentException("Departure station cannot be empty.");
        }

        if (arrivalStation == null || arrivalStation.isBlank()) {
            throw new IllegalArgumentException("Arrival station cannot be empty.");
        }

        if (departureStation.equalsIgnoreCase(arrivalStation)) {
            throw new IllegalArgumentException("Departure and arrival stations cannot be the same.");
        }

        return trainRepository.findTrainsBetweenStations(departureStation, arrivalStation);
    }

    public void displaySearchResults(String departureStation, String arrivalStation) {
        List<Train> trains = searchDirectTrains(departureStation, arrivalStation);

        System.out.println("\nSearch results from " + departureStation + " to " + arrivalStation + ":");

        if (trains.isEmpty()) {
            System.out.println("No direct train route found.");
            return;
        }

        for (Train train : trains) {
            System.out.println(train);
        }
    }
}