package com.inventiapp.stocktrack.sales.domain.model.valueobjects;

import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

public record StaffUserId(Long id) {
    public StaffUserId {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Staff User ID must be a positive number");
        }
    }
}
