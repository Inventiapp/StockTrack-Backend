package com.inventiapp.stocktrack.sales.domain.model.commands;

import com.inventiapp.stocktrack.sales.domain.model.entities.SaleDetail;

import java.util.List;

public record CreateSaleCommand(long staffUserId, double totalAmount, List<SaleDetailItem> details) {
    public CreateSaleCommand {

        if (staffUserId <= 0) {
            throw new IllegalArgumentException("Staff user ID must be positive");
        }
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }

        if (details == null || details.isEmpty()) { // También es bueno validar que no esté vacía
            throw new IllegalArgumentException("Sale details cannot be null or empty");
        }
    }
}

