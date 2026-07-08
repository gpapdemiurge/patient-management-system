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