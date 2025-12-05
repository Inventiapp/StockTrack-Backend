package com.inventiapp.stocktrack.sales.interfaces.rest.transform;

import com.inventiapp.stocktrack.sales.application.outboundservices.acl.ExternalInventoryService;
import com.inventiapp.stocktrack.sales.domain.model.commands.CreateSaleCommand;
import com.inventiapp.stocktrack.sales.domain.model.commands.SaleDetailItem;
import com.inventiapp.stocktrack.sales.interfaces.rest.resources.CreateSaleResource;
import org.springframework.stereotype.Component;

import java.util.List;


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

        List<SaleDetailItem> details = new java.util.ArrayList<>();

        // Process products
        if (resource.products() != null) {
            for (var productItem : resource.products()) {
                Long productId = productItem.productId();
                
                // Validate product exists
                if (!inventoryService.checkProductExists(productId)) {
                    throw new IllegalArgumentException("Producto no encontrado: " + productId);
                }
                
                // Get product unit price
                Double unitPrice = inventoryService.getProductUnitPrice(productId);
                if (unitPrice == null) {
                    throw new IllegalArgumentException("Producto sin precio: " + productId);
                }
                
                details.add(new SaleDetailItem(productId, productItem.quantity(), unitPrice));
            }
        }

        // Process kits
        if (resource.kits() != null) {
            for (var kitItem : resource.kits()) {
                Long kitId = kitItem.kitId();
                
                // Validate kit exists
                if (!inventoryService.checkKitExists(kitId)) {
                    throw new IllegalArgumentException("Kit no encontrado: " + kitId);
                }
                
                // Get kit total price
                Double kitTotalPrice = inventoryService.getKitTotalPrice(kitId);
                if (kitTotalPrice == null) {
                    throw new IllegalArgumentException("Kit no encontrado o sin precio: " + kitId);
                }
                
                // Get all products, quantities and prices from the kit
                List<Object[]> kitItems = inventoryService.getKitProductIdsQuantitiesAndPrices(kitId);
                if (kitItems.isEmpty()) {
                    throw new IllegalArgumentException("Kit vacío: " + kitId);
                }
                
                // For each product in the kit, create a SaleDetailItem
                // The price in the kit is already the unit price per product
                // The quantity for sale is: (item quantity in kit * kit quantity sold)
                for (Object[] item : kitItems) {
                    Long productId = (Long) item[0];
                    Integer itemQuantity = (Integer) item[1];
                    Double itemPrice = (Double) item[2];
                    
                    // Validate itemQuantity
                    if (itemQuantity == null || itemQuantity <= 0) {
                        throw new IllegalArgumentException("Kit item con cantidad inválida para producto " + productId + " en kit " + kitId);
                    }
                    
                    // The price in the kit is already the unit price (not total)
                    // Example: if product 4 has quantity 2 and price 3, then:
                    // - Unit price = 3 (already unitario)
                    // - Total for 2 units = 2 * 3 = 6
                    double unitPrice = itemPrice;
                    
                    // Total quantity to sell: item quantity in kit * number of kits sold
                    int totalQuantity = itemQuantity * kitItem.quantity();
                    
                    details.add(new SaleDetailItem(productId, totalQuantity, unitPrice));
                }
            }
        }

        double total = details.stream()
                .mapToDouble(i -> i.unitPrice() * i.quantity())
                .sum();

        return new CreateSaleCommand(resource.staffUserId(), total, details);
    }
}