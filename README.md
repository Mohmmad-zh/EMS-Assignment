# EMS-Assignment:

Employee Attendance System
The Employee Attendance System is a web-based application that allows managers to track and monitor employee attendance. The application provides an interface for employees to check in and check out, and a dashboard for managers to view attendance records and generate reports.

Technology Stack
The application is built using the following technologies:

Java 8
Spring Boot
Spring Data JPA
Thymeleaf
PostgreSQL
Getting Started
To get started with the application, follow these steps:

Clone the repository from GitHub: git clone https://github.com/Mohmmad-zh/EMS-Assignment.git
Install PostgreSQL if it is not already installed on your machine.
Create a new PostgreSQL database for the application.
Update the database configuration in src/main/resources/application.properties with the appropriate values for your database.
Build and run the application using Maven: mvn spring-boot:run
Navigate to http://localhost:8080 in your web browser to access the application.
Application Structure
The application has the following package structure:

assignment.EMS: The root package for the application.
controller: Contains the controllers for the application.
model: Contains the domain models for the application.
repository: Contains the repositories for the domain models.
service: Contains the services for the application.
EMS_AppApplication.java: The main class that starts the application.
The application follows the Model-View-Controller (MVC) architecture, where the domain models are in the model package, the controllers are in the controller package, and the views are implemented using Thymeleaf templates.

The services are responsible for implementing the business logic of the application, and interact with the repositories to retrieve and persist data.

Testing
The application includes unit tests for the service layer. To run the tests, use the following command: mvn test

Dependencies
The application has the following dependencies:

Spring Boot Starter Data JPA: Provides the Spring Data JPA framework for interacting with databases.
Spring Boot Starter Thymeleaf: Provides the Thymeleaf templating engine for rendering views.
Spring Boot Starter Web: Provides the Spring Web framework for building web applications.
Spring Boot DevTools: Provides tools for development time, such as automatic reloading of changes.
PostgreSQL Driver: Provides the driver for interacting with PostgreSQL databases.
Spring Boot Starter Test: Provides testing support for the application.
Conclusion
The Employee Attendance System is a simple web application that allows managers to track employee attendance. It is built using the Spring framework and uses PostgreSQL as the backend database. The application follows the MVC architecture and includes unit tests for the service layer.
