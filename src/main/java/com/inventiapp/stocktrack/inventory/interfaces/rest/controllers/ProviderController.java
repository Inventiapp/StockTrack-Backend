package com.inventiapp.stocktrack.inventory.interfaces.rest.controllers;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllProvidersQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetProviderByIdQuery;
import com.inventiapp.stocktrack.inventory.domain.services.ProviderCommandService;
import com.inventiapp.stocktrack.inventory.domain.services.ProviderQueryService;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.CreateProviderResource;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.ProviderResource;
import com.inventiapp.stocktrack.inventory.interfaces.rest.transform.CreateProviderCommandFromResourceAssembler;
import com.inventiapp.stocktrack.inventory.interfaces.rest.transform.ProviderResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Provider REST controller
 * @summary
 * This controller exposes endpoints to create and query Provider aggregates.
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/providers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Providers", description = "Endpoints for providers")
public class ProviderController {

    private final ProviderCommandService providerCommandService;
    private final ProviderQueryService providerQueryService;

    public ProviderController(ProviderCommandService providerCommandService,
                              ProviderQueryService providerQueryService) {
        this.providerCommandService = providerCommandService;
        this.providerQueryService = providerQueryService;
    }

    /**
     * Create a new provider.
     *
     * @param resource the create provider resource
     * @return 201 Created with ProviderResource body on success, 400 Bad Request on validation errors
     */
    @Operation(summary = "Create a provider", description = "Creates a provider with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Provider created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProviderResource> createProvider(@Valid @RequestBody CreateProviderResource resource) {
        try {
            var command = CreateProviderCommandFromResourceAssembler.toCommandFromResource(resource);
            Optional<Provider> created = providerCommandService.handle(command);

            if (created.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Provider provider = created.get();
            ProviderResource response = ProviderResourceFromEntityAssembler.toResourceFromEntity(provider);
            return ResponseEntity.created(URI.create("/api/v1/providers/" + provider.getId()))
                    .body(response);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get a provider by id.
     *
     * @param id provider id
     * @return 200 OK with ProviderResource or 404 Not Found
     */
    @Operation(summary = "Get provider by id", description = "Fetch a provider by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Provider found"),
            @ApiResponse(responseCode = "404", description = "Provider not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProviderResource> getById(@PathVariable Long id) {
        try {
            var query = new GetProviderByIdQuery(id);
            var opt = providerQueryService.handle(query);
            return opt.map(ProviderResourceFromEntityAssembler::toResourceFromEntity)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all providers.
     *
     * @return 200 OK with list of ProviderResource
     */
    @Operation(summary = "Get all providers", description = "Retrieve all providers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Providers found")
    })
    @GetMapping
    public ResponseEntity<List<ProviderResource>> getAll() {
        var query = new GetAllProvidersQuery();
        var providers = providerQueryService.handle(query);
        var resources = providers.stream()
                .map(ProviderResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }
}
