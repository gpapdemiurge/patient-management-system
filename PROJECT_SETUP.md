# Commands Used So Far

## 1. Clone the repository

```bash
git clone https://github.com/<your-username>/patient-management-system.git

cd patient-management-system
```

---

## 2. Verify installed tools

```bash
java -version

mvn -version

node -v

npm -v

git --version
```

---

## 3. Create the Spring Boot backend

Generated from:

https://start.spring.io

Settings:

- Project: Maven
- Language: Java
- Spring Boot: 4.1.0
- Java: 24
- Packaging: Jar

Dependencies:

- Spring Web
- Spring Security
- Spring Data JPA
- Validation
- PostgreSQL Driver
- Lombok
- Flyway

Extract the generated project into:

```text
patient-management-system/backend
```

---

## 4. Run the backend

Move into the backend folder:

```bash
cd backend
```

Run:

### Git Bash

```bash
./mvnw spring-boot:run
```

### PowerShell

```powershell
.\mvnw.cmd spring-boot:run
```

---

## 5. Create the React frontend

Move to the project root:

```bash
cd ..
```

If the frontend folder does not exist:

```bash
npm create vite@latest frontend
```

Choose:

```text
Framework: React
Variant: JavaScript
```

---

## 6. Install frontend dependencies

```bash
cd frontend

npm install
```

---

## 7. Start the frontend

```bash
npm run dev
```

Frontend URL:

```text
http://localhost:5173
```

---

## 8. Create the Flyway migration folder

Inside:

```text
backend/src/main/resources/
```

Create:

```text
db/
└── migration/
```

---

## 9. Create the first migration

Create:

```text
backend/src/main/resources/db/migration/V1__create_initial_schema.sql
```

Example:

```sql
CREATE SCHEMA IF NOT EXISTS hospital;

CREATE TABLE hospital.users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE hospital.patients (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    gender VARCHAR(20),
    phone VARCHAR(30),
    email VARCHAR(150),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE hospital.appointments (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_date TIMESTAMP NOT NULL,
    reason VARCHAR(255),
    status VARCHAR(50) DEFAULT 'SCHEDULED',

    CONSTRAINT fk_patient
        FOREIGN KEY(patient_id)
        REFERENCES hospital.patients(id),

    CONSTRAINT fk_doctor
        FOREIGN KEY(doctor_id)
        REFERENCES hospital.users(id)
);
```

---

## 10. Configure application.properties

File:

```text
backend/src/main/resources/application.properties
```

Configuration:

```properties
spring.application.name=backend

spring.datasource.url=jdbc:postgresql://<neon-host>/neondb?sslmode=require&channel_binding=require
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.schemas=hospital
spring.flyway.default-schema=hospital
```

---

## 11. Verify Flyway dependencies

```bash
./mvnw dependency:tree | grep flyway
```

Windows:

```powershell
.\mvnw.cmd dependency:tree | findstr flyway
```

Expected output:

```text
org.flywaydb:flyway-core
org.flywaydb:flyway-database-postgresql
```

---

## 12. Verify migration files

Git Bash:

```bash
ls src/main/resources/db/migration
```

PowerShell:

```powershell
dir src\main\resources\db\migration
```

---

## 13. Build the backend

```bash
./mvnw clean package
```

Windows:

```powershell
.\mvnw.cmd clean package
```

Generated artifact:

```text
backend/target/backend-0.0.1-SNAPSHOT.jar
```

---

## 14. Future commands (not implemented yet)

Docker:

```bash
docker build

docker push
```

Kubernetes:

```bash
kubectl apply -f k8s/
```

Helm:

```bash
helm install
```

ArgoCD:

```bash
argocd app create
```

Ansible:

```bash
ansible-playbook
```

GitHub Actions:

```text
.github/workflows/
```