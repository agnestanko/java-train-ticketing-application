# Java Train Ticketing Application

## Project Description

This project is a Java console application for searching train routes, booking train tickets, managing train data, and notifying customers about bookings and delays.

The application uses predefined train stations, routes, schedules, and trains. Customers can search for available routes, book one or multiple tickets, and receive a simulated email confirmation. Administrators can view bookings and report train delays. When a delay is reported, booked customers are notified through a simulated email message in the console.

## Main Features

- Search train routes between two stations
- Display available trains and schedules
- Book one or multiple tickets
- Prevent overbooking by checking available seats
- Send simulated booking confirmation emails
- View bookings for a selected train
- Report train delays
- Send simulated delay notification emails
- Use predefined sample stations, routes, trains, and schedules
- Include unit tests for booking and route search functionality

## Technologies Used

- Java 17 or newer
- Maven
- JUnit 5
- Git
- GitHub
- IntelliJ IDEA

## Project Structure

```text
src/
├── main/
│   └── java/
│       └── com/example/trainticketing/
│           ├── Main.java
│           ├── model/
│           │   ├── Booking.java
│           │   ├── Customer.java
│           │   ├── Route.java
│           │   ├── Schedule.java
│           │   ├── Station.java
│           │   └── Train.java
│           ├── repository/
│           │   ├── BookingRepository.java
│           │   ├── RouteRepository.java
│           │   ├── StationRepository.java
│           │   └── TrainRepository.java
│           └── service/
│               ├── AdminService.java
│               ├── BookingService.java
│               ├── ConsoleEmailService.java
│               ├── EmailService.java
│               └── RouteService.java
└── test/
    └── java/
        └── com/example/trainticketing/
            ├── BookingServiceTest.java
            └── RouteServiceTest.java
Sample Data

The application starts with predefined stations:

Berlin
Leipzig
Frankfurt
Munich
Hamburg

Predefined routes:

Berlin → Leipzig → Frankfurt
Hamburg → Berlin → Munich

Predefined trains:

IC101 - InterCity Express
ICE202 - High Speed Express
How to Run the Project

Clone the repository:

git clone https://github.com/agnestanko/java-train-ticketing-application.git

Open the project in IntelliJ IDEA.

Run:

src/main/java/com/example/trainticketing/Main.java

Alternatively, if Maven is installed, run:

mvn clean test
mvn exec:java
Console Menu

When the application starts, the user can choose from the following menu:

===== TRAIN TICKETING SYSTEM =====
1. Show all stations
2. Search train route
3. Book ticket
4. View bookings for train
5. Report train delay
6. Show all trains
7. Exit
Example Input and Output
Show All Stations

Input:

1

Output:

===== AVAILABLE STATIONS =====
Berlin (Berlin)
Leipzig (Leipzig)
Frankfurt (Frankfurt)
Munich (Munich)
Hamburg (Hamburg)
Search Train Route

Input:

2
Berlin
Frankfurt

Output:

Search results from Berlin to Frankfurt:
IC101 - InterCity Express, capacity: 100, route: Berlin to Frankfurt Route, Departure: 2026-05-10 09:00, Arrival: 2026-05-10 13:30, delay: 0 minutes
Book Ticket

Input:

3
IC101
Alice Brown
alice@example.com
2

Output:

===== EMAIL NOTIFICATION =====
To: alice@example.com
Subject: Train Ticket Booking Confirmation
Message:
Dear Alice Brown,

Your booking has been confirmed.

Train: IC101 - InterCity Express
Tickets: 2

Thank you for using our Train Ticketing Application.
==============================

Booking created successfully.
Available seats after booking: 98
View Bookings

Input:

4
IC101

Output:

Booking ID: BK-12345678, Customer: Alice Brown, Train: IC101, Tickets: 2
Report Delay

Input:

5
IC101
20

Output:

===== EMAIL NOTIFICATION =====
To: alice@example.com
Subject: Train Delay Notification
Message:
Dear Alice Brown,

We inform you that your train is delayed.

Train: IC101 - InterCity Express
Delay: 20 minutes

We apologize for the inconvenience.
==============================

Delay reported successfully.
```

### Testing


The project includes JUnit tests for important functionality.

Tested features:

Booking succeeds when seats are available
Booking fails when there are not enough seats
Route search finds a direct route
Route search returns empty result when no route exists
Route search fails when departure and arrival stations are the same

Run tests in IntelliJ by right-clicking:

src/test/java

and selecting:

Run All Tests

Or with Maven:

mvn test
Design Explanation

The application is divided into several layers:

Model Layer

Contains the main data classes:

Station
Route
Train
Schedule
Customer
Booking
Repository Layer

Stores data in memory using Java collections.

This project does not require an external database. The repositories use lists to store stations, routes, trains, and bookings.

Service Layer

Contains the main business logic:

BookingService handles ticket booking and overbooking prevention
RouteService handles route search
AdminService handles administrator operations
EmailService defines email functionality
ConsoleEmailService simulates email sending in the console
Current Limitations
Data is stored in memory, so it resets when the program restarts
Email notifications are simulated in the console
Route search currently supports direct routes from the predefined train route order
The application is console-based and does not include a graphical interface
Possible Future Improvements
Add a real database such as H2, MySQL, or PostgreSQL
Add real email sending using JavaMail or Spring Boot Mail
Add login system for administrators
Add support for more complex route changeovers
Add a graphical interface or web interface
Save bookings to files or database

Author
Agnes-Maria Tanko

