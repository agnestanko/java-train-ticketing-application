package com.example.trainticketing.repository;

import com.example.trainticketing.model.Booking;
import com.example.trainticketing.model.Train;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    public List<Booking> findBookingsByTrain(Train train) {
        return bookings.stream()
                .filter(booking -> booking.getTrain().getTrainId().equalsIgnoreCase(train.getTrainId()))
                .toList();
    }

    public int getBookedSeatsForTrain(Train train) {
        return findBookingsByTrain(train).stream()
                .mapToInt(Booking::getNumberOfTickets)
                .sum();
    }
}