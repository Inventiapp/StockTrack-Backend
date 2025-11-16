package com.inventiapp.stocktrack.sales.domain.model.commands;

public record SaleDetailItem(Long productId, int quantity, double unitPrice) {}
