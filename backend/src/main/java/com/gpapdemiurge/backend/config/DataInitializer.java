package com.gpapdemiurge.backend.config;

import com.gpapdemiurge.backend.entity.Role;
import com.gpapdemiurge.backend.entity.User;
import com.gpapdemiurge.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds convenient local users when the `local` profile is active.
 * This helps manual testing without requiring a registration UI.
 */
@Component
@Profile("local")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("doctor").isEmpty()) {
            User doctor = User.builder()
                    .username("doctor")
                    .email("doctor@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.DOCTOR)
                    .build();
            userRepository.save(doctor);
        }
    }
}
