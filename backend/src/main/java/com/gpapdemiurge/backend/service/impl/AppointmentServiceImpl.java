package com.gpapdemiurge.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gpapdemiurge.backend.entity.Appointment;
import com.gpapdemiurge.backend.entity.Patient;
import com.gpapdemiurge.backend.entity.User;
import com.gpapdemiurge.backend.exception.ResourceNotFoundException;
import com.gpapdemiurge.backend.repository.AppointmentRepository;
import com.gpapdemiurge.backend.repository.PatientRepository;
import com.gpapdemiurge.backend.repository.UserRepository;
import com.gpapdemiurge.backend.service.AppointmentService;

/**
 * Default {@link AppointmentService} implementation backed by Spring Data JPA
 * repositories ({@link AppointmentRepository}, {@link PatientRepository} and
 * {@link UserRepository}).
 *
 * <p>The entity-graph fetch strategy for {@code patient} and {@code doctor} is
 * {@code LAZY}, so when resolving the foreign keys passed in the
 * {@link Appointment} we eagerly load those rows here to avoid
 * {@link org.hibernate.LazyInitializationException} later in the request
 * cycle.
 */
@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private static final String ENTITY_NAME = "Appointment";
    private static final String ID_FIELD = "id";

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository; // lookup doctor by id

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  PatientRepository patientRepository,
                                  UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new appointment after resolving the {@code patient} and
     * {@code doctor} (a {@link User}) references from their ids, so that the
     * foreign-key columns are populated and the database constraints pass.
     *
     * @throws ResourceNotFoundException if either the patient or the doctor
     *         id does not exist
     * @throws IllegalArgumentException if {@code appointment} is null or is
     *         missing the patient / doctor references
     */
    @Override
    public Appointment createAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment must not be null");
        }
        if (appointment.getPatient() == null || appointment.getDoctor() == null) {
            throw new IllegalArgumentException(
                    "Appointment must specify both a patient and a doctor");
        }

        // Ensure the supplied patient/doctor actually exist (avoids
        // constraint-violation exceptions with a friendlier 404 instead).
        Patient patient = patientRepository.findById(appointment.getPatient().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + appointment.getPatient().getId()));
        User doctor = userRepository.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor (User) not found with id: " + appointment.getDoctor().getId()));

        // Replace the detached references with freshly loaded ones.
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        // Reset the id so JPA uses its IDENTITY strategy.
        appointment.setId(null);
        return appointmentRepository.save(appointment);
    }

    /** @return every appointment, eagerly fetching {@code patient} and {@code doctor}. */
    @Override
    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /** @throws ResourceNotFoundException if no appointment matches {@code id}. */
    @Override
    @Transactional(readOnly = true)
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ENTITY_NAME + " not found with " + ID_FIELD + ": " + id));
    }

    /**
     * Convenience lookup used by {@code GET /api/appointments/patient/{patientId}}.
     *
     * @param patientId the patient's id
     * @return the appointments for that patient (possibly empty)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        // Validate the patient exists so we return 404 instead of silently
        // returning an empty list for a bogus id.
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException(
                    "Patient not found with id: " + patientId);
        }
        return appointmentRepository.findByPatientId(patientId);
    }

    /**
     * Updates the mutable fields of an existing appointment.
     *
     * <p>Because the foreign-key references (patient/doctor) are relationships,
     * a non-null value means "reassign"; we re-resolve them the same way as in
     * {@link #createAppointment(Appointment)}. Omitted (null) fields keep
     * their existing values.
     */
    @Override
    public Appointment updateAppointment(Long id, Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment must not be null");
        }
        Appointment existing = getAppointmentById(id); // throws 404 when absent

        if (appointment.getPatient() != null) {
            Patient patient = patientRepository.findById(appointment.getPatient().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Patient not found with id: " + appointment.getPatient().getId()));
            existing.setPatient(patient);
        }
        if (appointment.getDoctor() != null) {
            User doctor = userRepository.findById(appointment.getDoctor().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Doctor (User) not found with id: " + appointment.getDoctor().getId()));
            existing.setDoctor(doctor);
        }
        if (appointment.getAppointmentDate() != null) {
            existing.setAppointmentDate(appointment.getAppointmentDate());
        }
        if (appointment.getReason() != null) {
            existing.setReason(appointment.getReason());
        }
        if (appointment.getStatus() != null) {
            existing.setStatus(appointment.getStatus());
        }

        return appointmentRepository.save(existing);
    }

    /** @throws ResourceNotFoundException if no appointment matches {@code id}. */
    @Override
    public void deleteAppointment(Long id) {
        Appointment existing = getAppointmentById(id);
        appointmentRepository.delete(existing);
    }
}
