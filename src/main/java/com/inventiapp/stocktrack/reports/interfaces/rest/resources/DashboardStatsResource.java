package com.inventiapp.stocktrack.reports.interfaces.rest.resources;

/**
 * Resource for dashboard statistics.
 * @param productsInInventory Total number of active products
 * @param monthlyIncome Total income for the current month
 * @param salesThisMonth Number of sales made this month
 * @param productsWithAlerts Products with stock below minimum
 */
public record DashboardStatsResource(
        int productsInInventory,
        double monthlyIncome,
        int salesThisMonth,
        int productsWithAlerts
) {}

