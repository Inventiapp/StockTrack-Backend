package com.inventiapp.stocktrack.sales.domain.model.valueobjects;

public record ProductId(Long id) {
    public ProductId {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
    }
}
