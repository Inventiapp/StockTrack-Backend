package com.inventiapp.stocktrack.inventory.domain.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super("Product with ID %s was not found".formatted(productId));
    }
}
