package com.gpapdemiurge.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gpapdemiurge.backend.entity.Appointment;

/**
 * Spring Data JPA repository for {@link Appointment} entities.
 *
 * <p>Provides standard CRUD operations plus a derived query to fetch
 * every appointment that belongs to a given patient — used by the
 * {@code GET /api/appointments/patient/{patientId}} endpoint.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Returns all appointments for the given patient, in the order
     * the database returns them (no explicit sort).
     *
     * @param patientId the id of the patient whose appointments to fetch
     * @return a list of appointments (possibly empty, never {@code null})
     */
    List<Appointment> findByPatientId(Long patientId);
}
