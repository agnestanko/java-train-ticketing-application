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