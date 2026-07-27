# E-Commerce Backend REST API

A robust, production-ready RESTful e-commerce backend built with **Java 21**, **Spring Boot 3**, and **PostgreSQL**. Designed using clean architecture, layered domain-driven principles, and secured with JWT authentication.

---

## 🛠️ Tech Stack & Prerequisites

### **Tech Stack**

* **Framework:** Spring Boot 3.x
* **Language:** Java 17 / 21
* **Database:** PostgreSQL 16
* **ORM:** Spring Data JPA / Hibernate
* **Security:** Spring Security + JWT *(Phase 6)*
* **Containerization:** Docker & Docker Compose
* **Build Tool:** Maven
* **Utilities:** Lombok, Jakarta Validation

### **Prerequisites**

Make sure you have the following installed locally:

* **JDK 17 or 21**

* **Docker Desktop** (or Docker Engine + Docker Compose)
* **Git**
* **cURL** or **Postman** (for API testing)

---

## 🏗️ Architecture & Project Structure

The project follows a standard layered architecture:

```text
src/main/java/com/example/ecommerce
 ├── config/          # Security, Database, and App Configurations
 ├── controller/      # REST API Endpoints / Request Handlers
 ├── dto/             # Data Transfer Objects (Requests & Responses)
 ├── entity/          # Database Entities (JPA Domain Models)
 ├── exception/       # Global Exception Handling & Custom Errors
 ├── repository/      # Data Access Layer (Spring Data JPA)
 └── service/         # Business Logic Layer
```

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/bogaledemasrepo/ecommerce-backend-api.git
cd ecommerce-backend-api
```

### 2. Start the PostgreSQL Database (Docker)

Ensure your Docker daemon is running, then start the database container:

```bash
docker compose up -d
```

To verify the container is running:

```bash
docker ps
```

### 3. Run the Spring Boot Application

Run the application locally using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

The server will start at **`http://localhost:8080`**.

---

## 🗺️ System Roadmap

* [x] **Phase 1:** Project Initialization & Dockerized PostgreSQL Setup

* [ ] **Phase 2:** Domain Modeling (`Product`, `Category`, `User`, `Cart`, `Order`)

* [ ] **Phase 3:** Data Repositories & DTO Mappers

* [ ] **Phase 4:** Product Catalog & Inventory Management API

* [ ] **Phase 5:** Shopping Cart & Order Processing Engine

* [ ] **Phase 6:** Spring Security, JWT Auth & Role-Based Access (Admin vs Customer)

---

## ⚙️ Environment Configuration

Database connection settings are configured in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce_db
    username: postgres
    password: postgrespassword
```

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
