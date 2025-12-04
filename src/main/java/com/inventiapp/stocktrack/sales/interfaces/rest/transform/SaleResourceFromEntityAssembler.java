package com.inventiapp.stocktrack.sales.interfaces.rest.transform;

import com.inventiapp.stocktrack.sales.domain.model.aggregates.Sale;
import com.inventiapp.stocktrack.sales.interfaces.rest.resources.SaleDetailResource;
import com.inventiapp.stocktrack.sales.interfaces.rest.resources.SaleResource;

import java.util.stream.Collectors;

public class SaleResourceFromEntityAssembler {

    public static SaleResource toResourceFromEntity(Sale entity) {
        var detailsResource = entity.getDetails().stream()
                .map(detail -> new SaleDetailResource(
                        detail.getId(),
                        detail.getProductId().id(),
                        detail.getQuantity(),
                        detail.getUnitPrice(),
                        detail.getTotalPrice()
                )).collect(Collectors.toList());


        return new SaleResource(
                entity.getId(),
                entity.getTotalAmount(),
                entity.getCreatedAt(),
                detailsResource
        );
    }
}
