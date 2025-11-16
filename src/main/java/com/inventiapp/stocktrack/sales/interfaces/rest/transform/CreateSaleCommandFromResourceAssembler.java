package com.inventiapp.stocktrack.sales.interfaces.rest.transform;

import com.inventiapp.stocktrack.sales.domain.model.commands.CreateSaleCommand;
import com.inventiapp.stocktrack.sales.interfaces.rest.resources.CreateSaleResource;

public class CreateSaleCommandFromResourceAssembler {

    public static CreateSaleCommand toCommandFromResource(CreateSaleResource resource) {
        return new CreateSaleCommand(
                resource.staffUserId(),
                resource.totalAmount(),
                resource.details()
        );
    }
}
