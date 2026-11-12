# Hotel Reservation System

## Project Description
The Hotel Reservation System is a Java-based application developed using Java, JDBC, Maven, and MySQL. It allows users to view available rooms, search rooms based on booking dates, create reservations, cancel bookings, view reservation details, and process payments.

## Technologies Used
- Java
- JDBC
- MySQL
- Maven
- Object-Oriented Programming (OOP)

## Features
- View available rooms
- Search room availability by check-in and check-out dates
- Create room reservations
- Cancel room reservations
- View reservation details
- Process payments
- Validate customer and booking information
- Store reservation and payment details in MySQL

## Database
Database Name: `hotel_reservation`

## How to Run the Project
1. Clone or download this repository.
2. Configure MySQL and create the `hotel_reservation` database and required tables.
3. Set the environment variables `DB_USERNAME` and `DB_PASSWORD` with your MySQL credentials.
4. Open the project in VS Code or another Java IDE.
5. Run the following Maven command:

```bash
mvn clean compile
```

6. Run the application:

```bash
mvn exec:java "-Dexec.mainClass=com.hotel.App"
```

## Developed By
Kusuma Kalyani
