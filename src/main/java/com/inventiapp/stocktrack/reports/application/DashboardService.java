package com.inventiapp.stocktrack.reports.application;

import com.inventiapp.stocktrack.reports.interfaces.rest.resources.DashboardResource;

/**
 * Service interface for dashboard operations.
 */
public interface DashboardService {
    
    /**
     * Gets the complete dashboard data including stats, charts, and notifications.
     * @return DashboardResource with all dashboard information
     */
    DashboardResource getDashboardData();
}

