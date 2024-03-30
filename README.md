# Multi-Service Docker Application

This repository contains a multi-container Docker application that consists of several services: 
supplierservice, consumerservice, and a PostgreSQL database container (db-consumer). 
These services work together to provide functionality for a consumer service application.

## Description

The application is designed to demonstrate a microservices architecture 
where the consumer service communicates with the supplier service to manage categories and products. 
PostgreSQL is used as the database for the supplier service.

## Docker Compose Configuration

The `docker-compose.yaml` file defines the configuration for orchestrating the Docker containers. 
It specifies the services, their dependencies, network configuration, and environment variables.

## Services

- **supplierservice**: Represents the supplier service container built from the `./supplier-service` directory. Exposes port 8081.
- **consumerservice**: Represents the consumer service container built from the `./consumer-service` directory. Exposes port 8083.
- **db-consumer**: PostgreSQL database container for the consumer service. Uses the official PostgreSQL image 'postgres:13.1-alpine'. Exposes port 5432.

## Usage

To start the services, follow these steps:

1. Clone this repository to your local machine:

   ```bash
    git clone <repository_url>
   
2. Navigate to the repository directory:

   ```bash 
    cd <repository_directory>

3. Make sure your Spring Boot application is configured to use the appropriate profile for Docker ('prod' profile).

4. Run command to start Docker containers:

   ```bash
    docker-compose up -d

5. When containers are running, you may access the consumer service at http://localhost:8083

## Tests
The project include integration tests for supplier-service, ensuring correctness in functionality.
These tests can be found in the tests directory and run independently.

   ```bash
   mvn test