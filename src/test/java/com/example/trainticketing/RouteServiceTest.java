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

        List<Train> results = routeService.searchDirectTrains("Berlin", "Frankfurt");

        assertEquals(1, results.size());
        assertEquals("IC101", results.get(0).getTrainId());
    }

    @Test
    void searchShouldReturnEmptyListWhenNoRouteExists() {
        TrainRepository trainRepository = new TrainRepository();
        RouteService routeService = new RouteService(trainRepository);

        Train train = createSampleTrain();
        trainRepository.addTrain(train);

        List<Train> results = routeService.searchDirectTrains("Frankfurt", "Berlin");

        assertEquals(0, results.size());
    }

    @Test
    void searchShouldFailWhenStationsAreTheSame() {
        TrainRepository trainRepository = new TrainRepository();
        RouteService routeService = new RouteService(trainRepository);

        assertThrows(
                IllegalArgumentException.class,
                () -> routeService.searchDirectTrains("Berlin", "Berlin")
        );
    }

    private Train createSampleTrain() {
        Station berlin = new Station("ST-001", "Berlin", "Berlin");
        Station leipzig = new Station("ST-002", "Leipzig", "Leipzig");
        Station frankfurt = new Station("ST-003", "Frankfurt", "Frankfurt");

        Route route = new Route("RT-001", "Berlin to Frankfurt Route");
        route.addStation(berlin);
        route.addStation(leipzig);
        route.addStation(frankfurt);

        Schedule schedule = new Schedule(
                LocalDateTime.of(2026, 5, 10, 9, 0),
                LocalDateTime.of(2026, 5, 10, 13, 30)
        );

        return new Train("IC101", "InterCity Express", 100, route, schedule);
    }
}