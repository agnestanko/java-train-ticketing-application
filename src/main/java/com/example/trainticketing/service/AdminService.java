package com.example.trainticketing.service;

import com.example.trainticketing.model.Booking;
import com.example.trainticketing.model.Route;
import com.example.trainticketing.model.Station;
import com.example.trainticketing.model.Train;
import com.example.trainticketing.repository.BookingRepository;
import com.example.trainticketing.repository.RouteRepository;
import com.example.trainticketing.repository.StationRepository;
import com.example.trainticketing.repository.TrainRepository;

import java.util.List;

public class AdminService {
    private final StationRepository stationRepository;
    private final RouteRepository routeRepository;
    private final TrainRepository trainRepository;
    private final BookingRepository bookingRepository;
    private final EmailService emailService;

    public AdminService(
            StationRepository stationRepository,
            RouteRepository routeRepository,
            TrainRepository trainRepository,
            BookingRepository bookingRepository,
            EmailService emailService
    ) {
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
        this.trainRepository = trainRepository;
        this.bookingRepository = bookingRepository;
        this.emailService = emailService;
    }

    public void addStation(Station station) {
        stationRepository.addStation(station);
    }

    public void removeStation(Station station) {
        stationRepository.removeStation(station);
    }

    public List<Station> getAllStations() {
        return stationRepository.getAllStations();
    }

    public void addRoute(Route route) {
        routeRepository.addRoute(route);
    }

    public void removeRoute(Route route) {
        routeRepository.removeRoute(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.getAllRoutes();
    }

    public void addTrain(Train train) {
        trainRepository.addTrain(train);
    }

    public void removeTrain(Train train) {
        trainRepository.removeTrain(train);
    }

    public List<Train> getAllTrains() {
        return trainRepository.getAllTrains();
    }

    public List<Booking> getBookingsForTrain(Train train) {
        return bookingRepository.findBookingsByTrain(train);
    }

    public void reportDelay(Train train, int delayMinutes) {
        if (delayMinutes <= 0) {
            throw new IllegalArgumentException("Delay minutes must be greater than zero.");
        }

        train.setDelayMinutes(delayMinutes);

        List<Booking> bookings = bookingRepository.findBookingsByTrain(train);

        for (Booking booking : bookings) {
            sendDelayNotificationEmail(booking, delayMinutes);
        }
    }

    private void sendDelayNotificationEmail(Booking booking, int delayMinutes) {
        String subject = "Train Delay Notification";

        String body = "Dear " + booking.getCustomer().getName() + ",\n\n"
                + "We inform you that your train is delayed.\n\n"
                + "Booking ID: " + booking.getBookingId() + "\n"
                + "Train: " + booking.getTrain().getTrainId() + " - " + booking.getTrain().getTrainName() + "\n"
                + "Delay: " + delayMinutes + " minutes\n\n"
                + "We apologize for the inconvenience.";

        emailService.sendEmail(booking.getCustomer().getEmail(), subject, body);
    }
}