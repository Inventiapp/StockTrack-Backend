package com.inventiapp.stocktrack.inventory.interfaces.rest.resources;

public record UpdateBatchResource(
        Long id,
        Integer quantity
) {

    public UpdateBatchResource(Long id, Integer quantity) {

        if (quantity != null && quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.id = id;
        this.quantity = quantity;

    }
}
