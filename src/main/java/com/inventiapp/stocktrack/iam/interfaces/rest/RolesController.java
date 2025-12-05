package com.inventiapp.stocktrack.iam.interfaces.rest;

import com.inventiapp.stocktrack.iam.domain.model.queries.GetAllRolesQuery;
import com.inventiapp.stocktrack.iam.domain.services.RoleQueryService;
import com.inventiapp.stocktrack.iam.interfaces.rest.resources.RoleResource;
import com.inventiapp.stocktrack.iam.interfaces.rest.transform.RoleResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for role endpoints
 */
@RestController
@RequestMapping(value = "/api/v1/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Roles", description = "Role Management Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class RolesController {

    private final RoleQueryService roleQueryService;

    public RolesController(RoleQueryService roleQueryService) {
        this.roleQueryService = roleQueryService;
    }

    /**
     * Get all roles
     * @return List of role resources
     */
    @GetMapping
    @Operation(summary = "Get all roles", description = "Retrieve all roles in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<RoleResource>> getAllRoles() {
        var query = new GetAllRolesQuery();
        var roles = roleQueryService.handle(query);
        var roleResources = roles.stream()
                .map(RoleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(roleResources);
    }
}

