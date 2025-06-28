
# Client Snap

**Client Snap** is a full-stack customer management dashboard designed to help businesses manage their clients effectively. It features a secure, scalable, and responsive interface built using modern Java and JavaScript technologies.

## Overview

Client Snap allows users to register, log in, manage customer records, and monitor client activities in a secure environment. The platform uses JWT-based authentication, role-based access control, and a fully containerized deployment pipeline.

## Features

- User registration and login with JWT authentication
- Role-based access (Admin & User)
- Create, Read, Update, Delete (CRUD) operations on clients
- Responsive and modern UI with Chakra UI (React)
- RESTful API integration between frontend and backend
- PostgreSQL database with Flyway versioning
- Dockerized backend and frontend
- CI/CD pipeline using GitHub Actions and Slack notifications
- Unit testing using JUnit and Mockito

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security + JWT
- PostgreSQL
- Flyway
- JUnit, Mockito

### Frontend
- React.js
- Chakra UI
- Axios
- React Router DOM

### DevOps
- Docker
- GitHub Actions
- AWS EC2
- Slack Integration

## Getting Started

### Prerequisites

- Java 17+
- Node.js & npm
- Docker & Docker Compose
- PostgreSQL

### Backend Setup

```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

### Frontend Setup

```bash
cd frontend
npm install
npm start
```

### Docker Setup

```bash
docker-compose up --build
```

## Deployment

The app is deployed on AWS EC2 using Docker. GitHub Actions automate the build and deployment process, while Slack is used to notify deployment status.

## Folder Structure

```
client-snap/
├── backend/
│   ├── src/
│   └── Dockerfile
├── frontend/
│   ├── src/
│   └── Dockerfile
├── docker-compose.yml
└── README.md
```

## Author

**Lakshay Jain**  
[GitHub](https://github.com/lakshayyjain) | [LinkedIn](https://www.linkedin.com/in/lakshay-jain-lj3201/)

## License

This project is licensed under the MIT License.
