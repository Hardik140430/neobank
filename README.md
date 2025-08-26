🏦 NeoBank – Microservices Based Banking System
📌 Project Overview

NeoBank is a microservices-based digital banking system built with Spring Boot, Spring Cloud, Kafka, and PostgreSQL.
It simulates core banking operations such as user onboarding, authentication, account management, transactions, notifications, auditing, and reporting.
This project is designed to demonstrate real-world microservices architecture with a focus on scalability, security, and resilience.

⚙️ Tech Stack

Backend: Java 17, Spring Boot, Spring Security, Spring Cloud (Eureka, Gateway, Feign)

Database: PostgreSQL (for persistence), H2 (for testing)

Messaging: Apache Kafka (event-driven architecture)

Build Tool: Maven

Deployment: Docker (ready for containerization)

Monitoring (Planned): Prometheus & Grafana / ELK Stack

📂 Microservices in this Project
Service	                    Port	                Responsibility
Eureka Discovery Server	    8761	                Service registry for all microservices
API Gateway	                8080	                Single entry point, request routing, filters, rate-limiting
Auth Service	              8081	                User signup, login, JWT generation & validation
User Service	              8082	                Manages user details (name, email, mobile, address)
Account Service	            8083	                Handles accounts and balances
Transaction Service	        8084	                Handles transfers (debit/credit) between accounts
Kyc service                 8085                  Handles KYC
Audit Service	              8086	                Stores logs for all transactions & account activities
Notification Service	      8087	                Sends email/SMS notifications on transactions
Reporting Service	          8090	                Generates monthly statements & analytics from transactions


🔑 Core Features

✅ Authentication & Authorization – JWT-secured endpoints

✅ Service Registry & Discovery – via Eureka

✅ API Gateway – single entry point with routing rules

✅ KYC - Kyc verification before account creation

✅ User & Account Management – CRUD APIs for customers & accounts

✅ Transaction Handling – debit, credit, transfer with balance updates

✅ Audit Trail – every transaction & account change logged centrally

✅ Notification System – email/SMS alerts for activities

✅ Reporting & Analytics – monthly statements, spending summaries

🚀 (Upcoming) Resilience (Circuit Breaker, Retry), Monitoring Dashboard

🏗️ Architecture Diagram
                [ Client / UI ]
                       |
                 [ API Gateway ]
                       |
    ---------------------------------------
    |          |      |         |         |           
 [Auth]    [User]   [KYC]   [Account] [Transaction] 
                       |
                    [Kafka Bus]
                       |
      -----------------------------------
      |               |                 |
   [Audit]     [Notification]      [Reporting]

▶️ How to Run
Prerequisites

Java 17+

Maven 3+

Docker (optional, for DB/Kafka)

Steps

Clone this repo:

git clone https://github.com/<your-username>/neobank.git
cd neobank


Start PostgreSQL & Kafka locally (or via Docker).

Run each microservice:

mvn spring-boot:run


Access:

Eureka Dashboard: http://localhost:8761

API Gateway: http://localhost:8080

Example API: POST /auth/signup, POST /transaction/transfer


👨‍💻 Author

Hardik Harsora
📧 Contact: hardikharsora75@gmail.com
💼 Aspiring Java Backend / Full Stack Developer | Interested in FinTech & Scalable Systems

✨ This project is continuously evolving with new features (resilience, monitoring, dashboards).
