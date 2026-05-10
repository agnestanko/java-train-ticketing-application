package com.example.trainticketing.service;

import com.example.trainticketing.model.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class BookingFileService {
    private static final String BOOKINGS_FILE_PATH = "data/bookings.json";

    private final ObjectMapper objectMapper;

    public BookingFileService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void saveBooking(Booking booking) {
        try {
            File file = new File(BOOKINGS_FILE_PATH);
            createDataDirectoryIfMissing(file);

            List<Map<String, Object>> bookings = readExistingBookings(file);
            bookings.add(convertBookingToMap(booking));

            objectMapper.writeValue(file, bookings);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to save booking to JSON file: " + exception.getMessage(), exception);
        }
    }

    private void createDataDirectoryIfMissing(File file) {
        File parentDirectory = file.getParentFile();

        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }
    }

    private List<Map<String, Object>> readExistingBookings(File file) throws IOException {
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        return objectMapper.readValue(
                file,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class)
        );
    }

    private Map<String, Object> convertBookingToMap(Booking booking) {
        Map<String, Object> bookingMap = new LinkedHashMap<>();

        bookingMap.put("bookingId", booking.getBookingId());
        bookingMap.put("customerName", booking.getCustomer().getName());
        bookingMap.put("customerEmail", booking.getCustomer().getEmail());
        bookingMap.put("trainId", booking.getTrain().getTrainId());
        bookingMap.put("trainName", booking.getTrain().getTrainName());
        bookingMap.put("trainType", booking.getTrain().getTrainType());
        bookingMap.put("route", booking.getTrain().getRoute().getRouteName());
        bookingMap.put("departureTime", booking.getTrain().getSchedule().getDepartureTime().toString());
        bookingMap.put("arrivalTime", booking.getTrain().getSchedule().getArrivalTime().toString());
        bookingMap.put("numberOfTickets", booking.getNumberOfTickets());
        bookingMap.put("pricePerTicket", booking.getTrain().getTicketPrice());
        bookingMap.put("totalPrice", booking.getTotalPrice());
        bookingMap.put("status", booking.getStatus().toString());
        bookingMap.put("bookingTime", booking.getBookingTime().toString());

        return bookingMap;
    }
}