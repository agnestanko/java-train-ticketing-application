package com.example.trainticketing.model;
import com.example.trainticketing.enums.BookingStatus;
import java.time.LocalDateTime;

public class Booking {
    private String bookingId;
    private Customer customer;
    private Train train;
    private int numberOfTickets;
    private LocalDateTime bookingTime;
    private BookingStatus status;

    public Booking(String bookingId, Customer customer, Train train, int numberOfTickets) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.train = train;
        this.numberOfTickets = numberOfTickets;
        this.bookingTime = LocalDateTime.now();
        this.status = BookingStatus.CONFIRMED;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return train.getTicketPrice() * numberOfTickets;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId
                + ", Customer: " + customer.getName()
                + ", Train: " + train.getTrainId()
                + ", Tickets: " + numberOfTickets
                + ", Total price: " + String.format("%.2f", getTotalPrice()) + " RON"
                + ", Status: " + status
                + ", Time: " + bookingTime;
    }
}