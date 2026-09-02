package com.gpapdemiurge.backend.service;

import java.util.List;

import com.gpapdemiurge.backend.entity.Patient;

/**
 * Service interface defining the business operations for managing patient
 * records.
 *
 * <p>The methods here mirror the {@code /api/patients} REST endpoints described
 * in the project README and delegate the actual persistence work to
 * {@link com.gpapdemiurge.backend.repository.PatientRepository}.
 */
public interface PatientService {

    /**
     * Creates a new patient record.
     *
     * @param patient the patient to persist
     * @return the persisted patient, including its generated {@code id} and
     *         {@code createdAt} timestamp
     */
    Patient createPatient(Patient patient);

    /**
     * Returns every patient currently stored in the database.
     *
     * @return a list of all patients; never {@code null}, but may be empty
     */
    List<Patient> getAllPatients();

    /**
     * Retrieves a single patient by its primary key.
     *
     * @param id the patient id
     * @return the patient with the given id
     * @throws com.gpapdemiurge.backend.exception.ResourceNotFoundException if no patient exists for the id
     */
    Patient getPatientById(Long id);

    /**
     * Updates the fields of an existing patient.
     *
     * @param id      the id of the patient to update
     * @param patient the new values to apply (only non-null fields are copied
     *                onto the existing entity)
     * @return the updated patient
     * @throws com.gpapdemiurge.backend.exception.ResourceNotFoundException if no patient exists for the id
     */
    Patient updatePatient(Long id, Patient patient);

    /**
     * Permanently deletes a patient.
     *
     * @param id the id of the patient to delete
     * @throws com.gpapdemiurge.backend.exception.ResourceNotFoundException if no patient exists for the id
     */
    void deletePatient(Long id);
}
