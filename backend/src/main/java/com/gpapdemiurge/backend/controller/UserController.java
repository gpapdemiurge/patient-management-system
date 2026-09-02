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

import com.gpapdemiurge.backend.entity.User;
import com.gpapdemiurge.backend.exception.ResourceNotFoundException;
import com.gpapdemiurge.backend.service.UserService;

/**
 * REST controller exposing the {@code /api/users} endpoints described in the
 * project README.
 *
 * <p>Endpoints:
 *
 * <pre>
 * POST   /api/users       – create a new user (admin, doctor, etc.)
 * GET    /api/users       – list every user
 * GET    /api/users/{id}  – fetch a single user
 * PUT    /api/users/{id}  – update a user
 * DELETE /api/users/{id}  – delete a user
 * </pre>
 *
 * The controller delegates persistence logic to {@link UserService} and returns
 * standard HTTP status codes (200 OK, 201 Created, 404 Not Found).
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new application user.
     *
     * @param user the user to create (caller should set {@code role})
     * @return {@code 201 Created} with the persisted entity
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Returns every user.
     *
     * @return {@code 200 OK} with a list of users (may be empty)
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Returns a single user by id.
     *
     * @param id the user id
     * @return {@code 200 OK} with the user, or
     *         {@code 404 Not Found} if the id does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Updates an existing user. Omitted (null) JSON properties are ignored by
     * the service and keep their existing values.
     *
     * @param id   the id of the user to update
     * @param user the new values
     * @return {@code 200 OK} with the updated user, or
     *         {@code 404 Not Found} if the id does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a user.
     *
     * @param id the id of the user to delete
     * @return {@code 204 No Content}
     * @throws ResourceNotFoundException (mapped to {@code 404}) if the id does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
