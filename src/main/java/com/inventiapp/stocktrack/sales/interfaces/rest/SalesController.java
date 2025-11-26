package com.inventiapp.stocktrack.sales.interfaces.rest;

import com.inventiapp.stocktrack.sales.application.outboundservices.acl.ExternalInventoryService;
import com.inventiapp.stocktrack.sales.domain.model.commands.CreateSaleCommand;
import com.inventiapp.stocktrack.sales.domain.model.commands.SaleDetailItem;
import com.inventiapp.stocktrack.sales.domain.model.queries.GetAllSalesQuery;
import com.inventiapp.stocktrack.sales.domain.model.queries.GetSaleByIdQuery;
import com.inventiapp.stocktrack.sales.domain.services.SaleCommandService;
import com.inventiapp.stocktrack.sales.domain.services.SaleQueryService;
import com.inventiapp.stocktrack.sales.interfaces.rest.resources.CreateSaleDetailResource;
import com.inventiapp.stocktrack.sales.interfaces.rest.resources.CreateSaleResource;
import com.inventiapp.stocktrack.sales.interfaces.rest.resources.SaleResource;
import com.inventiapp.stocktrack.sales.interfaces.rest.transform.CreateSaleCommandFromResourceAssembler;
import com.inventiapp.stocktrack.sales.interfaces.rest.transform.SaleResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "api/v1/sales", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Sales", description = "Sales management API")
public class SalesController {

    private final SaleCommandService salesCommandService;
    private final SaleQueryService salesQueryService;
    private final ExternalInventoryService externalInventoryService;

    public SalesController(SaleCommandService salesCommandService, SaleQueryService salesQueryService, ExternalInventoryService externalInventoryService) {
        this.salesCommandService = salesCommandService;
        this.salesQueryService = salesQueryService;
        this.externalInventoryService = externalInventoryService;
    }

    @PostMapping
    @Operation(summary = "Create Sale", description = "Creates a new sale record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sale created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Related sale not found"),
    })
    public ResponseEntity<SaleResource> createSale(@RequestBody CreateSaleResource resource) {
        try {
            List<SaleDetailItem> details = new ArrayList<>();
            double totalAmount = 0.0;

            for (CreateSaleDetailResource item : resource.details()) {
                Double unitPrice = externalInventoryService.getProductUnitPrice(item.productId());
                if (unitPrice == null) {
                    return ResponseEntity.badRequest().build();
                }
                details.add(new SaleDetailItem(item.productId(), item.quantity(), unitPrice));
                totalAmount += unitPrice * item.quantity();
            }

            var createSaleCommand = CreateSaleCommandFromResourceAssembler.toCommandFromResource(resource);
            var saleId = salesCommandService.handle(createSaleCommand);
            if (saleId == null || saleId == 0L) {
                return ResponseEntity.badRequest().build();
            }
            var getSaleByIdQuery = new GetSaleByIdQuery(saleId);
            var sale = salesQueryService.handle(getSaleByIdQuery);
            if (sale.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var saleEntity = sale.get();
            var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(saleEntity);

            return new ResponseEntity<>(saleResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping({"/{id}"})
    @Operation(summary = "Get a sale by id", description = "Retrieves a sale by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
    })
    public ResponseEntity<SaleResource> getSaleById(@PathVariable Long id) {
        var getSaleByIdQuery = new GetSaleByIdQuery(id);
        var sale = salesQueryService.handle(getSaleByIdQuery);
        if (sale.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var saleEntity = sale.get();
        var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(saleEntity);
        return ResponseEntity.ok(saleResource);
    }


    @CrossOrigin(origins = "*")
    @GetMapping
    @Operation(summary = "Get all sales", description = "Retrieves all sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Sale not found"),
    })
    public ResponseEntity<List<SaleResource>> getAllSales() {
        var sales = salesQueryService.handle(new GetAllSalesQuery());
        var saleResources = sales.stream()
                .map(SaleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(saleResources);
    }
}

