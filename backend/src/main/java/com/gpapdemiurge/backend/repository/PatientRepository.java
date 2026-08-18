package com.gpapdemiurge.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gpapdemiurge.backend.entity.Patient;

/**
 * Spring Data JPA repository for {@link Patient} entities.
 *
 * <p>Provides standard CRUD and pagination operations out of the box
 * via {@link JpaRepository}. Custom query methods can be added here as
 * the API grows (e.g. search by last name, filter by creation date).
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
