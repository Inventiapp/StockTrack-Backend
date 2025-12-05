package com.inventiapp.stocktrack.reports.interfaces.rest.resources;

import java.util.List;

/**
 * Resource for complete dashboard data.
 * @param stats Dashboard statistics
 * @param monthlyIncome Income data by month
 * @param productSales Sales percentage by product
 * @param notifications Active notifications (low stock, expiring products)
 */
public record DashboardResource(
        DashboardStatsResource stats,
        List<MonthlyIncomeResource> monthlyIncome,
        List<ProductSalesResource> productSales,
        List<NotificationResource> notifications
) {}

