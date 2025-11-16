package com.inventiapp.stocktrack.sales.domain.model.valueobjects;


public record StaffUserId(Long id) {
    public StaffUserId {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Staff User ID must be a positive number");
        }
    }
}
