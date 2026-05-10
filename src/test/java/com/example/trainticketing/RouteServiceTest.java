package com.example.trainticketing;

import com.example.trainticketing.model.Route;
import com.example.trainticketing.model.Schedule;
import com.example.trainticketing.model.Station;
import com.example.trainticketing.model.Train;
import com.example.trainticketing.repository.TrainRepository;
import com.example.trainticketing.service.RouteService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RouteServiceTest {

    @Test
    void searchShouldFindDirectTrainRoute() {
        TrainRepository trainRepository = new TrainRepository();
        RouteService routeService = new RouteService(trainRepository);

        Train train = createSampleTrain();
        trainRepository.addTrain(train);

        List<Train> results = routeService.searchDirectTrains("Timisoara Nord", "Bucuresti Nord");

        assertEquals(1, results.size());
        assertEquals("IR1746", results.get(0).getTrainId());
    }

    @Test
    void searchShouldReturnEmptyListWhenNoRouteExists() {
        TrainRepository trainRepository = new TrainRepository();
        RouteService routeService = new RouteService(trainRepository);

        Train train = createSampleTrain();
        trainRepository.addTrain(train);

        List<Train> results = routeService.searchDirectTrains("Bucuresti Nord", "Timisoara Nord");

        assertEquals(0, results.size());
    }

    @Test
    void searchShouldFailWhenStationsAreTheSame() {
        TrainRepository trainRepository = new TrainRepository();
        RouteService routeService = new RouteService(trainRepository);

        assertThrows(
                IllegalArgumentException.class,
                () -> routeService.searchDirectTrains("Timisoara Nord", "Timisoara Nord")
        );
    }

    @Test
    void searchShouldFindJourneyWithChangeover() {
        TrainRepository trainRepository = new TrainRepository();
        RouteService routeService = new RouteService(trainRepository);

        Station oradea = new Station("ST-001", "Oradea", "Oradea");
        Station cluj = new Station("ST-002", "Cluj-Napoca", "Cluj-Napoca");
        Station timisoara = new Station("ST-003", "Timisoara Nord", "Timisoara");

        Route firstRoute = new Route("RT-001", "Oradea to Cluj-Napoca Route");
        firstRoute.addStation(oradea);
        firstRoute.addStation(cluj);

        Route secondRoute = new Route("RT-002", "Cluj-Napoca to Timisoara Nord Route");
        secondRoute.addStation(cluj);
        secondRoute.addStation(timisoara);

        Train firstTrain = new Train(
                "IR1833",
                "InterRegio Oradea - Bucuresti Nord",
                "InterRegio",
                100,
                95.00,
                firstRoute,
                new Schedule(
                        LocalDateTime.of(2026, 5, 10, 8, 30),
                        LocalDateTime.of(2026, 5, 10, 11, 30)
                )
        );

        Train secondTrain = new Train(
                "IR3001",
                "InterRegio Cluj-Napoca - Timisoara Nord",
                "InterRegio",
                90,
                62.00,
                secondRoute,
                new Schedule(
                        LocalDateTime.of(2026, 5, 10, 14, 20),
                        LocalDateTime.of(2026, 5, 10, 19, 10)
                )
        );

        trainRepository.addTrain(firstTrain);
        trainRepository.addTrain(secondTrain);

        var results = routeService.searchJourneys("Oradea", "Timisoara Nord");

        assertEquals(1, results.size());
        assertEquals("Cluj-Napoca", results.get(0).getChangeoverStation());
        assertEquals("IR1833", results.get(0).getFirstTrain().getTrainId());
        assertEquals("IR3001", results.get(0).getSecondTrain().getTrainId());
    }

    private Train createSampleTrain() {
        Station timisoara = new Station("ST-001", "Timisoara Nord", "Timisoara");
        Station arad = new Station("ST-002", "Arad", "Arad");
        Station bucuresti = new Station("ST-003", "Bucuresti Nord", "Bucuresti");

        Route route = new Route("RT-001", "Timisoara Nord to Bucuresti Nord Route");
        route.addStation(timisoara);
        route.addStation(arad);
        route.addStation(bucuresti);

        Schedule schedule = new Schedule(
                LocalDateTime.of(2026, 5, 10, 9, 0),
                LocalDateTime.of(2026, 5, 10, 13, 30)
        );

        return new Train("IR1746", "InterRegio Timisoara Nord - Bucuresti Nord", "InterRegio", 100, 89.50, route, schedule);
    }
}