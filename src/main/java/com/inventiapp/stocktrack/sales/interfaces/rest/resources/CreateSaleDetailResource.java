package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateSaleDetailResource(
        @JsonProperty("productId") Long productId, 
        @JsonProperty("kitId") Long kitId, 
        @JsonProperty("quantity") int quantity) {
    
    public CreateSaleDetailResource {
        // Normalize: treat null or <= 0 as not provided
        Long validProductId = (productId != null && productId > 0) ? productId : null;
        Long validKitId = (kitId != null && kitId > 0) ? kitId : null;
        
        // Either productId or kitId must be provided, but not both
        if (validProductId == null && validKitId == null) {
            throw new IllegalArgumentException("Either productId or kitId must be provided and positive");
        }
        if (validProductId != null && validKitId != null) {
            throw new IllegalArgumentException("Cannot provide both productId and kitId. Provide only one.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
    }
}