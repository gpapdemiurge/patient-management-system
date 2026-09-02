# Patient Management System

## Project Overview

This project is a full-stack Patient Management System built as a real-world DevOps learning project.

## short prokect description

what do i need to implement


1. UserService

Your users table contains:

users
├── id
├── username
├── email
├── password
├── role
└── created_at

Keep the service simple:

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}

So your API could be:

POST   /api/users
GET    /api/users
GET    /api/users/{id}
PUT    /api/users/{id}
DELETE /api/users/{id}

For your course, you could have roles such as:

ADMIN
DOCTOR

You don't need complicated authentication/authorization initially.

2. PatientService

This should probably be your main CRUD functionality.

Your table:

patients
├── id
├── first_name
├── last_name
├── date_of_birth
├── gender
├── phone
├── email
├── address
└── created_at

Service:

public interface PatientService {

    Patient createPatient(Patient patient);

    List<Patient> getAllPatients();

    Patient getPatientById(Long id);

    Patient updatePatient(Long id, Patient patient);

    void deletePatient(Long id);
}

API:

POST   /api/patients
GET    /api/patients
GET    /api/patients/{id}
PUT    /api/patients/{id}
DELETE /api/patients/{id}

For example:

POST /api/patients

with:

{
    "firstName": "John",
    "lastName": "Smith",
    "dateOfBirth": "1985-05-12",
    "gender": "MALE",
    "phone": "6900000000",
    "email": "john@example.com",
    "address": "Athens"
}
3. AppointmentService

This is where your three tables actually connect.

Your appointment has:

appointments
├── id
├── patient_id  → patients.id
├── doctor_id   → users.id
├── appointment_date
├── reason
└── status

For a basic application, I would implement:

public interface AppointmentService {

    Appointment createAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    Appointment getAppointmentById(Long id);

    Appointment updateAppointment(Long id, Appointment appointment);

    void deleteAppointment(Long id);
}

API:

POST   /api/appointments
GET    /api/appointments
GET    /api/appointments/{id}
PUT    /api/appointments/{id}
DELETE /api/appointments/{id}
The entire backend

You can therefore have a very simple structure:

backend
│
└── src/main/java/com/yourapp
    │
    ├── controller
    │   ├── UserController.java
    │   ├── PatientController.java
    │   └── AppointmentController.java
    │
    ├── service
    │   ├── UserService.java
    │   ├── PatientService.java
    │   └── AppointmentService.java
    │
    ├── service/impl
    │   ├── UserServiceImpl.java
    │   ├── PatientServiceImpl.java
    │   └── AppointmentServiceImpl.java
    │
    ├── repository
    │   ├── UserRepository.java
    │   ├── PatientRepository.java
    │   └── AppointmentRepository.java
    │
    └── entity
        ├── User.java
        ├── Patient.java
        └── Appointment.java

The flow is:

React
  │
  │ HTTP
  ↓
Controller
  │
  ↓
Service
  │
  ↓
Repository
  │
  ↓
JPA / Hibernate
  │
  ↓
PostgreSQL
How the Appointment part works

This is the only part that is slightly more interesting because you have relationships.

You have:

Patient
   │
   │ 1
   │
   │
   │ *
Appointment
   │
   │ *
   │
   │ 1
Doctor/User

So an appointment belongs to:

one patient

and

one doctor.

Your Appointment entity could look roughly like:

@Entity
@Table(name = "appointments", schema = "hospital")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    private LocalDateTime appointmentDate;

    private String reason;

    private String status;

    // getters and setters
}

Then you could create an appointment like:

{
    "patientId": 5,
    "doctorId": 2,
    "appointmentDate": "2026-09-05T10:30:00",
    "reason": "Regular checkup",
    "status": "SCHEDULED"
}

Your backend finds:

patient_id = 5
       ↓
Patient #5

doctor_id = 2
       ↓
User #2

Patient services 

-cancel appointment 


The goal is to learn and implement modern software engineering practices using:

- React
- Spring Boot
- PostgreSQL (Neon)
- Flyway
- Docker
- Kubernetes
- Helm
- ArgoCD
- GitHub Actions
- Ansible
- Ubuntu Server

Development is done on a Windows PC and the final deployment target will be a local Ubuntu server running Kubernetes.

---

# Current Architecture

Frontend:

- React (Vite)
- JavaScript (no TypeScript)

Backend:

- Spring Boot 4.1.0
- Java 24
- Maven

Database:

- PostgreSQL hosted on Neon

Database migration:

- Flyway

Version control:

- Git
- GitHub

Future infrastructure:

- Docker
- Kubernetes
- Helm
- ArgoCD
- GitHub Actions
- Ansible

---

# Project Structure

