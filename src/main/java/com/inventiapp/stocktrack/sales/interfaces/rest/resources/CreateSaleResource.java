package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

public record CreateSaleResource(double totalAmount) {
    public CreateSaleResource {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }
    }

}
