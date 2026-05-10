package com.example.trainticketing.service;

import com.example.trainticketing.model.JourneyOption;
import com.example.trainticketing.model.Station;
import com.example.trainticketing.model.Train;
import com.example.trainticketing.repository.TrainRepository;

import java.util.ArrayList;
import java.util.List;

import com.example.trainticketing.exception.NoRouteFoundException;

public class RouteService {
    private final TrainRepository trainRepository;

    public RouteService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public List<Train> searchDirectTrains(String departureStation, String arrivalStation) {
        validateStations(departureStation, arrivalStation);
        return trainRepository.findTrainsBetweenStations(departureStation, arrivalStation);
    }

    public List<JourneyOption> searchJourneys(String departureStation, String arrivalStation) {
        validateStations(departureStation, arrivalStation);

        List<JourneyOption> journeyOptions = new ArrayList<>();

        List<Train> directTrains = searchDirectTrains(departureStation, arrivalStation);

        for (Train train : directTrains) {
            journeyOptions.add(new JourneyOption(train, null, null));
        }

        List<Train> allTrains = trainRepository.getAllTrains();

        for (Train firstTrain : allTrains) {
            if (!firstTrain.getRoute().containsStation(departureStation)) {
                continue;
            }

            for (Station possibleChangeoverStation : firstTrain.getRoute().getStations()) {
                String changeoverStationName = possibleChangeoverStation.getName();

                if (changeoverStationName.equalsIgnoreCase(departureStation)
                        || changeoverStationName.equalsIgnoreCase(arrivalStation)) {
                    continue;
                }

                if (!firstTrain.getRoute().hasDirectConnection(departureStation, changeoverStationName)) {
                    continue;
                }

                for (Train secondTrain : allTrains) {
                    if (firstTrain.getTrainId().equalsIgnoreCase(secondTrain.getTrainId())) {
                        continue;
                    }

                    if (secondTrain.getRoute().hasDirectConnection(changeoverStationName, arrivalStation)) {
                        journeyOptions.add(new JourneyOption(firstTrain, secondTrain, changeoverStationName));
                    }
                }
            }
        }

        return journeyOptions;
    }

    public void displaySearchResults(String departureStation, String arrivalStation) {
        List<JourneyOption> journeyOptions = searchJourneys(departureStation, arrivalStation);

        System.out.println("\nSearch results from " + departureStation + " to " + arrivalStation + ":");

        if (journeyOptions.isEmpty()) {
            throw new NoRouteFoundException(
                    "No train route found from " + departureStation + " to " + arrivalStation + "."
            );
        }

        for (JourneyOption journeyOption : journeyOptions) {
            System.out.println(journeyOption);
            System.out.println();
        }
    }

    private void validateStations(String departureStation, String arrivalStation) {
        if (departureStation == null || departureStation.isBlank()) {
            throw new IllegalArgumentException("Departure station cannot be empty.");
        }

        if (arrivalStation == null || arrivalStation.isBlank()) {
            throw new IllegalArgumentException("Arrival station cannot be empty.");
        }

        if (departureStation.equalsIgnoreCase(arrivalStation)) {
            throw new IllegalArgumentException("Departure and arrival stations cannot be the same.");
        }
    }
}