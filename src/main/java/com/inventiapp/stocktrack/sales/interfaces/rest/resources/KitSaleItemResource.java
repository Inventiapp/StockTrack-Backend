package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KitSaleItemResource(
        @JsonProperty("kitId") Long kitId,
        @JsonProperty("quantity") int quantity) {
    
    public KitSaleItemResource {
        if (kitId == null || kitId <= 0) {
            throw new IllegalArgumentException("kitId must be provided and positive");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
    }
}

