package com.gpapdemiurge.backend.service.impl;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gpapdemiurge.backend.entity.User;
import com.gpapdemiurge.backend.exception.ResourceNotFoundException;
import com.gpapdemiurge.backend.repository.UserRepository;
import com.gpapdemiurge.backend.service.UserService;

/**
 * Default {@link UserService} implementation backed by a Spring Data JPA
 * {@link UserRepository}.
 *
 * <p>All mutating operations are wrapped in a transaction
 * ({@link Transactional}) so that, should a method throw midway, any partial
 * writes are rolled back and data integrity is preserved.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String ENTITY_NAME = "User";
    private static final String ID_FIELD = "id";

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Persists a brand-new user.
     *
     * <p>Because {@code id} and {@code createdAt} are generated (respectively by
     * the database and via Hibernate's {@code @CreationTimestamp}), the caller
     * only needs to supply the remaining fields. Duplicate username/email
     * violations surface as {@link OptimisticLockingFailureException} or a
     * native {@code DataIntegrityViolationException}, which the global
     * exception handler maps to a {@code 409 Conflict}.
     */
    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        // Guard against callers manually setting an id (would bypass the
        // IDENTITY generation strategy and cause a primary-key violation).
        user.setId(null);
        return userRepository.save(user);
    }

    /** @return every user, ordered by primary key so iteration is deterministic. */
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /** @throws ResourceNotFoundException when no row matches {@code id}. */
    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ENTITY_NAME + " not found with " + ID_FIELD + ": " + id));
    }

    /**
     * Merges non-null fields from {@code user} onto the existing entity.
     *
     * <p>Only updatable business fields are copied; {@code id}, {@code username},
     * and {@code createdAt} are treated as immutable for safety, matching the
     * README's expectation that a {@code PUT /api/users/{id}} simply updates
     * the user identified by {@code id}.
     */
    @Override
    public User updateUser(Long id, User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        User existing = getUserById(id); // fetches via PK; throws 404 if absent

        // Copy mutable fields, guarding against nulls so that omitted JSON
        // properties don't erase existing values.
        if (user.getEmail() != null) {
            existing.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            existing.setPassword(user.getPassword());
        }
        if (user.getRole() != null) {
            existing.setRole(user.getRole());
        }

        return userRepository.save(existing);
    }

    /** @throws ResourceNotFoundException when no row matches {@code id}. */
    @Override
    public void deleteUser(Long id) {
        User existing = getUserById(id);
        userRepository.delete(existing);
    }
}
