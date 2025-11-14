package com.inventiapp.stocktrack.inventory.interfaces.rest;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Category;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllCategoriesQuery;
import com.inventiapp.stocktrack.inventory.domain.services.CategoryCommandService;
import com.inventiapp.stocktrack.inventory.domain.services.CategoryQueryService;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.CategoryResource;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.CreateCategoryResource;
import com.inventiapp.stocktrack.inventory.interfaces.rest.transform.CategoryResourceFromEntityAssembler;
import com.inventiapp.stocktrack.inventory.interfaces.rest.transform.CreateCategoryCommandFromResourceAssembler;
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
 * REST controller for categories.
 * @summary
 * This class provides REST endpoints for categories.
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/categories", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Categories", description = "Endpoints for categories")
public class CategoryController {

    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    /**
     * Constructor for CategoryController.
     * @param categoryCommandService Category command service
     * @param categoryQueryService Category query service
     * @since 1.0
     * @see CategoryCommandService
     * @see CategoryQueryService
     */
    public CategoryController(
            CategoryCommandService categoryCommandService,
            CategoryQueryService categoryQueryService) {
        this.categoryCommandService = categoryCommandService;
        this.categoryQueryService = categoryQueryService;
    }

    /**
     * Creates a category.
     * @param resource CreateCategoryResource containing the name and description
     * @return ResponseEntity with the created category resource, or bad request if the resource is invalid
     * @since 1.0
     * @see CreateCategoryResource
     * @see CategoryResource
     */
    @Operation(
            summary = "Create a category",
            description = "Creates a category with the provided name and description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<CategoryResource> createCategory(@Valid @RequestBody CreateCategoryResource resource) {
        try {
            var category = categoryCommandService
                    .handle(CreateCategoryCommandFromResourceAssembler.toCommandFromResource(resource));
            return category.map(cat -> new ResponseEntity<>(
                    CategoryResourceFromEntityAssembler.toResourceFromEntity(cat), CREATED))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Gets all categories.
     * @return ResponseEntity with the list of category resources
     * @since 1.0
     * @see CategoryResource
     */
    @Operation(
            summary = "Get all categories",
            description = "Gets all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories found")
    })
    @GetMapping
    public ResponseEntity<List<CategoryResource>> getAllCategories() {
        var getAllCategoriesQuery = new GetAllCategoriesQuery();
        var categories = categoryQueryService.handle(getAllCategoriesQuery);
        var categoryResources = categories.stream()
                .map(CategoryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(categoryResources);
    }
}

