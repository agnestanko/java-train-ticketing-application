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
        Station timisoara = new Station("ST-001", "Timisoara Nord", "Timisoara");
        Station arad = new Station("ST-002", "Arad", "Arad");
        Station oradea = new Station("ST-003", "Oradea", "Oradea");
        Station cluj = new Station("ST-004", "Cluj-Napoca", "Cluj-Napoca");
        Station brasov = new Station("ST-005", "Brasov", "Brasov");
        Station bucuresti = new Station("ST-006", "Bucuresti Nord", "Bucuresti");

        adminService.addStation(timisoara);
        adminService.addStation(arad);
        adminService.addStation(oradea);
        adminService.addStation(cluj);
        adminService.addStation(brasov);
        adminService.addStation(bucuresti);

        Route routeOne = new Route("RT-001", "Timisoara Nord to Bucuresti Nord Route");
        routeOne.addStation(timisoara);
        routeOne.addStation(arad);
        routeOne.addStation(cluj);
        routeOne.addStation(brasov);
        routeOne.addStation(bucuresti);

        Route routeTwo = new Route("RT-002", "Oradea to Bucuresti Nord Route");
        routeTwo.addStation(oradea);
        routeTwo.addStation(cluj);
        routeTwo.addStation(brasov);
        routeTwo.addStation(bucuresti);

        Route routeThree = new Route("RT-003", "Arad to Timisoara Nord Route");
        routeThree.addStation(arad);
        routeThree.addStation(timisoara);

        adminService.addRoute(routeOne);
        adminService.addRoute(routeTwo);
        adminService.addRoute(routeThree);

        Train trainOne = new Train(
                "IR1746",
                "InterRegio Timisoara Nord - Bucuresti Nord",
                "InterRegio",
                120,
                89.50,
                routeOne,
                new Schedule(
                        LocalDateTime.of(2026, 5, 10, 7, 15),
                        LocalDateTime.of(2026, 5, 10, 17, 45)
                )
        );

        Train trainTwo = new Train(
                "IR1833",
                "InterRegio Oradea - Bucuresti Nord",
                "InterRegio",
                100,
                95.00,
                routeTwo,
                new Schedule(
                        LocalDateTime.of(2026, 5, 10, 8, 30),
                        LocalDateTime.of(2026, 5, 10, 18, 20)
                )
        );

        Train trainThree = new Train(
                "R2602",
                "Regio Arad - Timisoara Nord",
                "Regio",
                80,
                18.50,
                routeThree,
                new Schedule(
                        LocalDateTime.of(2026, 5, 10, 6, 40),
                        LocalDateTime.of(2026, 5, 10, 7, 55)
                )
        );

        adminService.addTrain(trainOne);
        adminService.addTrain(trainTwo);
        adminService.addTrain(trainThree);
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