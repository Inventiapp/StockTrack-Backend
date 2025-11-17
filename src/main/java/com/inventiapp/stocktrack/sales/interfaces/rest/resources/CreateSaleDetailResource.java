package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

public record CreateSaleDetailResource(Long productId, int quantity) {
    public CreateSaleDetailResource {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId must be positive");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
    }
}