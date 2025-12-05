package com.inventiapp.stocktrack.iam.domain.services;

import com.inventiapp.stocktrack.iam.domain.model.aggregates.User;
import com.inventiapp.stocktrack.iam.domain.model.commands.SignInCommand;
import com.inventiapp.stocktrack.iam.domain.model.commands.SignUpCommand;
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
     * Handle sign-up command
     * @param command The sign-up command
     * @return Optional User if created successfully
     */
    Optional<User> handle(SignUpCommand command);
}

