package com.inventiapp.stocktrack.sales.interfaces.rest.resources;


import java.util.Date;
import java.util.List;

public record SaleResource(Long id, double totalAmount, Date createdAt, List<SaleDetailResource> details) {
}
