package com.inventiapp.stocktrack.iam.interfaces.rest;

import com.inventiapp.stocktrack.iam.application.internal.outboundservices.tokens.TokenService;
import com.inventiapp.stocktrack.iam.domain.model.queries.GetAllUsersQuery;
import com.inventiapp.stocktrack.iam.domain.model.queries.GetUserByIdQuery;
import com.inventiapp.stocktrack.iam.domain.services.UserCommandService;
import com.inventiapp.stocktrack.iam.domain.services.UserQueryService;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.CreateUserResource;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.UpdateUserResource;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.UserResource;
import com.inventiapp.stocktrack.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.inventiapp.stocktrack.iam.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import com.inventiapp.stocktrack.iam.interfaces.rest.transform.UpdateUserCommandFromResourceAssembler;
import com.inventiapp.stocktrack.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UsersController
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class UsersController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final TokenService tokenService;

    /**
     * Constructor
     * @param userQueryService The {@link UserQueryService} instance
     * @param userCommandService The {@link UserCommandService} instance
     * @param tokenService The {@link TokenService} instance
     */
    public UsersController(UserQueryService userQueryService, UserCommandService userCommandService, TokenService tokenService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
        this.tokenService = tokenService;
    }

    /**
     * Get all users
     * @return A list of {@link UserResource} resources for all users, or an unauthorized response if not authenticated
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve all users in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var query = new GetAllUsersQuery();
        var users = userQueryService.handle(query);
        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(userResources);
    }

    /**
     * Get user by ID
     * @param userId The user ID
     * @return A {@link UserResource} resource for the user, or a not found response if the user could not be found
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var query = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(query);
        
        return user
                .map(u -> ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new user (from staff management)
     * Automatically authenticates the user and returns a JWT token
     * @param resource The {@link CreateUserResource} instance with email, password and permissions
     * @return A {@link AuthenticatedUserResource} resource for the created and authenticated user with JWT token, or a bad request response if the user could not be created
     */
    @PostMapping
    @Operation(summary = "Create user", description = "Create a new staff user with specified permissions and automatically authenticate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created and authenticated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data or email already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<AuthenticatedUserResource> createUser(@RequestBody CreateUserResource resource) {
        var command = CreateUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        
        return result
                .map(user -> {
                    var token = tokenService.generateToken(
                            user.getId(),
                            user.getEmail(),
                            user.getRolesAsStringList()
                    );
                    var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(user, token);
                    return new ResponseEntity<>(authenticatedUserResource, HttpStatus.CREATED);
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Update a user (from staff management)
     * @param userId The user ID to update
     * @param resource The {@link UpdateUserResource} instance with email, password (optional) and permissions
     * @return A {@link UserResource} resource for the updated user, or a not found response if the user could not be found
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Update user", description = "Update a staff user's email, password and permissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data or email already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResource> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserResource resource) {
        var command = UpdateUserCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var result = userCommandService.handle(command);
        
        return result
                .map(user -> ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete a user
     * @param userId The user ID to delete
     * @return A no content response if the user was deleted successfully, or a not found response if the user could not be found
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Delete a staff user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userCommandService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
