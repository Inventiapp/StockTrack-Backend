package com.inventiapp.stocktrack.inventory.interfaces.rest.controllers;

import com.inventiapp.stocktrack.inventory.domain.exceptions.BatchNotFoundException;
import com.inventiapp.stocktrack.inventory.domain.exceptions.ProductNotFoundException;
import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Batch;
import com.inventiapp.stocktrack.inventory.domain.model.commands.DeleteBatchCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateBatchCommand;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllBatchesQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetBatchByIdQuery;
import com.inventiapp.stocktrack.inventory.domain.services.BatchCommandService;
import com.inventiapp.stocktrack.inventory.domain.services.BatchQueryService;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.BatchResource;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.CreateBatchResource;
import com.inventiapp.stocktrack.inventory.interfaces.rest.transform.BatchResourceFromEntityAssembler;
import com.inventiapp.stocktrack.inventory.interfaces.rest.transform.CreateBatchCommandFromResourceAssembler;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/batches", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Batches", description = "Endpoints for batches")
public class BatchController {

    private final BatchCommandService batchCommandService;
    private final BatchQueryService batchQueryService;

    public BatchController(BatchCommandService batchCommandService,
                           BatchQueryService batchQueryService) {
        this.batchCommandService = batchCommandService;
        this.batchQueryService = batchQueryService;
    }

    @Operation(summary = "Create a batch", description = "Creates a new batch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Batch created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<BatchResource> createBatch(@Valid @RequestBody CreateBatchResource resource) {
        try {
            var command = CreateBatchCommandFromResourceAssembler.toCommandFromResource(resource);
            Long createdId = batchCommandService.handle(command);

            var opt = batchQueryService.handle(new GetBatchByIdQuery(createdId));
            return opt.map(batch -> {
                        BatchResource response = BatchResourceFromEntityAssembler.toResource(batch);
                        return ResponseEntity.created(URI.create("/api/v1/batches/" + createdId)).body(response);
                    })
                    .orElseGet(() -> ResponseEntity.badRequest().build());

        } catch (IllegalArgumentException | ProductNotFoundException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get batch by id", description = "Fetch a batch by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Batch found"),
            @ApiResponse(responseCode = "404", description = "Batch not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BatchResource> getById(@PathVariable Long id) {
        try {
            Optional<Batch> opt = batchQueryService.handle(new GetBatchByIdQuery(id));
            return opt.map(BatchResourceFromEntityAssembler::toResource)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get all batches", description = "Retrieve all batches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Batches found")
    })
    @GetMapping
    public ResponseEntity<List<BatchResource>> getAll() {
        List<Batch> batches = batchQueryService.handle(new GetAllBatchesQuery());
        List<BatchResource> resources = batches.stream()
                .map(BatchResourceFromEntityAssembler::toResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Delete a batch", description = "Deletes a batch by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Batch deleted"),
            @ApiResponse(responseCode = "404", description = "Batch not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
        try {
            var command = new DeleteBatchCommand(id);
            batchCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (BatchNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update a batch", description = "Updates an existing batch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Batch updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Batch not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BatchResource> updateBatch(@PathVariable Long id, @Valid @RequestBody CreateBatchResource resource) {
        try {
            var command = new UpdateBatchCommand(id, resource.quantity());
            Optional<Batch> updatedOpt = batchCommandService.handle(command);
            return updatedOpt.map(BatchResourceFromEntityAssembler::toResource)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (BatchNotFoundException | ProductNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
