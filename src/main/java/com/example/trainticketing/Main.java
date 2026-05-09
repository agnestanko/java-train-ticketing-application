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
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static StationRepository stationRepository;
    private static RouteRepository routeRepository;
    private static TrainRepository trainRepository;
    private static BookingRepository bookingRepository;

    private static BookingService bookingService;
    private static RouteService routeService;
    private static AdminService adminService;

    public static void main(String[] args) {
        initializeApplication();
        showMainMenu();
    }

    private static void initializeApplication() {
        stationRepository = new StationRepository();
        routeRepository = new RouteRepository();
        trainRepository = new TrainRepository();
        bookingRepository = new BookingRepository();

        EmailService emailService = new ConsoleEmailService();

        bookingService = new BookingService(bookingRepository, emailService);
        routeService = new RouteService(trainRepository);
        adminService = new AdminService(
                stationRepository,
                routeRepository,
                trainRepository,
                bookingRepository,
                emailService
        );

        loadSampleData();

        System.out.println("Train Ticketing Application started successfully!");
    }

    private static void loadSampleData() {
        Station berlin = new Station("ST-001", "Berlin", "Berlin");
        Station leipzig = new Station("ST-002", "Leipzig", "Leipzig");
        Station frankfurt = new Station("ST-003", "Frankfurt", "Frankfurt");
        Station munich = new Station("ST-004", "Munich", "Munich");
        Station hamburg = new Station("ST-005", "Hamburg", "Hamburg");

        adminService.addStation(berlin);
        adminService.addStation(leipzig);
        adminService.addStation(frankfurt);
        adminService.addStation(munich);
        adminService.addStation(hamburg);

        Route routeOne = new Route("RT-001", "Berlin to Frankfurt Route");
        routeOne.addStation(berlin);
        routeOne.addStation(leipzig);
        routeOne.addStation(frankfurt);

        Route routeTwo = new Route("RT-002", "Hamburg to Munich Route");
        routeTwo.addStation(hamburg);
        routeTwo.addStation(berlin);
        routeTwo.addStation(munich);

        adminService.addRoute(routeOne);
        adminService.addRoute(routeTwo);

        Train trainOne = new Train(
                "IC101",
                "InterCity Express",
                100,
                routeOne,
                new Schedule(
                        LocalDateTime.of(2026, 5, 10, 9, 0),
                        LocalDateTime.of(2026, 5, 10, 13, 30)
                )
        );

        Train trainTwo = new Train(
                "ICE202",
                "High Speed Express",
                80,
                routeTwo,
                new Schedule(
                        LocalDateTime.of(2026, 5, 10, 10, 0),
                        LocalDateTime.of(2026, 5, 10, 15, 45)
                )
        );

        adminService.addTrain(trainOne);
        adminService.addTrain(trainTwo);
    }

    private static void showMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== TRAIN TICKETING SYSTEM =====");
            System.out.println("1. Show all stations");
            System.out.println("2. Search train route");
            System.out.println("3. Book ticket");
            System.out.println("4. View bookings for train");
            System.out.println("5. Report train delay");
            System.out.println("6. Show all trains");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showAllStations();
                case "2" -> searchTrainRoute();
                case "3" -> bookTicket();
                case "4" -> viewBookingsForTrain();
                case "5" -> reportTrainDelay();
                case "6" -> showAllTrains();
                case "7" -> {
                    System.out.println("Thank you for using the Train Ticketing Application.");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please choose a number from 1 to 7.");
            }
        }
    }

    private static void showAllStations() {
        System.out.println("\n===== AVAILABLE STATIONS =====");
        adminService.getAllStations().forEach(System.out::println);
    }

    private static void searchTrainRoute() {
        System.out.println("\n===== SEARCH TRAIN ROUTE =====");

        System.out.print("Enter departure station: ");
        String departureStation = scanner.nextLine();

        System.out.print("Enter arrival station: ");
        String arrivalStation = scanner.nextLine();

        try {
            routeService.displaySearchResults(departureStation, arrivalStation);
        } catch (IllegalArgumentException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void bookTicket() {
        System.out.println("\n===== BOOK TICKET =====");

        System.out.print("Enter train ID: ");
        String trainId = scanner.nextLine();

        Train train = trainRepository.findById(trainId).orElse(null);

        if (train == null) {
            System.out.println("Train not found.");
            return;
        }

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine();

        System.out.print("Enter number of tickets: ");
        int numberOfTickets = readIntegerInput();

        Customer customer = new Customer(
                "CU-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase(),
                customerName,
                customerEmail
        );

        try {
            Booking booking = bookingService.bookTickets(customer, train, numberOfTickets);
            System.out.println("Booking created successfully:");
            System.out.println(booking);
            System.out.println("Available seats after booking: " + bookingService.getAvailableSeats(train));
        } catch (IllegalArgumentException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void viewBookingsForTrain() {
        System.out.println("\n===== VIEW BOOKINGS FOR TRAIN =====");

        System.out.print("Enter train ID: ");
        String trainId = scanner.nextLine();

        Train train = trainRepository.findById(trainId).orElse(null);

        if (train == null) {
            System.out.println("Train not found.");
            return;
        }

        List<Booking> bookings = adminService.getBookingsForTrain(train);

        if (bookings.isEmpty()) {
            System.out.println("No bookings found for this train.");
            return;
        }

        bookings.forEach(System.out::println);
    }

    private static void reportTrainDelay() {
        System.out.println("\n===== REPORT TRAIN DELAY =====");

        System.out.print("Enter train ID: ");
        String trainId = scanner.nextLine();

        Train train = trainRepository.findById(trainId).orElse(null);

        if (train == null) {
            System.out.println("Train not found.");
            return;
        }

        System.out.print("Enter delay in minutes: ");
        int delayMinutes = readIntegerInput();

        try {
            adminService.reportDelay(train, delayMinutes);
            System.out.println("Delay reported successfully.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void showAllTrains() {
        System.out.println("\n===== AVAILABLE TRAINS =====");
        adminService.getAllTrains().forEach(train -> {
            System.out.println(train);
            System.out.println("Available seats: " + bookingService.getAvailableSeats(train));
        });
    }

    private static int readIntegerInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());

                if (value < 0) {
                    System.out.print("Please enter a positive number: ");
                    continue;
                }

                return value;
            } catch (NumberFormatException exception) {
                System.out.print("Invalid number. Please enter again: ");
            }
        }
    }
}