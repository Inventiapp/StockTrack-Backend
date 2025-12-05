package com.inventiapp.stocktrack.iam.interfaces.acl;

import com.inventiapp.stocktrack.iam.domain.model.commands.SignUpCommand;
import com.inventiapp.stocktrack.iam.domain.model.entities.Role;
import com.inventiapp.stocktrack.iam.domain.model.queries.GetUserByIdQuery;
import com.inventiapp.stocktrack.iam.domain.model.queries.GetUserByEmailQuery;
import com.inventiapp.stocktrack.iam.domain.services.UserCommandService;
import com.inventiapp.stocktrack.iam.domain.services.UserQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade for IAM context - Anti-Corruption Layer
 * Exposes IAM functionality to other bounded contexts
 */
@Service
public class IamContextFacade {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public IamContextFacade(UserCommandService userCommandService, 
                           UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    /**
     * Create a user with default role
     * @param email The email
     * @param password The password
     * @return The created user's ID, or 0 if creation failed
     */
    public Long createUser(String email, String password) {
        var signUpCommand = new SignUpCommand(email, password, new ArrayList<>());
        var result = userCommandService.handle(signUpCommand);
        return result.map(user -> user.getId()).orElse(0L);
    }

    /**
     * Create a user with specific roles
     * @param email The email
     * @param password The password
     * @param roleNames List of role names
     * @return The created user's ID, or 0 if creation failed
     */
    public Long createUser(String email, String password, List<String> roleNames) {
        var roles = roleNames.stream()
                .map(Role::toRoleFromName)
                .toList();
        var signUpCommand = new SignUpCommand(email, password, roles);
        var result = userCommandService.handle(signUpCommand);
        return result.map(user -> user.getId()).orElse(0L);
    }

    /**
     * Get user ID by email
     * @param email The email
     * @return The user ID, or 0 if not found
     */
    public Long fetchUserIdByEmail(String email) {
        var query = new GetUserByEmailQuery(email);
        var result = userQueryService.handle(query);
        return result.map(user -> user.getId()).orElse(0L);
    }

    /**
     * Get email by user ID
     * @param userId The user ID
     * @return The email, or empty string if not found
     */
    public String fetchEmailByUserId(Long userId) {
        var query = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(query);
        return result.map(user -> user.getEmail()).orElse("");
    }
}

