package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductSaleItemResource(
        @JsonProperty("productId") Long productId,
        @JsonProperty("quantity") int quantity) {
    
    public ProductSaleItemResource {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId must be provided and positive");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
    }
}

