package com.inventiapp.stocktrack.iam.interfaces.rest;

import com.inventiapp.stocktrack.iam.application.internal.outboundservices.tokens.TokenService;
import com.inventiapp.stocktrack.iam.domain.services.UserCommandService;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.SignInResource;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.SignUpResource;
import com.inventiapp.stocktrack.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.inventiapp.stocktrack.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.inventiapp.stocktrack.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication endpoints
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {

    private final UserCommandService userCommandService;
    private final TokenService tokenService;

    public AuthenticationController(UserCommandService userCommandService, TokenService tokenService) {
        this.userCommandService = userCommandService;
        this.tokenService = tokenService;
    }

    /**
     * Sign in endpoint
     * @param signInResource The sign-in resource
     * @return Authenticated user resource with JWT token
     */
    @PostMapping("/sign-in")
    @Operation(summary = "Sign in", description = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var result = userCommandService.handle(signInCommand);
        
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        var user = result.get().getLeft();
        var token = result.get().getRight();
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(user, token);
        
        return ResponseEntity.ok(authenticatedUserResource);
    }

    /**
     * Sign up endpoint
     * @param signUpResource The sign-up resource
     * @return Authenticated user resource with JWT token
     */
    @PostMapping("/sign-up")
    @Operation(summary = "Sign up", description = "Register a new user and automatically authenticate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created and authenticated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data or email already exists")
    })
    public ResponseEntity<AuthenticatedUserResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var result = userCommandService.handle(signUpCommand);
        
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        var user = result.get();
        // Generate token for the newly created user
        var token = tokenService.generateToken(user.getEmail());
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(user, token);
        
        return new ResponseEntity<>(authenticatedUserResource, HttpStatus.CREATED);
    }
}

