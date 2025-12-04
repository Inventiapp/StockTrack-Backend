package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateSaleResource(
        @JsonProperty("staffUserId") long staffUserId,
        @JsonProperty("products") List<ProductSaleItemResource> products,
        @JsonProperty("kits") List<KitSaleItemResource> kits) {
    
    public CreateSaleResource {
        if (staffUserId <= 0) {
            throw new IllegalArgumentException("Staff user ID must be positive");
        }
        
        // At least one product or kit must be provided
        boolean hasProducts = products != null && !products.isEmpty();
        boolean hasKits = kits != null && !kits.isEmpty();
        
        if (!hasProducts && !hasKits) {
            throw new IllegalArgumentException("At least one product or kit must be provided");
        }
    }
}