# Java Train Ticketing Application

## Project Description

This repository contains two Java console applications developed for a technical test.

The main project is a train ticketing application. It allows users to search train routes, book tickets, prevent overbooking, receive simulated email notifications, and save bookings to a JSON file.

The repository also includes an optional second problem: an industrial sensor monitoring and alarm system inspired by PLC and SCADA environments. It monitors simulated industrial sensors and saves alarm information to a JSON file.

Both solutions are implemented using object-oriented programming principles and are organized into clear packages, models, services, and tests.

---

# Problem 1: Train Ticketing Application

## Description

The train ticketing application uses predefined Romanian train stations, routes, schedules, and trains.

Customers can search for available routes, book one or multiple tickets, and receive a simulated email confirmation. Administrators can view bookings and report train delays. When a delay is reported, booked customers are notified through a simulated email message in the console.

Bookings are also saved to a JSON file for persistence and later inspection.

## Main Features

- Search direct train routes between two stations
- Search train journeys with one train changeover
- Display available trains and schedules
- Book one or multiple tickets
- Prevent overbooking by checking available seats
- Validate customer email input before booking
- Send simulated booking confirmation emails
- View bookings for a selected train
- Report train delays
- Send simulated delay notification emails
- Optional SMTP email service for real email sending
- Save bookings to `data/bookings.json`
- Use predefined Romanian stations, routes, trains, and schedules
- Calculate ticket price and total booking price
- Store booking status using a `BookingStatus` enum
- Separate customer and administrator menus
- Include unit tests for booking and route search functionality
- Use custom exceptions for clearer booking, route search, and train lookup error handling
- Admin can view stations, routes, and trains
- Admin can remove trains from the system

## Technologies Used

- Java 17 or newer
- Maven
- JUnit 5
- Jackson Databind
- Jakarta Mail / Angus Mail
- Git
- GitHub
- IntelliJ IDEA

## Project Structure

```text
src/
├── main/
│   └── java/
│       └── com/example/
│           ├── industrialmonitoring/
│           │   ├── IndustrialMonitoringMain.java
│           │   ├── model/
│           │   │   ├── Alarm.java
│           │   │   ├── Sensor.java
│           │   │   ├── SensorReading.java
│           │   │   └── SensorType.java
│           │   └── service/
│           │       ├── AlarmFileService.java
│           │       └── SensorMonitoringService.java
│           │
│           └── trainticketing/
|               ├── exception/
|               │   ├── InvalidBookingException.java
|               │   ├── NoRouteFoundException.java
|               │   ├── OverbookingException.java
|               │   └── TrainNotFoundException.java
│               ├── Main.java
│               ├── enums/
│               │   └── BookingStatus.java
│               ├── model/
│               │   ├── Booking.java
│               │   ├── Customer.java
│               │   ├── JourneyOption.java
│               │   ├── Route.java
│               │   ├── Schedule.java
│               │   ├── Station.java
│               │   └── Train.java
│               ├── repository/
│               │   ├── BookingRepository.java
│               │   ├── RouteRepository.java
│               │   ├── StationRepository.java
│               │   └── TrainRepository.java
│               └── service/
│                   ├── AdminService.java
│                   ├── BookingFileService.java
│                   ├── BookingService.java
│                   ├── ConsoleEmailService.java
│                   ├── EmailService.java
│                   ├── RouteService.java
│                   └── SmtpEmailService.java
│
└── test/
    └── java/
        └── com/example/
            ├── industrialmonitoring/
            │   └── IndustrialMonitoringServiceTest.java
            └── trainticketing/
                ├── BookingServiceTest.java
                └── RouteServiceTest.java

data/
├── alarms.json
└── bookings.json
```

## Sample Data

The application starts with predefined Romanian train stations:

```text
Timisoara Nord
Arad
Oradea
Cluj-Napoca
Brasov
Bucuresti Nord
```

Predefined routes:

```text
Timisoara Nord → Arad → Cluj-Napoca → Brasov → Bucuresti Nord
Oradea → Cluj-Napoca → Brasov → Bucuresti Nord
Arad → Timisoara Nord
```

Predefined trains:

```text
IR1746 - InterRegio Timisoara Nord - Bucuresti Nord
IR1833 - InterRegio Oradea - Bucuresti Nord
R2602 - Regio Arad - Timisoara Nord
IR3001 - InterRegio Cluj-Napoca - Timisoara Nord
```

## How to Run Problem 1

Clone the repository:

```bash
git clone https://github.com/agnestanko/java-train-ticketing-application.git
```