```text
patient-management-system/

├── frontend/
│   └── React + Vite application
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── db/
│   │   │           └── migration/
│   │   │               └── V1__create_initial_schema.sql
│   │   └── test/
│   │
│   ├── pom.xml
│   └── mvnw
│
└── README.md
```

---

# Backend

## Technology Stack

- Java 24
- Spring Boot 4.1.0
- Spring Security
- Spring Data JPA
- Spring Validation
- Maven
- Flyway
- PostgreSQL Driver
- Lombok

---

# Frontend

## Technology Stack

- React
- Vite
- JavaScript

The frontend folder already exists and will communicate with the Spring Boot API.

---

# Database

Database provider:

- Neon PostgreSQL

Current schema:

```sql
hospital
```

Flyway manages all database changes.

Migration folder:

```text
backend/src/main/resources/db/migration
```

Current migration:

```text
V1__create_initial_schema.sql
```

---

# Planned Database Tables

## users

Stores application users.

Possible fields:

- id
- email
- password
- first_name
- last_name
- role
- created_at

---

## patients

Stores patient information.

Possible fields:

- id
- first_name
- last_name
- birth_date
- phone
- email

---

## appointments

Stores appointments.

Possible fields:

- id
- patient_id
- doctor_id
- appointment_date
- status
- notes

---

# Flyway Configuration

Configured in:

```text
backend/src/main/resources/application.properties
```

Current Flyway configuration:

```properties
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.schemas=hospital
spring.flyway.default-schema=hospital
```

Migration naming convention:

```text
V1__description.sql
V2__description.sql
V3__description.sql
```

Never modify old migrations after production data exists.

Create new migration files instead.

Example:

```text
V2__add_user_table.sql
V3__add_email_column.sql
```

---

# Development Environment

Operating system:

- Windows 10/11

Editor:

- Visual Studio Code

Installed tools:

- Java 24
- Maven Wrapper
- Node.js
- npm
- Git

---

# Future DevOps Roadmap

## Phase 1

✅ Create React frontend

✅ Create Spring Boot backend

✅ Connect to Neon PostgreSQL

✅ Configure Flyway

---

## Phase 2

- Create REST API
- Create entities
- Create repositories
- Create services
- Create controllers

---

## Phase 3

- Dockerize frontend
- Dockerize backend
- Create Docker Compose

---

## Phase 4

Create Kubernetes manifests:

- Namespace
- Deployment
- Service
- ConfigMap
- Secret
- Ingress

---

## Phase 5

Package manifests using Helm.

Structure:

```text
helm/

└── patient-management/
    ├── Chart.yaml
    ├── values.yaml
    └── templates/
```

---

## Phase 6

Configure GitHub Actions:

- Build React
- Build Spring Boot
- Run tests
- Build Docker images
- Push Docker images

---

## Phase 7

Configure ArgoCD:

- Sync Kubernetes manifests automatically
- GitOps workflow

---

## Phase 8

Configure Ansible:

- Provision Ubuntu server
- Install Docker
- Install Kubernetes
- Install Helm
- Install ArgoCD

---

# Deployment Goal

Target environment:

- Local Ubuntu VM
- Kubernetes cluster
- GitOps deployment with ArgoCD

Expected deployment flow:

```text
Developer

    ↓

GitHub Push

    ↓

GitHub Actions

    ↓

Docker Build

    ↓

Docker Registry

    ↓

ArgoCD detects changes

    ↓

Kubernetes updates cluster

    ↓

Application becomes available
```

---

# Notes for AI Agents

Important constraints:

- Use JavaScript, not TypeScript.
- Frontend uses React + Vite.
- Backend uses Spring Boot.
- Database is Neon PostgreSQL.
- Flyway handles all schema migrations.
- Kubernetes will be used for deployment.
- Helm will manage Kubernetes manifests.
- ArgoCD will handle GitOps deployments.
- Ansible will provision Ubuntu servers.
- Development happens on Windows.
- Production target is Ubuntu.

When modifying the database:

- Never edit old Flyway migrations.
- Always create a new migration.

Example:

```text
V2__add_patient_table.sql
```

---

# Status

Current status:

✅ Frontend created.

✅ Backend created.

✅ Neon database connected.

✅ Flyway configured.

🚧 REST API not implemented yet.

🚧 Docker not configured.

🚧 Kubernetes not configured.

🚧 CI/CD not configured.

# AI Project Context

## Project Name

Hospital Management System

---

# Project Goal

This project is a complete Hospital Management System built as a real-world software engineering and DevOps learning project.

The objective is not only to create a working application, but to design, build, deploy, and maintain it using modern software engineering best practices and production-grade DevOps tools.

Every part of the project should be implemented as if it were being developed by a professional software company.

---

# Primary Objectives

The project should teach and demonstrate:

