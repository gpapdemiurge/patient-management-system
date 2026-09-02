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

import com.gpapdemiurge.backend.entity.Patient;
import com.gpapdemiurge.backend.exception.ResourceNotFoundException;
import com.gpapdemiurge.backend.service.PatientService;

/**
 * REST controller exposing the {@code /api/patients} endpoints described in the
 * project README.
 *
 * <p>Endpoints:
 *
 * <pre>
 * POST   /api/patients       – create a new patient
 * GET    /api/patients       – list every patient
 * GET    /api/patients/{id}  – fetch a single patient
 * PUT    /api/patients/{id}  – update a patient
 * DELETE /api/patients/{id}  – delete a patient
 * </pre>
 *
 * The controller delegates persistence logic to {@link PatientService} and
 * returns standard HTTP status codes (200 OK, 201 Created, 404 Not Found).
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Creates a new patient record.
     *
     * @param patient the patient to create
     * @return {@code 201 Created} with the persisted entity
     */
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient created = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Returns every patient.
     *
     * @return {@code 200 OK} with a list of patients (may be empty)
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * Returns a single patient by id.
     *
     * @param id the patient id
     * @return {@code 200 OK} with the patient, or
     *         {@code 404 Not Found} if the id does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    /**
     * Updates an existing patient. Omitted (null) JSON properties are ignored
     * by the service and keep their existing values.
     *
     * @param id      the id of the patient to update
     * @param patient the new values
     * @return {@code 200 OK} with the updated patient, or
     *         {@code 404 Not Found} if the id does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id,
                                                 @RequestBody Patient patient) {
        Patient updated = patientService.updatePatient(id, patient);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a patient.
     *
     * @param id the id of the patient to delete
     * @return {@code 204 No Content}
     * @throws ResourceNotFoundException (mapped to {@code 404}) if the id does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
