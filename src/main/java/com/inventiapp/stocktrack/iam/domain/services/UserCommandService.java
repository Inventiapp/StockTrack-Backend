package com.inventiapp.stocktrack.iam.domain.services;

import com.inventiapp.stocktrack.iam.domain.model.aggregates.User;
import com.inventiapp.stocktrack.iam.domain.model.commands.CreateUserCommand;
import com.inventiapp.stocktrack.iam.domain.model.commands.SignInCommand;
import com.inventiapp.stocktrack.iam.domain.model.commands.SignUpCommand;
import com.inventiapp.stocktrack.iam.domain.model.commands.UpdateUserCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

/**
 * Service interface for user command operations
 */
public interface UserCommandService {

    /**
     * Handle sign-in command
     * @param command The sign-in command
     * @return Optional pair of User and JWT token
     */
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);

    /**
     * Handle sign-up command (creates ADMIN user - business owner)
     * @param command The sign-up command
     * @return Optional User if created successfully
     */
    Optional<User> handle(SignUpCommand command);

    /**
     * Handle create user command (from staff management with specific role)
     * @param command The create user command
     * @return Optional User if created successfully
     */
    Optional<User> handle(CreateUserCommand command);

    /**
     * Handle update user command (from staff management)
     * @param command The update user command
     * @return Optional User if updated successfully
     */
    Optional<User> handle(UpdateUserCommand command);

    /**
     * Delete a user by ID
     * @param userId The user ID to delete
     */
    void deleteUser(Long userId);
}

