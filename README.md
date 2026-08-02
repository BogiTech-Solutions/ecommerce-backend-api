# 🛒 E-Commerce Backend REST API

A production-grade, secure RESTful e-commerce backend built with **Java 21**, **Spring Boot 3.4.2**, and **PostgreSQL**. Designed with layered architecture, JWT authentication, containerized deployments, and production deployment pipeline configurations.

---

## 🛠️ Tech Stack & Prerequisites

### **Tech Stack**

* **Language:** Java 21
* **Framework:** Spring Boot 3.4.2
* **Database:** PostgreSQL 16 (Local Docker Container / Neon Cloud Postgres)
* **Security:** Spring Security + JWT (Stateless)
* **ORM & Migrations:** Spring Data JPA / Hibernate
* **Containerization:** Docker Multi-Stage Builds & Docker Compose
* **Build Tool:** Maven (`./mvnw`)
* **Payment Gateways:** Stripe & Chapa Integrations
* **Deployment Platform:** Render (Web Service)

### **Prerequisites**

* **JDK 21**
* **Docker Desktop** (or Docker Engine + Docker Compose)
* **Git**
* **Bash** terminal (Linux, macOS, WSL, or Git Bash)

---

## 🏗️ Architecture & Project Structure

The project follows standard layered architectural principles:

src/main/java/com/ecommerce
 ├── config/          # Spring Security, JWT Filters, CORS & App Configs
 ├── controller/      # REST Endpoints (Controller Slice Tested)
 ├── dto/             # Data Transfer Objects & Validation Constraints
 ├── entity/          # JPA Domain Entities
 ├── exception/       # Global Exception Handler (@ControllerAdvice)
 ├── repository/      # Data Access Layer (Spring Data JPA)
 └── service/         # Business Logic Layer

---

## 🚀 Quick Start (Local Development)

We use a custom `run.sh` script to streamline container management, environment variable injection, and test executions.

### 1. Clone the Repository

git clone https://github.com/bogaledemasrepo/ecommerce-backend-api.git
cd ecommerce-backend-api

### 2. Configure Local Environment

Ensure `.env.local` exists in the project root:

SPRING_PROFILES_ACTIVE=dev
POSTGRES_DB=ecommerce_db
POSTGRES_USER=mrbg
POSTGRES_PASSWORD=1234

JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
JWT_EXPIRATION_MS=86400000

STRIPE_SECRET_KEY=sk_test_placeholder
STRIPE_WEBHOOK_SECRET=whsec_test_placeholder
CHAPA_SECRET_KEY=CHASECK_TEST_placeholder
CHAPA_WEBHOOK_SECRET=chapa_webhook_secret_placeholder

### 3. Grant Script Execution Permissions

chmod +x run.sh

### 4. Spin Up the Local Stack

Launch the application along with PostgreSQL in background containers:

./run.sh dev

The REST API will be accessible at http://localhost:8080.

---

## 🛠️ Developer CLI Tools (`./run.sh`)

Use `./run.sh` to execute standard developer tasks:

| Command | Description |
| :--- | :--- |
| `./run.sh dev` | Builds and starts local Docker container stack with `.env.local` |
| `./run.sh logs` | Tails live log output from the API container |
| `./run.sh dev-down` | Stops and removes local development containers |
| `./run.sh test` | Executes all Maven unit and slice tests |
| `./run.sh test <Class>` | Runs a specific test class (e.g., `./run.sh test UserControllerTest`) |
| `./run.sh test-debug <Class>` | Runs test with full stack traces (`-e -X`) |
| `./run.sh test-report` | Displays Surefire error text reports directly in terminal |
| `./run.sh prod-test` | Runs local container stack using `.env.production` |
| `./run.sh clean` | Removes target folder, stops containers, and purges volumes |

---

## ⚙️ Configuration Management

Environment properties follow a 3-tier hierarchy:

1. Base Defaults: Defined in src/main/resources/application.yaml.
2. Local Overrides: Managed via .env.local and injected into docker-compose.yml via --env-file.
3. Production Overrides: Injected directly through Render environment variable configs.

⚠️ Security Warning: Never commit .env.local, .env.production, or real secret keys to source control. Ensure they are listed in .gitignore.

---

## ☁️ Production Deployment (Render + Neon Postgres)

The application is prepared for seamless cloud deployment on Render backed by Neon PostgreSQL:

1. Dockerfile: Multi-stage build leveraging Eclipse Temurin 21 JRE, G1 Garbage Collector tuning, container memory awareness (-XX:MaxRAMPercentage=75.0), and non-root execution.
2. Infrastructure-as-Code: Render service deployment configuration can be managed safely using render.yaml.
3. Database: Connected securely over TLS/SSL (sslmode=require).

---

## 📄 License

This project is open-source and available under the MIT License.