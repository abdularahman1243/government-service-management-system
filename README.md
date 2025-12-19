📡 Government Service Management System (GSMS)

A scalable, secure, and production-ready backend system designed for managing government telecommunication and public services.
This project simulates a real-world Ministry of Telecommunications system with role-based access control, distributed databases, caching, and JWT-based security.

🚀 Project Overview

GSMS is a backend system that enables:

Secure user registration & authentication

Role-based access for government officers, admins, and citizens

Management of government services

Submission and tracking of service requests

High performance using Redis caching

Separation of concerns using multiple databases

Fully Dockerized infrastructure

Covered with unit tests

This project was built to demonstrate real enterprise-level backend skills, not just CRUD APIs.

🧱 Architecture Overview
Client (Web / Mobile)
        |
        v
 Spring Boot REST API
        |
        +-------------------+
        |                   |
   MySQL (Users)     MongoDB (Services & Requests)
        |
        v
     Redis (Caching)

🗄️ Databases Strategy (Why 3 Databases?)
Database	Usage	Reason
MySQL	Users, Auth, Roles	ACID, relational consistency
MongoDB	Services & Requests	Flexible schema, scalability
Redis	Caching	Performance, reduced DB load

This architecture reflects real government-scale systems.

🔐 Security Features

JWT Authentication

Spring Security 7

Stateless sessions

BCrypt password hashing

Method & URL-based authorization

Custom UserDetails implementation

Role-Based Access Control (RBAC)
Role	Permissions
SUPER_ADMIN	Full system control
ADMIN	Manage users & services
OFFICER	Review & process requests
CITIZEN	Submit & track requests
🧑‍💼 Core Domain Models
👤 User (MySQL)

Authentication & authorization

Government-specific fields (nationalId, employeeCode)

Audit fields (createdAt)

Enum-based roles

🛠️ Service (MongoDB)

Government services (Passport, SIM Registration, etc.)

Active/Inactive management

📄 Service Request (MongoDB)

Request lifecycle (SUBMITTED → APPROVED)

Status history tracking

Citizen ↔ Officer interaction

📦 Tech Stack
Category	Technology
Language	Java 25
Framework	Spring Boot 4
Security	Spring Security + JWT
ORM	Spring Data JPA
NoSQL	MongoDB
Cache	Redis
Testing	JUnit 5, Mockito
Mapping	MapStruct
Build	Maven
Containers	Docker, Docker Compose
Docs	OpenAPI / Swagger
🧪 Testing Strategy

Unit tests for service layer

Mockito-based repository & mapper mocking

Business logic tested independently from DB

Maven-based test execution (CI ready)

Example:

mvn test

🔌 REST API Highlights
🔑 Authentication
POST /api/auth/register
POST /api/auth/login

🛠️ Services
POST /api/services
GET  /api/services

📄 Service Requests
POST /api/requests
GET  /api/requests/my

🐳 Docker Support

All required infrastructure services are containerized:

docker-compose up -d


Includes:

MySQL

MongoDB

Redis

📚 API Documentation

Swagger UI available at:

http://localhost:8080/swagger-ui.html

🎯 Why This Project Matters

This project demonstrates:

✅ Real-world backend architecture
✅ Enterprise security practices
✅ Multi-database design decisions
✅ Clean code & layered architecture
✅ Test-driven thinking
✅ Docker & DevOps awareness

This is not a tutorial project — it is a production-style system.

🧠 Future Improvements

Audit logging

Event-driven notifications

Kafka integration

CI/CD pipeline

Admin dashboard frontend

👨‍💻 Author

Abdul Rahman "Bahadurzai"
Backend Developer (Spring Boot)
Focused on building secure, scalable, real-world systems
