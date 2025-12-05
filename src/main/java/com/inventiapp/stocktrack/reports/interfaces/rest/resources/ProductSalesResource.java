package com.inventiapp.stocktrack.reports.interfaces.rest.resources;

/**
 * Resource for product sales percentage.
 * @param productName Name of the product
 * @param percentage Percentage of total sales
 */
public record ProductSalesResource(
        String productName,
        double percentage
) {}

