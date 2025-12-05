package com.inventiapp.stocktrack.reports.interfaces.rest;

import com.inventiapp.stocktrack.reports.application.DashboardService;
import com.inventiapp.stocktrack.reports.interfaces.rest.resources.DashboardResource;
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

/**
 * REST Controller for dashboard endpoints.
 * Provides aggregated data from multiple bounded contexts for the dashboard view.
 */
@RestController
@RequestMapping(value = "/api/v1/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Dashboard", description = "Dashboard endpoints for aggregated statistics and reports")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Gets complete dashboard data including statistics, charts, and notifications.
     * @return Dashboard resource with all aggregated data
     */
    @GetMapping
    @Operation(
            summary = "Get dashboard data",
            description = "Retrieves aggregated dashboard data including statistics, monthly income, " +
                    "product sales distribution, and notifications for low stock and expiring products"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dashboard data retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - valid token required")
    })
    public ResponseEntity<DashboardResource> getDashboard() {
        DashboardResource dashboard = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboard);
    }
}

