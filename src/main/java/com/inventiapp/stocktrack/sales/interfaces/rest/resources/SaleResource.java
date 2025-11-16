package com.inventiapp.stocktrack.sales.interfaces.rest.resources;

import com.inventiapp.stocktrack.sales.domain.model.entities.SaleDetail;

import java.util.List;

public record SaleResource(Long id, double totalAmount, List<SaleDetailResource> details) {
}
