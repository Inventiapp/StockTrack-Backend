package com.inventiapp.stocktrack.sales.interfaces.rest.transform;

import com.inventiapp.stocktrack.sales.application.outboundservices.acl.ExternalInventoryService;
import com.inventiapp.stocktrack.sales.domain.model.commands.CreateSaleCommand;
import com.inventiapp.stocktrack.sales.domain.model.commands.SaleDetailItem;
import com.inventiapp.stocktrack.sales.interfaces.rest.resources.CreateSaleResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CreateSaleCommandFromResourceAssembler {

    private static ExternalInventoryService inventoryService;

    public CreateSaleCommandFromResourceAssembler(ExternalInventoryService inventoryService) {
        CreateSaleCommandFromResourceAssembler.inventoryService = inventoryService;
    }

    public static CreateSaleCommand toCommandFromResource(CreateSaleResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("resource cannot be null");
        }

        List<SaleDetailItem> details = resource.details().stream()
                .map(d -> {
                    Double unitPrice = inventoryService.getProductUnitPrice(d.productId());
                    if (unitPrice == null) {
                        throw new IllegalArgumentException("Producto no encontrado: " + d.productId());
                    }
                    return new SaleDetailItem(d.productId(), d.quantity(), unitPrice);
                })
                .collect(Collectors.toList());

        double total = details.stream()
                .mapToDouble(i -> i.unitPrice() * i.quantity())
                .sum();

        return new CreateSaleCommand(resource.staffUserId(), total, details);
    }
}