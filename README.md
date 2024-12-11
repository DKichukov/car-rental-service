#  Full-Stack Car Rental Application

Welcome to the **Full-Stack Car Rental** web application! This project is a hands-on guide designed for developers of all skill levels to create a feature-rich, full-stack car rental system using **Spring Boot-3**, **Angular-16**, **Ng Zorro**, **PostgreSQL**, and **Docker**. The application is designed to handle two distinct user roles: **Admin** and **Customer**, each with unique functionalities and workflows.

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Features](#features)
  - [Admin Functions](#admin-functions)
  - [Customer Functions](#customer-functions)
- [Docker Integration](#docker-integration)


## Project Overview

This full-stack car rental application demonstrates the essential features required to manage a car rental system, including user management, car listings, and booking systems. It features two user roles:

- **Admin:** Responsible for managing the car inventory, reviewing booking requests, and performing CRUD (Create, Read, Update, Delete) operations.
- **Customer:** Can browse available cars, search for specific listings, submit booking requests, and view past bookings.

The frontend of the application runs on `localhost:4200`, while the backend is hosted on `localhost:8080`.

## Technologies Used

### Backend
- **Spring Boot** - For developing the REST API services.
- **PostgreSQL** - As the relational database to manage data.

### Frontend
- **Angular** - For creating the user interfaces.
- **Ng Zorro** - A UI library based on Ant Design for Angular, providing a professional look and feel.

### Containerization
- **Docker** - To containerize both the backend and the database for ease of deployment.

---

## Features

### Admin Functions

As an Admin, you have full control over the car rental system and its operations. Key functionalities include:

- **CRUD Operations for Cars:**
  - **Create:** Add new cars to the inventory with details such as make, model, year, and availability.
  - **Read:** View all car listings and details, such as pricing and availability.
  - **Update:** Modify car details such as rental price, availability, or other specifications.
  - **Delete:** Remove cars from the inventory if they are no longer available for rental.

- **Advanced Car Search:**
  - Implement robust search functionality for filtering cars based on make, model, year, price range, and availability.

- **Booking Request Management:**
  - **Review Requests:** View all booking requests submitted by customers.
  - **Change Status:** Approve or reject booking requests based on availability, customer preference, or other criteria.

### Customer Functions

As a customer, you have the ability to explore available cars and book them for specific time slots. Key features include:

- **Browse Car Listings:**
  - View detailed information for available cars, including make, model, year, price per day, and availability status.

- **Advanced Car Search:**
  - Filter cars by various criteria such as make, model, year, price range, and availability to find the perfect rental option.

- **Submit Booking Requests:**
  - Request to book cars for specific dates and times, with an easy-to-use interface.

- **Booking Status Notifications:**
  - Receive notifications about the status of your booking requests (approved, pending, rejected).

- **View Past Bookings:**
  - Access the history of your past bookings, including detailed information on each reservation.

---

## Docker Integration

This project uses Docker to simplify deployment and ensure consistency across different environments. Both the backend and the database are containerized, which enables quick setup and reduces dependency management issues.

- **Docker Compose** is used to orchestrate the containers for the backend and PostgreSQL database.
- Pre-configured Dockerfiles and `docker-compose.yml` files are included for easy startup.

---