Open the project in IntelliJ IDEA.

Run this file:

```text
src/main/java/com/example/trainticketing/Main.java
```

Alternatively, if Maven is installed, run:

```bash
mvn clean test
mvn exec:java
```

## Console Menu

When the application starts, the user first sees the main menu:

```text
===== MAIN MENU =====
1. Customer menu
2. Admin menu
3. Exit
Choose an option:
```

The customer menu contains the customer-related operations:

```text
===== CUSTOMER MENU =====
1. Show all stations
2. Search train route
3. Book ticket
4. Show all trains
5. Back to main menu
Choose an option:
```

The admin menu contains the administrator-related operations:

```text
===== ADMIN MENU =====
1. View bookings for train
2. Report train delay
3. Show all trains
4. Show all stations
5. Show all routes
6. Remove train
7. Back to main menu
Choose an option:
```

## Example Input and Output

### 1. Show All Stations

Input:

```text
1
1
```

Explanation:

```text
1 = Open Customer menu
1 = Show all stations
```

Output:

```text
===== AVAILABLE STATIONS =====
Timisoara Nord (Timisoara)
Arad (Arad)
Oradea (Oradea)
Cluj-Napoca (Cluj-Napoca)
Brasov (Brasov)
Bucuresti Nord (Bucuresti)
```

### 2. Search Train Route

Input:

```text
1
2
Timisoara Nord
Bucuresti Nord
```

Explanation:

```text
1 = Open Customer menu
2 = Search train route
```

Output:

```text
===== SEARCH TRAIN ROUTE =====
Enter departure station: Timisoara Nord
Enter arrival station: Bucuresti Nord

Search results from Timisoara Nord to Bucuresti Nord:
Direct journey:
Train: IR1746 - InterRegio Timisoara Nord - Bucuresti Nord
Route: Timisoara Nord to Bucuresti Nord Route
Schedule: Departure: 2026-05-10 07:15, Arrival: 2026-05-10 17:45
```

### 2.1. Search Train Route with Changeover

Input:

```text
1
2
Oradea
Timisoara Nord
```

Explanation:

```text
1 = Open Customer menu
2 = Search train route
```

Output:

```text
===== SEARCH TRAIN ROUTE =====
Enter departure station: Oradea
Enter arrival station: Timisoara Nord

Search results from Oradea to Timisoara Nord:
Changeover journey:
First train: IR1833 - InterRegio Oradea - Bucuresti Nord
Changeover station: Cluj-Napoca
Second train: IR3001 - InterRegio Cluj-Napoca - Timisoara Nord
```

### 3. Book Ticket

Input:

```text
1
3
IR1746
Alice Brown
alice@example.com
2
```

Explanation:

```text
1 = Open Customer menu
3 = Book ticket
```

Output:

```text
===== BOOK TICKET =====
Enter train ID: IR1746
Enter customer name: Alice Brown
Enter customer email: alice@example.com
Enter number of tickets: 2

===== EMAIL NOTIFICATION =====
To: alice@example.com
Subject: Train Ticket Booking Confirmation - BK-12345678
Message:
Dear Alice Brown,

Your booking has been confirmed successfully.

Booking details:
Booking ID: BK-12345678
Status: CONFIRMED
Train: IR1746 - InterRegio Timisoara Nord - Bucuresti Nord
Train type: InterRegio
Route: Timisoara Nord to Bucuresti Nord Route
Schedule: Departure: 2026-05-10 07:15, Arrival: 2026-05-10 17:45
Tickets: 2
Price per ticket: 89.50 RON
Total price: 179.00 RON

Please arrive at the station at least 15 minutes before departure.

Thank you for using our Train Ticketing Application.
==============================

Booking created successfully:
Booking ID: BK-12345678, Customer: Alice Brown, Train: IR1746, Tickets: 2, Total price: 179.00 RON, Status: CONFIRMED
Available seats after booking: 118
```

### 4. Invalid Email Validation

Input:

```text
1
3
IR1746
Alice Brown
wrongemail
alice@example.com
2
```

Output:

```text
===== BOOK TICKET =====
Enter train ID: IR1746
Enter customer name: Alice Brown
Enter customer email: wrongemail
Invalid email address. Please enter a valid email, for example: customer@example.com
Enter customer email: alice@example.com
Enter number of tickets: 2
```

### 5. View Bookings for Train

Input:

```text
2
1
IR1746
```

Explanation:

```text
2 = Open Admin menu
1 = View bookings for train
```

