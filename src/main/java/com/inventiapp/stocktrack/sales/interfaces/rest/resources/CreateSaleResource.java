package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

import com.inventiapp.stocktrack.sales.domain.model.commands.SaleDetailItem;

import java.util.List;

public record CreateSaleResource(long staffUserId, List<CreateSaleDetailResource> details) {
    public CreateSaleResource {
        if (staffUserId <= 0) {
            throw new IllegalArgumentException("Staff user ID must be positive");
        }
        if (details == null || details.isEmpty()) {
            throw new IllegalArgumentException("Sale details cannot be null or empty");
        }
    }
}