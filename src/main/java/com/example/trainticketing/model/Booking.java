package com.example.trainticketing.model;

import java.time.LocalDateTime;

public class Booking {
    private String bookingId;
    private Customer customer;
    private Train train;
    private int numberOfTickets;
    private LocalDateTime bookingTime;

    public Booking(String bookingId, Customer customer, Train train, int numberOfTickets) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.train = train;
        this.numberOfTickets = numberOfTickets;
        this.bookingTime = LocalDateTime.now();
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId
                + ", Customer: " + customer.getName()
                + ", Train: " + train.getTrainId()
                + ", Tickets: " + numberOfTickets
                + ", Time: " + bookingTime;
    }
}