Output:

```text
===== VIEW BOOKINGS FOR TRAIN =====
Enter train ID: IR1746

Booking ID: BK-12345678, Customer: Alice Brown, Train: IR1746, Tickets: 2, Total price: 179.00 RON, Status: CONFIRMED
```

### 6. Report Train Delay

Input:

```text
2
2
IR1746
20
```

Explanation:

```text
2 = Open Admin menu
2 = Report train delay
```

Output:

```text
===== REPORT TRAIN DELAY =====
Enter train ID: IR1746
Enter delay in minutes: 20

===== EMAIL NOTIFICATION =====
To: alice@example.com
Subject: Train Delay Notification
Message:
Dear Alice Brown,

We inform you that your train is delayed.

Booking ID: BK-12345678
Train: IR1746 - InterRegio Timisoara Nord - Bucuresti Nord
Delay: 20 minutes

We apologize for the inconvenience.
==============================

Delay reported successfully.
```

### 7. Show All Trains

Input from Customer menu:

```text
1
4
```

Or input from Admin menu:

```text
2
3
```

Output:

```text
===== AVAILABLE TRAINS =====
IR1746 - InterRegio Timisoara Nord - Bucuresti Nord [InterRegio], capacity: 120, price: 89.50 RON, route: Timisoara Nord to Bucuresti Nord Route, Departure: 2026-05-10 07:15, Arrival: 2026-05-10 17:45, delay: 0 minutes
Available seats: 120

IR1833 - InterRegio Oradea - Bucuresti Nord [InterRegio], capacity: 100, price: 95.00 RON, route: Oradea to Bucuresti Nord Route, Departure: 2026-05-10 08:30, Arrival: 2026-05-10 18:20, delay: 0 minutes
Available seats: 100

R2602 - Regio Arad - Timisoara Nord [Regio], capacity: 80, price: 18.50 RON, route: Arad to Timisoara Nord Route, Departure: 2026-05-10 06:40, Arrival: 2026-05-10 07:55, delay: 0 minutes
Available seats: 80

IR3001 - InterRegio Cluj-Napoca - Timisoara Nord [InterRegio], capacity: 90, price: 62.00 RON, route: Cluj-Napoca to Timisoara Nord Route, Departure: 2026-05-10 14:20, Arrival: 2026-05-10 19:10, delay: 0 minutes
Available seats: 90
```

### 8. Remove Train

Input:

```text
2
6
R2602
```

Explanation:

```text
2 = Open Admin menu
6 = Remove train
R2602 = Train ID to remove
```

Output:

```text
===== REMOVE TRAIN =====
Enter train ID to remove: R2602
Train removed successfully: R2602 - Regio Arad - Timisoara Nord
```

### 9. Exit

Input:

```text
3
```

Output:

```text
Thank you for using the Train Ticketing Application.
```

## Booking Persistence

The application saves created bookings to a JSON file:

```text
data/bookings.json
```

This file stores booking history in a readable format.

Example saved booking:

```json
[
  {
    "bookingId": "BK-EC5C6E79",
    "customerName": "Andrei Popescu",
    "customerEmail": "andrei.popescu@example.com",
    "trainId": "IR1746",
    "trainName": "InterRegio Timisoara Nord - Bucuresti Nord",
    "trainType": "InterRegio",
    "route": "Timisoara Nord to Bucuresti Nord Route",
    "departureTime": "2026-05-10T07:15",
    "arrivalTime": "2026-05-10T17:45",
    "numberOfTickets": 4,
    "pricePerTicket": 89.5,
    "totalPrice": 358.0,
    "status": "CONFIRMED",
    "bookingTime": "2026-05-10T17:50:25.222066300"
  }
]
```

The JSON export is handled by `BookingFileService` using Jackson Databind.

## Overbooking Prevention

The application prevents overbooking by calculating the available seats before creating a booking.

The logic is:

```text
available seats = train capacity - already booked seats
```

If the customer requests more tickets than available seats, the booking is rejected.

Example:

```text
Train capacity: 120
Already booked seats: 118
Requested tickets: 3
Result: Booking failed because only 2 seats are available.
```

## Email Validation

The application validates the customer email address before creating a booking.

If the user enters an invalid email address, the application asks for the email again.

Example:

```text
Enter customer email: wrongemail
Invalid email address. Please enter a valid email, for example: customer@example.com
Enter customer email: alice@example.com
```

## Email Notification System

The project includes an email service interface:

```text
EmailService
```

The application has two email service implementations:

