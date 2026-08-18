Phase 2: Build the REST API
The backend currently only has BackendApplication.java and a Flyway logger config. The database schema is in place, so now you need to mirror it with JPA entities and expose endpoints.

Step 1 — JPA Entities (mirror the hospital schema)
Create three entities under com.gpapdemiurge.backend.entity:

User.java → maps to hospital.users (id, username, email, password, role, created_at)
Patient.java → maps to hospital.patients (id, first_name, last_name, date_of_birth, gender, phone, email, address, created_at)
Appointment.java → maps to hospital.appointments (id, patient_id, doctor_id, appointment_date, reason, status) with @ManyToOne to Patient and User
Use Lombok (@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, @Builder) and JPA annotations (@Entity, @Table(name=..., schema="hospital"), @Id, @GeneratedValue, @Column).

Step 2 — Repositories
Create three interfaces under com.gpapdemiurge.backend.repository extending JpaRepository<Entity, Long>:

UserRepository — add Optional<User> findByUsername(String username)
PatientRepository
AppointmentRepository — add List<Appointment> findByPatientId(Long patientId)
Step 3 — DTOs
Under com.gpapdemiurge.backend.dto, create request/response DTOs to decouple API from entities (e.g., PatientRequest, PatientResponse, AppointmentRequest, AppointmentResponse). Use Jakarta Validation annotations (@NotBlank, @Email, @Size, etc.).

Step 4 — Service Layer
Under com.gpapdemiurge.backend.service, create interfaces + implementations:

PatientService — CRUD + pagination
AppointmentService — CRUD, plus business rules (e.g., can't book appointment in the past)
UserService — registration with BCrypt password hashing
Mark with @Service and @Transactional.

Step 5 — REST Controllers
Under com.gpapdemiurge.backend.controller:

PatientController — GET /api/patients, GET /api/patients/{id}, POST, PUT, DELETE
AppointmentController — GET /api/appointments, GET /api/appointments/patient/{patientId}, POST, PUT, DELETE
AuthController — POST /api/auth/register, POST /api/auth/login (returns JWT later)
Use @RestController, @RequestMapping, @Valid, and proper HTTP status codes (ResponseEntity).

Step 6 — Exception Handling
Create GlobalExceptionHandler with @RestControllerAdvice to return consistent error responses for ResourceNotFoundException, validation errors, etc.

Step 7 — CORS Configuration
Since the frontend runs on http://localhost:5173, add a CorsConfig class to allow that origin (and the production domain later).

Step 8 — Test It

Then use curl or Postman to hit http://localhost:8080/api/patients and verify CRUD works against Neon.

