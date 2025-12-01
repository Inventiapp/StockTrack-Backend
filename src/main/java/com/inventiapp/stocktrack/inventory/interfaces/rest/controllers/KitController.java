package com.inventiapp.stocktrack.inventory.interfaces.rest.controllers;

import com.inventiapp.stocktrack.inventory.domain.exceptions.KitNotFoundException;
import com.inventiapp.stocktrack.inventory.domain.model.commands.DeleteKitCommand;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllKitsQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetKitByIdQuery;
import com.inventiapp.stocktrack.inventory.domain.services.KitCommandService;
import com.inventiapp.stocktrack.inventory.domain.services.KitQueryService;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.CreateKitResource;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.KitResource;
import com.inventiapp.stocktrack.inventory.interfaces.rest.transform.CreateKitCommandFromResourceAssembler;
import com.inventiapp.stocktrack.inventory.interfaces.rest.transform.KitResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for kits.
 * 
 * @summary
 * This class provides REST endpoints for kits.
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/kits", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Kits", description = "Endpoints for kits")
public class KitController {

    private final KitCommandService kitCommandService;
    private final KitQueryService kitQueryService;

    /**
     * Constructor for KitController.
     * @param kitCommandService Kit command service
     * @param kitQueryService Kit query service
     * @since 1.0
     * @see KitCommandService
     * @see KitQueryService
     */
    public KitController(
            KitCommandService kitCommandService,
            KitQueryService kitQueryService) {
        this.kitCommandService = kitCommandService;
        this.kitQueryService = kitQueryService;
    }

    /**
     * Creates a kit.
     * @param resource CreateKitResource containing the name and products with prices
     * @return ResponseEntity with the created kit resource, or bad request if the resource is invalid
     * @since 1.0
     * @see CreateKitResource
     * @see KitResource
     */
    @Operation(
            summary = "Create a kit",
            description = "Creates a kit with the provided name and products with prices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Kit created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<KitResource> createKit(@Valid @RequestBody CreateKitResource resource) {
        try {
            var kit = kitCommandService
                    .handle(CreateKitCommandFromResourceAssembler.toCommandFromResource(resource));
            return kit.map(k -> new ResponseEntity<>(
                    KitResourceFromEntityAssembler.toResourceFromEntity(k), CREATED))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Gets all kits.
     * @return ResponseEntity with the list of kit resources
     * @since 1.0
     * @see KitResource
     */
    @Operation(
            summary = "Get all kits",
            description = "Gets all kits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kits found")
    })
    @GetMapping
    public ResponseEntity<List<KitResource>> getAllKits() {
        var getAllKitsQuery = new GetAllKitsQuery();
        var kits = kitQueryService.handle(getAllKitsQuery);
        var kitResources = kits.stream()
                .map(KitResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(kitResources);
    }

    /**
     * Gets a kit by id.
     * @param id The kit id
     * @return ResponseEntity with the kit resource, or not found if the kit doesn't exist
     * @since 1.0
     * @see KitResource
     */
    @Operation(
            summary = "Get a kit by id",
            description = "Gets a kit by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kit found"),
            @ApiResponse(responseCode = "404", description = "Kit not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<KitResource> getKitById(@PathVariable Long id) {
        try {
            var query = new GetKitByIdQuery(id);
            var kit = kitQueryService.handle(query);
            return kit.map(k -> ResponseEntity.ok(
                    KitResourceFromEntityAssembler.toResourceFromEntity(k)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes a kit.
     * @param id The kit id
     * @return ResponseEntity with no content if successful, or not found if the kit doesn't exist
     * @since 1.0
     */
    @Operation(
            summary = "Delete a kit",
            description = "Deletes a kit by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Kit deleted"),
            @ApiResponse(responseCode = "404", description = "Kit not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKit(@PathVariable Long id) {
        try {
            var command = new DeleteKitCommand(id);
            kitCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (KitNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}

