package com.gpapdemiurge.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gpapdemiurge.backend.entity.Patient;
import com.gpapdemiurge.backend.exception.ResourceNotFoundException;
import com.gpapdemiurge.backend.repository.PatientRepository;
import com.gpapdemiurge.backend.service.PatientService;

/**
 * Default {@link PatientService} implementation backed by a Spring Data JPA
 * {@link PatientRepository}.
 *
 * <p>Each mutating operation runs inside a transaction so partial writes are
 * rolled back should an error occur mid-method.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private static final String ENTITY_NAME = "Patient";
    private static final String ID_FIELD = "id";

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Persists a new patient record.
     *
     * <p>{@code id} and {@code createdAt} are generated, so callers only need
     * the patient's personal information.
     *
     * @throws IllegalArgumentException if {@code patient} is {@code null}
     */
    @Override
    public Patient createPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient must not be null");
        }
        // Prevent callers from specifying a pre-existing id.
        patient.setId(null);
        return patientRepository.save(patient);
    }

    /** @return every stored patient. */
    @Override
    @Transactional(readOnly = true)
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /** @throws ResourceNotFoundException if no patient matches {@code id}. */
    @Override
    @Transactional(readOnly = true)
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ENTITY_NAME + " not found with " + ID_FIELD + ": " + id));
    }

    /**
     * Updates the fields of an existing patient.
     *
     * <p>Only the personal fields are mutable; {@code id} and {@code createdAt}
     * are left untouched, mirroring the README's {@code PUT /api/patients/{id}}
     * contract. Null-valued JSON properties are skipped so that omitted fields
     * keep their existing database values.
     */
    @Override
    public Patient updatePatient(Long id, Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient must not be null");
        }
        Patient existing = getPatientById(id); // throws 404 when absent

        if (patient.getFirstName() != null) {
            existing.setFirstName(patient.getFirstName());
        }
        if (patient.getLastName() != null) {
            existing.setLastName(patient.getLastName());
        }
        if (patient.getDateOfBirth() != null) {
            existing.setDateOfBirth(patient.getDateOfBirth());
        }
        if (patient.getGender() != null) {
            existing.setGender(patient.getGender());
        }
        if (patient.getPhone() != null) {
            existing.setPhone(patient.getPhone());
        }
        if (patient.getEmail() != null) {
            existing.setEmail(patient.getEmail());
        }
        if (patient.getAddress() != null) {
            existing.setAddress(patient.getAddress());
        }

        return patientRepository.save(existing);
    }

    /** @throws ResourceNotFoundException if no patient matches {@code id}. */
    @Override
    public void deletePatient(Long id) {
        Patient existing = getPatientById(id);
        patientRepository.delete(existing);
    }
}
