package com.example.trainticketing;

import com.example.trainticketing.model.Customer;
import com.example.trainticketing.model.Route;
import com.example.trainticketing.model.Schedule;
import com.example.trainticketing.model.Station;
import com.example.trainticketing.model.Train;
import com.example.trainticketing.repository.BookingRepository;
import com.example.trainticketing.service.BookingService;
import com.example.trainticketing.service.ConsoleEmailService;
import com.example.trainticketing.service.EmailService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookingServiceTest {

    @Test
    void bookingShouldSucceedWhenSeatsAreAvailable() {
        BookingRepository bookingRepository = new BookingRepository();
        EmailService emailService = new ConsoleEmailService();
        BookingService bookingService = new BookingService(bookingRepository, emailService);

        Train train = createSampleTrain();
        Customer customer = new Customer("CU-001", "Alice Brown", "alice@example.com");

        bookingService.bookTickets(customer, train, 2);

        assertEquals(48, bookingService.getAvailableSeats(train));
        assertEquals(1, bookingService.getBookingsForTrain(train).size());
    }

    @Test
    void bookingShouldFailWhenNotEnoughSeatsAreAvailable() {
        BookingRepository bookingRepository = new BookingRepository();
        EmailService emailService = new ConsoleEmailService();
        BookingService bookingService = new BookingService(bookingRepository, emailService);

        Train train = createSampleTrain();
        Customer customer = new Customer("CU-001", "Alice Brown", "alice@example.com");

        assertThrows(
                IllegalArgumentException.class,
                () -> bookingService.bookTickets(customer, train, 60)
        );
    }

    private Train createSampleTrain() {
        Station timisoara = new Station("ST-001", "Timisoara Nord", "Timisoara");
        Station bucuresti = new Station("ST-002", "Bucuresti Nord", "Bucuresti");

        Route route = new Route("RT-001", "Timisoara Nord to Bucuresti Nord Route");
        route.addStation(timisoara);
        route.addStation(bucuresti);

        Schedule schedule = new Schedule(
                LocalDateTime.of(2026, 5, 10, 9, 0),
                LocalDateTime.of(2026, 5, 10, 13, 30)
        );

        return new Train("IR1746", "InterRegio Timisoara Nord - Bucuresti Nord", "InterRegio", 50, 89.50, route, schedule);
    }
}