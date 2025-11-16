package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

public record SaleDetailResource(
    Long id,
    Long productId,
    int quantity,
    double unitPrice,
    double totalPrice
) {}