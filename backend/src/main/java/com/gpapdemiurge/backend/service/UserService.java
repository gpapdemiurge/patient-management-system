package com.gpapdemiurge.backend.service;

import java.util.List;

import com.gpapdemiurge.backend.entity.User;

/**
 * Service interface defining the business operations for managing application
 * users (admins, doctors, nurses, receptionists, etc...).
 *
 * <p>The methods here mirror the {@code /api/users} REST endpoints described in
 * the project README and delegate the actual persistence work to
 * {@link com.gpapdemiurge.backend.repository.UserRepository}.
 */
public interface UserService {

    /**
     * Creates a new application user.
     *
     * @param user the user to persist (the caller is responsible for setting
     *             the {@code role} and providing a non-null {@code password})
     * @return the persisted user, including its generated {@code id} and
     *         {@code createdAt} timestamp
     */
    User createUser(User user);

    /**
     * Returns every user currently stored in the database.
     *
     * @return a list of all users; never {@code null}, but may be empty
     */
    List<User> getAllUsers();

    /**
     * Retrieves a single user by its primary key.
     *
     * @param id the user id
     * @return the user with the given id
     * @throws com.gpapdemiurge.backend.exception.ResourceNotFoundException if no user exists for the id
     */
    User getUserById(Long id);

    /**
     * Updates the fields of an existing user.
     *
     * @param id   the id of the user to update
     * @param user the new values to apply (only non-null fields are copied onto the
     *             existing entity)
     * @return the updated user
     * @throws com.gpapdemiurge.backend.exception.ResourceNotFoundException if no user exists for the id
     */
    User updateUser(Long id, User user);

    /**
     * Permanently deletes a user.
     *
     * @param id the id of the user to delete
     * @throws com.gpapdemiurge.backend.exception.ResourceNotFoundException if no user exists for the id
     */
    void deleteUser(Long id);
}
