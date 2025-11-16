package com.inventiapp.stocktrack.sales.domain.model.queries;

public record GetSaleByIdQuery(Long saleId) {
    public GetSaleByIdQuery {
        if (saleId == null || saleId <= 0) {
            throw new IllegalArgumentException("Sale ID must be a positive number");
        }
    }
}
