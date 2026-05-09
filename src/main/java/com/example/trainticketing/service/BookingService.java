package com.example.trainticketing.service;

import com.example.trainticketing.model.Booking;
import com.example.trainticketing.model.Customer;
import com.example.trainticketing.model.Train;
import com.example.trainticketing.repository.BookingRepository;

import java.util.List;
import java.util.UUID;

public class BookingService {
    private final BookingRepository bookingRepository;
    private final EmailService emailService;

    public BookingService(BookingRepository bookingRepository, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.emailService = emailService;
    }

    public Booking bookTickets(Customer customer, Train train, int numberOfTickets) {
        if (numberOfTickets <= 0) {
            throw new IllegalArgumentException("Number of tickets must be greater than zero.");
        }

        int availableSeats = getAvailableSeats(train);

        if (numberOfTickets > availableSeats) {
            throw new IllegalArgumentException(
                    "Booking failed. Only " + availableSeats + " seats are available."
            );
        }

        String bookingId = "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Booking booking = new Booking(bookingId, customer, train, numberOfTickets);
        bookingRepository.addBooking(booking);

        sendBookingConfirmationEmail(booking);

        return booking;
    }

    public int getAvailableSeats(Train train) {
        int bookedSeats = bookingRepository.getBookedSeatsForTrain(train);
        return train.getCapacity() - bookedSeats;
    }

    public List<Booking> getBookingsForTrain(Train train) {
        return bookingRepository.findBookingsByTrain(train);
    }

    private void sendBookingConfirmationEmail(Booking booking) {
        String subject = "Train Ticket Booking Confirmation";

        String body = "Dear " + booking.getCustomer().getName() + ",\n\n"
                + "Your booking has been confirmed.\n\n"
                + "Booking ID: " + booking.getBookingId() + "\n"
                + "Train: " + booking.getTrain().getTrainId() + " - " + booking.getTrain().getTrainName() + "\n"
                + "Route: " + booking.getTrain().getRoute().getRouteName() + "\n"
                + "Schedule: " + booking.getTrain().getSchedule() + "\n"
                + "Tickets: " + booking.getNumberOfTickets() + "\n\n"
                + "Thank you for using our Train Ticketing Application.";

        emailService.sendEmail(booking.getCustomer().getEmail(), subject, body);
    }
}