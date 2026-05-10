# Java Train Ticketing Application

## Project Description

This project is a Java console application for searching train routes, booking train tickets, managing train data, and notifying customers about bookings and delays.

The application uses predefined Romanian train stations, routes, schedules, and trains. Customers can search for available routes, book one or multiple tickets, and receive a simulated email confirmation. Administrators can view bookings and report train delays. When a delay is reported, booked customers are notified through a simulated email message in the console.

The project is implemented using object-oriented programming principles and is divided into model, repository, and service layers.

## Main Features

- Search train routes between two stations
- Display available trains and schedules
- Book one or multiple tickets
- Prevent overbooking by checking available seats
- Validate customer email input before booking
- Send simulated booking confirmation emails
- View bookings for a selected train
- Report train delays
- Send simulated delay notification emails
- Optional SMTP email service for real email sending
- Use predefined Romanian stations, routes, trains, and schedules
- Calculate ticket price and total booking price
- Store booking status using a `BookingStatus` enum
- Separate customer and administrator menus
- Include unit tests for booking and route search functionality

## Technologies Used

- Java 17 or newer
- Maven
- JUnit 5
- Jakarta Mail / Angus Mail
- Git
- GitHub
- IntelliJ IDEA
- Jackson Databind

## Project Structure

```text
src/
├── main/
│   └── java/
│       └── com/example/trainticketing/
│           ├── Main.java
│           ├── enums/
│           │   └── BookingStatus.java
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
|               ├── BookingFileService.java
│               ├── BookingService.java
│               ├── ConsoleEmailService.java
│               ├── EmailService.java
│               ├── RouteService.java
│               └── SmtpEmailService.java
└── test/
    └── java/
        └── com/example/trainticketing/
            ├── BookingServiceTest.java
            └── RouteServiceTest.java
data/
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
```

## How to Run the Project

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
4. Back to main menu
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
IR1746 - InterRegio Timisoara Nord - Bucuresti Nord [InterRegio], capacity: 120, price: 89.50 RON, route: Timisoara Nord to Bucuresti Nord Route, Departure: 2026-05-10 07:15, Arrival: 2026-05-10 17:45, delay: 0 minutes
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
```

### 8. Exit

Input:

```text
3
```

Output:

```text
Thank you for using the Train Ticketing Application.
```

## Testing

The project includes JUnit tests for both implemented problems.

Tested features:

### Problem 1 - Train Ticketing

- Booking succeeds when seats are available
- Booking fails when there are not enough seats
- Route search finds a direct route
- Route search returns an empty result when no route exists
- Route search fails when departure and arrival stations are the same

### Problem 2 - Industrial Monitoring

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

## Design Explanation

The application is divided into three main layers, plus an enum package.

### Enum Package

Contains enum values used by the application:

- `BookingStatus`

The `BookingStatus` enum currently supports:

```text
CONFIRMED
CANCELLED
```

At the moment, new bookings are created with the `CONFIRMED` status.

### Model Layer

Contains the main data classes:

- `Station`
- `Route`
- `Train`
- `Schedule`
- `Customer`
- `Booking`

These classes represent the main objects used by the train ticketing system.

### Repository Layer

Stores data in memory using Java collections.

This project does not require an external database. The repositories use lists to store stations, routes, trains, and bookings.

### Service Layer

Contains the main business logic:

- `BookingService` handles ticket booking, available seat calculation, and overbooking prevention
- `RouteService` handles route search
- `AdminService` handles administrator operations such as viewing bookings and reporting delays
- `EmailService` defines email functionality
- `ConsoleEmailService` simulates email sending in the console
- `SmtpEmailService` provides an optional SMTP-based implementation for real email sending

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

## Booking Persistence

The application saves created bookings to a JSON file:

```text
data/bookings.json
```
This file stores booking history in a readable format.
Example saved booking:
```
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
The JSON export is handled by BookingFileService using Jackson Databind.
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

Example console email output:

```text
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
```

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

## Administrator Functionalities

The administrator can:

- view all trains
- view bookings for a selected train
- report train delays
- notify customers about delays

The project also contains service and repository methods that support adding and removing stations, routes, and trains.

## Current Limitations

- Stations, routes, and trains are stored in memory, so they reset when the program restarts
- Bookings are saved to `data/bookings.json`
- Email notifications are simulated in the console by default
- Route search currently supports direct routes from the predefined train route order
- The application is console-based and does not include a graphical interface
- Administrator login is not implemented yet

## Possible Future Improvements

- Add a real database such as H2, MySQL, or PostgreSQL
- Activate real email sending using `SmtpEmailService`
- Add login system for administrators
- Add support for more complex route changeovers
- Add a graphical interface or web interface
- Add more unit tests
- Add booking cancellation functionality using the `CANCELLED` status

## Optional Problem 2: Industrial Sensor Monitoring and Alarm System

This repository also includes a second optional problem implemented separately from the train ticketing application.

The second problem is an industrial sensor monitoring and alarm system inspired by PLC and SCADA environments used in industrial automation.

The system monitors simulated industrial sensors and checks whether their values are inside predefined safe operating ranges. If a value is outside the allowed range, the system generates an alarm.

### Purpose

The purpose of this problem is to demonstrate basic industrial automation logic in Java.

The monitoring logic is similar to a PLC condition:

```text
IF sensor value is outside the allowed range
THEN alarm is active
```

### Implemented Sensor Types

The application includes the following sensor types:

```text
TEMPERATURE
PRESSURE
VIBRATION
LEVEL
```

### Example Sensors

```text
TEMP-101  - Boiler temperature sensor
PRESS-201 - Hydraulic pressure transducer
VIB-301   - Motor vibration sensor
LEVEL-401 - Tank level sensor
```

### Features

- Simulated industrial sensors
- Minimum and maximum allowed values for each sensor
- Sensor readings with timestamps
- Automatic alarm generation
- Alarm messages for values below or above allowed limits
- Alarm history display
- Separate package from the train ticketing application

### Package Structure

```text
src/main/java/com/example/industrialmonitoring/
├── IndustrialMonitoringMain.java
├── model/
│   ├── Alarm.java
│   ├── Sensor.java
│   ├── SensorReading.java
│   └── SensorType.java
└── service/
    └── SensorMonitoringService.java
```

### How to Run Problem 2

Run this file in IntelliJ IDEA:

```text
src/main/java/com/example/industrialmonitoring/IndustrialMonitoringMain.java
```

### Example Output

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

### Notes

This problem does not connect to a real PLC. It simulates PLC-like monitoring logic in Java, making it easy to run without hardware while still demonstrating industrial automation concepts.

## Author

Agnes-Maria Tanko

## Repository Link

https://github.com/agnestanko/java-train-ticketing-application