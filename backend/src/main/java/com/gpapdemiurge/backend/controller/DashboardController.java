package com.gpapdemiurge.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminDashboard() {
        return ResponseEntity.ok(Map.of(
                "message", "Welcome to the admin dashboard",
                "stats", Map.of("users", 0, "patients", 0, "appointments", 0)
        ));
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> doctorDashboard(@PathVariable Long doctorId) {
        return ResponseEntity.ok(Map.of(
                "message", "Welcome to the doctor dashboard",
                "doctorId", doctorId,
                "todayAppointments", 0
        ));
    }
}