```text
ConsoleEmailService
SmtpEmailService
```

### ConsoleEmailService

`ConsoleEmailService` is the default implementation used by the application.

It simulates email sending by printing the email content in the console. This is useful for testing and demonstration because it does not require an external email account, password, or SMTP configuration.

The application currently uses this implementation in `Main.java`:

```java
EmailService emailService = new ConsoleEmailService();
```

The application sends console email notifications for:

- successful booking confirmation
- train delay notification

### SmtpEmailService

`SmtpEmailService` is an optional realistic implementation that can send real emails through an SMTP server.

This implementation uses environment variables for configuration. This means that private credentials such as usernames, passwords, and sender email addresses are not stored directly in the source code and should not be committed to GitHub.

Required environment variables:

```text
SMTP_HOST
SMTP_PORT
SMTP_USERNAME
SMTP_PASSWORD
SMTP_FROM
```

Example SMTP providers:

```text
Gmail SMTP
Outlook SMTP
Mailtrap
SendGrid
Brevo
```

To use real email sending, the email service creation in `Main.java` can be changed from:

```java
EmailService emailService = new ConsoleEmailService();
```

to:

```java
EmailService emailService = new SmtpEmailService();
```

Important: real email credentials should never be written directly in the code or committed to GitHub.

The SMTP implementation is included to show how the project could be extended for real-world email delivery, while the console implementation remains the default option for simple testing and project demonstration.

---

# Problem 2: Industrial Sensor Monitoring and Alarm System

## Description

This repository also includes a second optional problem implemented separately from the train ticketing application.

The second problem is an industrial sensor monitoring and alarm system inspired by PLC and SCADA environments used in industrial automation.

The system monitors simulated industrial sensors and checks whether their values are inside predefined safe operating ranges. If a value is outside the allowed range, the system generates an alarm.

## Purpose

The purpose of this problem is to demonstrate basic industrial automation logic in Java.

The monitoring logic is similar to a PLC condition:

```text
IF sensor value is outside the allowed range
THEN alarm is active
```

## Implemented Sensor Types

The application includes the following sensor types:

```text
TEMPERATURE
PRESSURE
VIBRATION
LEVEL
```

## Example Sensors

```text
TEMP-101  - Boiler temperature sensor
PRESS-201 - Hydraulic pressure transducer
VIB-301   - Motor vibration sensor
LEVEL-401 - Tank level sensor
```

## Features

- Simulated industrial sensors
- Minimum and maximum allowed values for each sensor
- Sensor readings with timestamps
- Automatic alarm generation
- Alarm messages for values below or above allowed limits
- Alarm history display
- Alarm persistence in `data/alarms.json`
- Separate package from the train ticketing application

## How to Run Problem 2

Run this file in IntelliJ IDEA:

```text
src/main/java/com/example/industrialmonitoring/IndustrialMonitoringMain.java
```

## Example Output

```text
===== INDUSTRIAL SENSOR MONITORING SYSTEM =====
PLC-inspired monitoring logic for industrial sensors and alarms.

Sensor: TEMP-101 - Boiler temperature sensor
Type: TEMPERATURE
Value: 72.5 °C
Normal range: 20.0 - 80.0 °C
Status: NORMAL

Sensor: PRESS-201 - Hydraulic pressure transducer
Type: PRESSURE
Value: 11.2 bar
Normal range: 2.0 - 10.0 bar
Status: ALARM
Alarm: Hydraulic pressure transducer is above the maximum allowed value.

Sensor: VIB-301 - Motor vibration sensor
Type: VIBRATION
Value: 6.8 mm/s
Normal range: 0.0 - 5.0 mm/s
Status: ALARM
Alarm: Motor vibration sensor is above the maximum allowed value.

Sensor: LEVEL-401 - Tank level sensor
Type: LEVEL
Value: 5.0 %
Normal range: 10.0 - 90.0 %
Status: ALARM
Alarm: Tank level sensor is below the minimum allowed value.

===== ALARM HISTORY =====
ALARM ID: AL-12345678, Sensor: PRESS-201 - Hydraulic pressure transducer, Actual value: 11.2 bar, Message: Hydraulic pressure transducer is above the maximum allowed value.
```

## Alarm Persistence

The industrial monitoring application saves generated alarms to a JSON file:

```text
data/alarms.json
```

This file stores alarm history in a readable format, similar to how industrial monitoring systems keep alarm logs for analysis, maintenance, and troubleshooting.

Example saved alarm:

