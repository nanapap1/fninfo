# FNINFO

A monitoring and analytics platform for a Telegram channel named FNINFO.

## Tech Stack
- **Language:** Java 17
- **Framework:** Spring Boot (Microservices Architecture)
- **Build Tool:** Apache Maven
- **Message Broker:** RabbitMQ
- **Databases:** PostgreSQL, Redis
- **Containerization:** Docker,  GitHub Container Registry
- **CI/CD:** GitHub Actions

## Microservices
| Service | Purpose | Status |
|---|---|---|
| Epic Games API Tracker | Monitors public endpoints (server status, staging versions, etc.) | 🔵 Active Development |
| Fortnite STW Mission Tracker | Tracks unofficial API for Save the World daily missions | 🔵 Active Development |
| Change Analyzer & Dispatcher | Receives API change events, analyzes text/images, prepares Telegram alerts | 🔵 Active Development |
| Telegram Publisher | Receives formatted content and posts to specific Telegram channels by ID | 🔵 Active Development |
| Image Generator | Dynamically creates images/infographics from service data | 🟡 Early Development |
| DLQ Service | Works with dead messages from RabbitMQ | 🟡 Early Development |

## Branching Strategy
This repository follows a structured Git workflow:
- `main`: Stable, production-ready releases (major improvements)
- `develop`: Integration branch where task branches are merged
- `{task_name}`: Individual development branches (merged into `develop`)

## CI/CD Pipeline
Each microservice has an independent pipeline that handles:
1. Automated unit & integration testing
2. Docker image creation
3. Publishing to GitHub Container Registry

## Documentation
Each microservice will include its own dedicated `README.md` with service-specific configuration, environment variables, local setup, testing instructions and API/message schema documentation.
