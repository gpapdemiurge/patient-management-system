package com.gpapdemiurge.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gpapdemiurge.backend.entity.Appointment;
import com.gpapdemiurge.backend.exception.ResourceNotFoundException;
import com.gpapdemiurge.backend.service.AppointmentService;

/**
 * REST controller exposing the {@code /api/appointments} endpoints described in
 * the project README.
 *
 * <p>Endpoints:
 *
 * <pre>
 * POST   /api/appointments        – create a new appointment
 * GET    /api/appointments        – list every appointment
 * GET    /api/appointments/{id}   – fetch a single appointment
 * PUT    /api/appointments/{id}   – update an appointment
 * DELETE /api/appointments/{id}   – delete an appointment
 * </pre>
 *
 * The controller delegates persistence logic to {@link AppointmentService}
 * and returns standard HTTP status codes (200 OK, 201 Created, 404 Not Found,
 * 400 Bad Request).
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Creates a new appointment. The request body must supply valid {@code
     * patientId} and {@code doctorId} references so that the service can resolve
     * the foreign keys before persisting.
     *
     * @param appointment the appointment to create
     * @return {@code 201 Created} with the persisted entity in the body
     */
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment created = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Returns every appointment.
     *
     * @return {@code 200 OK} with a list of appointments (may be empty)
     */
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    /**
     * Returns a single appointment by its database id.
     *
     * @param id the appointment id
     * @return {@code 200 OK} with the appointment, or
     *         {@code 404 Not Found} if the id does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    /**
     * Updates the fields of an existing appointment. Omitted (null) JSON
     * properties are ignored by the service and keep their existing values.
     *
     * @param id          the id of the appointment to update
     * @param appointment the new values
     * @return {@code 200 OK} with the updated appointment, or
     *         {@code 404 Not Found} if the id does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id,
                                                         @RequestBody Appointment appointment) {
        Appointment updated = appointmentService.updateAppointment(id, appointment);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes an appointment.
     *
     * @param id the id of the appointment to delete
     * @return {@code 204 No Content}
     * @throws ResourceNotFoundException (mapped to {@code 404}) if the id does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