```json
[
  {
    "alarmId": "AL-97D9E627",
    "sensorId": "PRESS-201",
    "sensorName": "Hydraulic pressure transducer",
    "sensorType": "PRESSURE",
    "actualValue": 11.2,
    "unit": "bar",
    "minimumAllowedValue": 2.0,
    "maximumAllowedValue": 10.0,
    "message": "Hydraulic pressure transducer is above the maximum allowed value.",
    "timestamp": "2026-05-10T18:46:59.507058700"
  }
]
```

The JSON export is handled by `AlarmFileService` using Jackson Databind.

## Notes

This problem does not connect to a real PLC. It simulates PLC-like monitoring logic in Java, making it easy to run without hardware while still demonstrating industrial automation concepts.

---

# Testing

The project includes JUnit tests for both implemented problems. There are 10 tests in total.

## Problem 1 - Train Ticketing

Tested features:

- Booking succeeds when seats are available
- Booking fails when there are not enough seats
- Route search finds a direct route
- Route search returns an empty result when no route exists
- Route search fails when departure and arrival stations are the same
- Route search finds a journey with one train changeover

## Problem 2 - Industrial Monitoring

Tested features:

- Normal sensor reading does not create an alarm
- High sensor reading creates an alarm
- Low sensor reading creates an alarm
- Alarm history stores all generated alarms

Run tests in IntelliJ by right-clicking:

```text
src/test/java
```

and selecting:

```text
Run Tests in java
```

Or with Maven:

```bash
mvn test
```

# Design Explanation

The application is divided into separate packages for each problem.

## Train Ticketing Design

The train ticketing application contains:

- enum package
- model layer
- repository layer
- service layer

### Enum Package

Contains enum values used by the application:

- `BookingStatus`

The `BookingStatus` enum currently supports:

```text
CONFIRMED
CANCELLED
```

At the moment, new bookings are created with the `CONFIRMED` status.

### Exception Package

Contains custom exceptions used for clearer error handling:

- `InvalidBookingException`
- `OverbookingException`
- `NoRouteFoundException`
- `TrainNotFoundException`

These exceptions are used for booking validation, overbooking prevention, route search errors, and train lookup errors.

### Model Layer

Contains the main data classes:

- `Station`
- `Route`
- `Train`
- `Schedule`
- `Customer`
- `Booking`
- `JourneyOption`

`JourneyOption` is used to represent either a direct journey or a journey with one train changeover.

### Repository Layer

Stores data in memory using Java collections.

This project does not require an external database. The repositories use lists to store stations, routes, trains, and bookings.

### Service Layer

Contains the main business logic:

- `BookingService` handles ticket booking, available seat calculation, overbooking prevention, booking persistence, and booking-related exceptions
- `BookingFileService` saves booking data to JSON
- `RouteService` handles direct route search, route search with one train changeover, and no-route error handling
- `AdminService` handles administrator operations such as viewing bookings and reporting delays
- `EmailService` defines email functionality
- `ConsoleEmailService` simulates email sending in the console
- `SmtpEmailService` provides an optional SMTP-based implementation for real email sending

## Industrial Monitoring Design

The industrial monitoring application contains:

- model layer
- service layer

### Model Layer

Contains the main industrial monitoring classes:

- `Sensor`
- `SensorType`
- `SensorReading`
- `Alarm`

### Service Layer

Contains the monitoring and persistence logic:

- `SensorMonitoringService` handles sensor readings, range checking, alarm generation, and alarm history
- `AlarmFileService` saves alarm data to JSON

# Current Limitations

- Stations, routes, and trains are predefined in memory
- Bookings are saved to JSON, but they are not automatically loaded when the application starts
- Alarm logs are saved to JSON, but they are not automatically loaded when the application starts
- Route search supports direct routes and journeys with one train changeover
- The application is console-based and does not include a graphical interface
- Administrator login is not implemented yet
- The industrial monitoring system does not connect to a real PLC or physical sensors

# Possible Future Improvements

- Load saved bookings from JSON when the application starts
- Load saved alarm logs from JSON when the application starts
- Add a real database such as H2, MySQL, or PostgreSQL
- Activate real email sending using `SmtpEmailService`
- Add login system for administrators
- Add support for multiple route changeovers
- Add a graphical interface or web interface
- Add more unit tests
- Add booking cancellation functionality using the `CANCELLED` status
- Connect the industrial monitoring system to real PLC or sensor data in the future

# Author

Agnes-Maria Tanko

# Repository Link

https://github.com/agnestanko/java-train-ticketing-application