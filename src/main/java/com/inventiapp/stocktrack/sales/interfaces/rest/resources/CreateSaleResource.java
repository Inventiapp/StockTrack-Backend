package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

import com.inventiapp.stocktrack.sales.domain.model.commands.SaleDetailItem;

import java.util.List;

public record CreateSaleResource(long staffUserId, double totalAmount, List<SaleDetailItem> details) {
    public CreateSaleResource {
        if (staffUserId <= 0) {
            throw new IllegalArgumentException("Staff user ID must be positive");
        }
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }
        if (details == null) {
            throw new IllegalArgumentException("Sale details cannot be null");
        }
    }

}