- Professional backend development
- Professional frontend development
- Database design
- REST API development
- Authentication & Authorization
- CI/CD pipelines
- Containerization
- Kubernetes
- GitOps
- Infrastructure automation
- Production deployment

Whenever possible, recommend production-ready solutions instead of shortcuts.

---

# Technology Stack

## Frontend

- React
- Vite
- JavaScript
- React Router
- Axios
- Material UI (preferred unless another library is required)

---

## Backend

- Java 24
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven
- Bean Validation
- Lombok

---

## Database

- PostgreSQL
- Hosted on Neon

Database migrations are managed exclusively using Flyway.

No manual database modifications should be recommended.

---

## DevOps Stack

This project will eventually include:

- Docker
- Docker Compose
- Kubernetes
- Helm
- GitHub Actions
- ArgoCD
- Ansible
- Ubuntu Server

Deployment target is a local Ubuntu virtual machine running Kubernetes.

Development is performed on Windows.

---

# Current Project Status

Completed:

- Git repository created
- Frontend created using React + Vite
- Backend created using Spring Boot
- PostgreSQL database created on Neon
- Flyway configured
- Initial database migration completed
- Backend successfully connected to PostgreSQL

Still to build:

- Backend architecture
- REST API
- Authentication
- JWT
- User management
- React UI
- Docker
- Kubernetes
- Helm
- GitHub Actions
- ArgoCD
- Ansible

---

# Current Database Schema

The project currently contains one schema:

```
hospital
```

Current tables:

## users

Application users.

This table contains administrators, doctors, nurses, receptionists and any authenticated system user.

Columns:

- id
- username
- email
- password
- role
- created_at

---

## patients

Stores patient information.

Columns:

- id
- first_name
- last_name
- date_of_birth
- gender
- phone
- email
- address
- created_at

---

## appointments

Stores appointments between doctors and patients.

Columns:

- id
- patient_id
- doctor_id
- appointment_date
- reason
- status

Relationships:

- patient_id → patients.id
- doctor_id → users.id

---

# Future Database Expansion

The project is expected to grow considerably.

Future tables may include:

- medical_records
- prescriptions
- medications
- departments
- rooms
- admissions
- invoices
- insurance
- payments
- laboratory_results
- diagnoses
- surgeries
- nurses
- schedules
- audit_logs
- notifications
- refresh_tokens

The database should be designed so that adding these modules is straightforward.

---

# Application Roles

The system should support multiple user roles.

Examples include:

- Administrator
- Doctor
- Nurse
- Receptionist

Each role should have different permissions.

Spring Security and role-based authorization should be used.

---

# Backend Architecture

The backend should follow a layered architecture.

```
Controller

↓

Service

↓

Repository

↓

Database
```

Each entity should eventually contain:

- Entity
- DTO
- Repository
- Service
- Service Implementation (if appropriate)
- Controller
- Mapper (if needed)
- Validation

Use REST API best practices.

---

# Frontend Architecture

Frontend should follow a clean React structure.

Example:

```
src/

components/

pages/

layouts/

hooks/

services/

context/

routes/

assets/

utils/
```

Avoid putting everything inside one folder.

---

# Development Rules

When helping with this project:

- Explain why code is written a certain way.
- Follow production best practices.
- Keep code clean and maintainable.
- Use meaningful class and variable names.
- Keep responsibilities separated.
- Avoid duplicated code.
- Prefer scalability over quick solutions.
- Explain important Spring Boot concepts.
- Explain React concepts when needed.
- Explain DevOps concepts when needed.

---

# Flyway Rules

Never modify an old migration after it has been applied.

Always create a new migration.

Example:

```
V2__create_departments.sql

V3__create_prescriptions.sql
```

---

# AI Assistant Instructions

When assisting with this project:

1. Think like a senior software architect.

2. Recommend production-ready solutions.

3. Explain every important design decision.

4. If multiple solutions exist, compare them.

5. Keep the project scalable.

6. Keep the project secure.

7. Follow REST API best practices.

8. Follow Spring Boot best practices.

9. Follow React best practices.

10. Follow Kubernetes and Docker best practices.

11. Do not introduce unnecessary complexity.

12. Assume this project will eventually be deployed in production.

13. If the project structure can be improved, explain why before making changes.

14. Before creating new code, verify that it integrates cleanly with the existing architecture.

15. Always consider future scalability.

---

# Final Goal

The final application should be a production-quality Hospital Management System demonstrating the complete software development lifecycle:

- React Frontend
- Spring Boot Backend
- PostgreSQL Database
- Flyway Migrations
- Docker Containers
- Kubernetes Deployment
- Helm Charts
- GitHub Actions CI/CD
- ArgoCD GitOps
- Ansible Infrastructure Automation

The project should serve as both a portfolio project and a complete learning resource for professional full-stack and DevOps development.