package com.inventiapp.stocktrack.reports.interfaces.rest.resources;

/**
 * Resource for monthly income data.
 * @param month Month name (e.g., "Jan", "Feb")
 * @param amount Income amount for the month
 */
public record MonthlyIncomeResource(
        String month,
        double amount
) {}

