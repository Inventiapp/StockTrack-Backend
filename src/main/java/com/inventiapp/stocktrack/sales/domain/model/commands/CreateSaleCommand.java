package com.inventiapp.stocktrack.sales.domain.model.commands;

public record CreateSaleCommand(double totalAmount) {
    public CreateSaleCommand {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }
    }
}
