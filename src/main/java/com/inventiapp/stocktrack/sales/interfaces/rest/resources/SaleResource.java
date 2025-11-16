package com.inventiapp.stocktrack.sales.interfaces.rest.resources;


import java.util.List;

public record SaleResource(Long id, double totalAmount, List<SaleDetailResource> details) {
}
