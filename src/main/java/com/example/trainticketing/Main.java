package com.example.trainticketing;

import com.example.trainticketing.model.Booking;
import com.example.trainticketing.model.Customer;
import com.example.trainticketing.model.Route;
import com.example.trainticketing.model.Schedule;
import com.example.trainticketing.model.Station;
import com.example.trainticketing.model.Train;
import com.example.trainticketing.repository.BookingRepository;
import com.example.trainticketing.repository.RouteRepository;
import com.example.trainticketing.repository.StationRepository;
import com.example.trainticketing.repository.TrainRepository;
import com.example.trainticketing.service.AdminService;
import com.example.trainticketing.service.BookingService;
import com.example.trainticketing.service.ConsoleEmailService;
import com.example.trainticketing.service.EmailService;
import com.example.trainticketing.service.RouteService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Train Ticketing Application started successfully!");

        StationRepository stationRepository = new StationRepository();
        RouteRepository routeRepository = new RouteRepository();
        TrainRepository trainRepository = new TrainRepository();
        BookingRepository bookingRepository = new BookingRepository();

        EmailService emailService = new ConsoleEmailService();

        BookingService bookingService = new BookingService(bookingRepository, emailService);
        RouteService routeService = new RouteService(trainRepository);
        AdminService adminService = new AdminService(
                stationRepository,
                routeRepository,
                trainRepository,
                bookingRepository,
                emailService
        );

        Station berlin = new Station("ST-001", "Berlin", "Berlin");
        Station leipzig = new Station("ST-002", "Leipzig", "Leipzig");
        Station frankfurt = new Station("ST-003", "Frankfurt", "Frankfurt");

        adminService.addStation(berlin);
        adminService.addStation(leipzig);
        adminService.addStation(frankfurt);

        Route route = new Route("RT-001", "Berlin to Frankfurt Route");
        route.addStation(berlin);
        route.addStation(leipzig);
        route.addStation(frankfurt);

        adminService.addRoute(route);

        Schedule schedule = new Schedule(
                LocalDateTime.of(2026, 5, 10, 9, 0),
                LocalDateTime.of(2026, 5, 10, 13, 30)
        );

        Train train = new Train("IC101", "InterCity Express", 100, route, schedule);
        adminService.addTrain(train);

        System.out.println("\n===== ROUTE SEARCH =====");
        routeService.displaySearchResults("Berlin", "Frankfurt");

        Customer customer = new Customer("CU-001", "Alice Brown", "alice@example.com");

        System.out.println("\n===== BOOKING =====");
        Booking booking = bookingService.bookTickets(customer, train, 2);
        System.out.println("Booking created successfully:");
        System.out.println(booking);

        System.out.println("\nAvailable seats after booking: " + bookingService.getAvailableSeats(train));

        System.out.println("\n===== ADMIN: VIEW BOOKINGS =====");
        adminService.getBookingsForTrain(train).forEach(System.out::println);

        System.out.println("\n===== ADMIN: REPORT DELAY =====");
        adminService.reportDelay(train, 30);
        System.out.println("Delay reported successfully.");
    }
}