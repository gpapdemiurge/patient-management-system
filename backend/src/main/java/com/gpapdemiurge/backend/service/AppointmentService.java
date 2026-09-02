package com.gpapdemiurge.backend.service;

import java.util.List;

import com.gpapdemiurge.backend.entity.Appointment;

/**
 * Service interface defining the business operations for managing appointments
 * between a patient and a doctor ({@link com.gpapdemiurge.backend.entity.User}).
 *
 * <p>The methods here mirror the {@code /api/appointments} REST endpoints
 * described in the project README and delegate the actual persistence work to
 * {@link com.gpapdemiurge.backend.repository.AppointmentRepository}.
 */
public interface AppointmentService {

    /**
     * Creates a new appointment.
     *
     * <p>The caller must supply a valid {@code patientId} and {@code doctorId}
     * (the {@link com.gpapdemiurge.backend.entity.User} acting as the doctor)
     * so that the foreign-key relationships can be resolved before the
     * appointment is persisted.
     *
     * @param appointment the appointment to persist (with patient and doctor
     *                    references already set)
     * @return the persisted appointment, including its generated {@code id}
     */
    Appointment createAppointment(Appointment appointment);

    /**
     * Returns every appointment currently stored in the database.
     *
     * @return a list of all appointments; never {@code null}, but may be empty
     */
    List<Appointment> getAllAppointments();

    /**
     * Retrieves a single appointment by its primary key.
     *
     * @param id the appointment id
     * @return the appointment with the given id
     * @throws com.gpapdemiurge.backend.exception.ResourceNotFoundException if no appointment exists for the id
     */
    Appointment getAppointmentById(Long id);

    /**
     * Returns the appointments that are linked to a specific patient.
     *
     * @param patientId the id of the patient whose appointments to fetch
     * @return a list of appointments (possibly empty, never {@code null})
     */
    List<Appointment> getAppointmentsByPatientId(Long patientId);

    /**
     * Updates the fields of an existing appointment.
     *
     * @param id          the id of the appointment to update
     * @param appointment the new values to apply (only non-null fields are copied
     *                    onto the existing entity)
     * @return the updated appointment
     * @throws com.gpapdemiurge.backend.exception.ResourceNotFoundException if no appointment exists for the id
     */
    Appointment updateAppointment(Long id, Appointment appointment);

    /**
     * Permanently deletes an appointment.
     *
     * @param id the id of the appointment to delete
     * @throws com.gpapdemiurge.backend.exception.ResourceNotFoundException if no appointment exists for the id
     */
    void deleteAppointment(Long id);
}
