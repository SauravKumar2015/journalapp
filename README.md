# Journal App

A Spring Boot application for managing journal entries with authentication, sentiment analysis, caching, email notifications, and weather integration.

---

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- User authentication with **JWT**
- Create, update, and delete journal entries
- Sentiment analysis for journal entries
- Caching with **Redis** for faster responses
- Email notifications for certain events
- Weather information retrieval via external API
- Scheduled tasks for automated jobs

---

## Technologies Used

- Java 17
- Spring Boot
- Spring Security (JWT)
- Redis
- Maven
- MongoDB
- Kafka (for event-driven services)
- JUnit / Mockito (testing)

---

## Project Structure

journalApp/
│
├─ .github/                     # GitHub Actions workflows
│   └─ workflows/
│       └─ build.yml
│
├─ .idea/                        # IntelliJ project files
│
├─ .mvn/                         # Maven wrapper
│   └─ wrapper/
│       └─ maven-wrapper.properties
│
├─ htmlReport/                    # Test coverage HTML reports
│   ├─ css/
│   ├─ img/
│   ├─ js/
│   └─ ns-1/
│       └─ sources/
│
├─ src/
│   ├─ main/
│   │   ├─ java/
│   │   │   └─ com/saurav/journalApp/
│   │   │       ├─ api/response/         # API response DTOs
│   │   │       ├─ cache/                # Redis caching logic
│   │   │       ├─ config/               # Security and Redis configuration
│   │   │       ├─ controller/           # REST controllers
│   │   │       ├─ entity/               # Database entities
│   │   │       ├─ enums/                # Enum classes
│   │   │       ├─ filter/               # JWT filters
│   │   │       ├─ model/                # Service layer models
│   │   │       ├─ repository/           # DAO / Repository classes
│   │   │       ├─ service/              # Business logic services
│   │   │       ├─ sheduler/             # Scheduled tasks
│   │   │       ├─ utils/                # Utility classes (JWT, etc.)
│   │   │       └─ JournalAppApplication.java
│   │   │
│   │   └─ resources/
│   │       ├─ application.yml
│   │       ├─ application-dev.yml
│   │       ├─ logback.xml
│   │       ├─ static/                  # Static web resources
│   │       └─ templates/               # Thymeleaf / HTML templates
│   │
│   └─ test/
│       └─ java/com/saurav/journalApp/
│           ├─ repository/             # Repository tests
│           ├─ service/                # Service tests
│           ├─ sheduler/               # Scheduler tests
│           └─ JournalAppApplicationTests.java
│
├─ target/                             # Build outputs (ignored in git)
│
├─ .gitignore
├─ .gitattributes
├─ HELP.md
├─ mvnw
├─ mvnw.cmd
├─ pom.xml



---

## Getting Started

### Prerequisites

- Java 17 or above
- Maven
- Redis server
- MongoDb Atlas
- Optional: Kafka broker for event-driven services

### Running the Application

1. Clone the repository:
```bash
https://github.com/SauravKumar2015/journalapp.git
cd journalApp 

2. Build the project:uild the project:
    mvn clean install

3. Run the application:
    mvn spring-boot:run

4. Access the application:
    http://localhost:8080

## Configuration

application.properties or application.yml

- Database configuration
- Redis configuration
- JWT secret key and expiration
- Kafka configuration (if used)
- Email service credentials

## API Endpoints

Admin:

| Endpoint               | Method | Description           |
| ---------------------- | ------ | --------------------- |
| `/admin/get-all-users` | GET    | Fetch all admin users |
| `/admin/add`           | GET    | Add a new admin       |

Journal:

| Endpoint                | Method | Description                  |
| ----------------------- | ------ | ---------------------------- |
| `/journal/journal`      | GET    | Get all journal entries      |
| `/journal/add`          | GET    | Add a new journal entry      |
| `/journal/get-by-id`    | GET    | Get a journal entry by ID    |
| `/journal/delete-by-id` | GET    | Delete a journal entry by ID |
| `/journal/update-by-id` | GET    | Update a journal entry by ID |

Public:

| Endpoint                | Method | Description              |
| ----------------------- | ------ | ------------------------ |
| `/public/health`        | GET    | Health check             |
| `/public/Sign-Up`       | GET    | User registration        |
| `/public/imported-curl` | POST   | Import data via cURL     |
| `/public/login`         | GET    | User login (returns JWT) |

User:

| Endpoint            | Method | Description                 |
| ------------------- | ------ | --------------------------- |
| `/user/delete-user` | GET    | Delete a user               |
| `/user/update-user` | GET    | Update user information     |
| `/user/greetings`   | GET    | Test endpoint for greetings |

Weather:

| Endpoint        | Method | Description                          |
| --------------- | ------ | ------------------------------------ |
| `/weather/city` | GET    | Fetch weather information for a city |


## Testing
- Run all unit and integration tests with:
    mvn test


## License


---

If you want, I can also **generate a shorter, GitHub-friendly version** with badges, quick start, and example API request snippets that looks more professional on the repo page.  

Do you want me to do that?

