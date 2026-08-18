package com.gpapdemiurge.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gpapdemiurge.backend.entity.User;

/**
 * Spring Data JPA repository for {@link User} entities.
 *
 * <p>Beyond the CRUD operations inherited from {@link JpaRepository},
 * this interface exposes a lookup by {@code username} which is required
 * by the authentication flow (registration uniqueness check and login).
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their unique username.
     *
     * @param username the username to look up (case-sensitive, exact match)
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    Optional<User> findByUsername(String username);
}
