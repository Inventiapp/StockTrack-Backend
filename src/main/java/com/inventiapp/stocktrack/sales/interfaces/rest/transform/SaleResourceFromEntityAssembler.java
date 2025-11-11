package com.inventiapp.stocktrack.sales.interfaces.rest.transform;

import com.inventiapp.stocktrack.sales.domain.model.aggregates.Sale;
import com.inventiapp.stocktrack.sales.interfaces.rest.resources.SaleResource;

public class SaleResourceFromEntityAssembler {
    public static SaleResource toResourceFromEntity(Sale entity) {
        return new SaleResource(
                entity.getId(),
                entity.getTotalAmount()
        );
    }
}
