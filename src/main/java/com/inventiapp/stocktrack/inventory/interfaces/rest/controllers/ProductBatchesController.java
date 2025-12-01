package com.inventiapp.stocktrack.inventory.interfaces.rest.controllers;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Batch;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllBatchesByProductIdQuery;
import com.inventiapp.stocktrack.inventory.domain.services.BatchQueryService;
import com.inventiapp.stocktrack.inventory.domain.services.ProductQueryService;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.BatchResource;
import com.inventiapp.stocktrack.inventory.interfaces.rest.transform.BatchResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/products/{productId}/batches", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Endpoints for product batches")
public class ProductBatchesController {

    private final ProductQueryService productQueryService;
    private final BatchQueryService batchQueryService;

    public ProductBatchesController(ProductQueryService productQueryService,
                                    BatchQueryService batchQueryService) {
        this.productQueryService = productQueryService;
        this.batchQueryService = batchQueryService;
    }

    @GetMapping
    @Operation(summary = "Get batches by product id", description = "Retrieves batches by product id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful retrieval of batches"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public ResponseEntity<List<BatchResource>> getAllBatchesByProductId(@PathVariable Long productId) {
        try {
            var getAllBatchesByProductIdQuery = new GetAllBatchesByProductIdQuery(productId);
            var batches = batchQueryService.handle(getAllBatchesByProductIdQuery);
            var batchesResources = batches.stream()
                    .map(BatchResourceFromEntityAssembler::toResource)
                    .toList();
            return ResponseEntity.ok(batchesResources);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
