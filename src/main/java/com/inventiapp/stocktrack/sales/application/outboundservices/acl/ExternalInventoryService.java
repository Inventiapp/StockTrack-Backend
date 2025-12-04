package com.inventiapp.stocktrack.sales.application.outboundservices.acl;

import com.inventiapp.stocktrack.inventory.interfaces.acl.InventoryContextFacade;
import com.inventiapp.stocktrack.sales.domain.model.aggregates.Sale;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalInventoryService {
    private final InventoryContextFacade inventoryContextFacade;

    public ExternalInventoryService(InventoryContextFacade inventoryContextFacade) {
        this.inventoryContextFacade = inventoryContextFacade;
    }


    public void decreaseStockForSale(Sale sale) {
        sale.getDetails().forEach(detail -> {
            Long productId = detail.getProductId().id();
            int quantity = detail.getQuantity();
            inventoryContextFacade.decreaseStock(productId, quantity);
        });
    }

    public boolean checkStockForSale(Sale sale) {


        return sale.getDetails().stream().allMatch(detail ->
                inventoryContextFacade.checkProductStockAvailability(
                        detail.getProductId().id(),
                        detail.getQuantity()
                )
        );
    }

    public boolean checkProductExists(Long productId) {
        Long result = inventoryContextFacade.getProductById(productId);
        return result != null;
    }

    public Double getProductUnitPrice(Long productId) {
        return inventoryContextFacade.getProductUnitPrice(productId);
    }

    public boolean checkKitExists(Long kitId) {
        Long result = inventoryContextFacade.getKitById(kitId);
        return result != null;
    }

    public Double getKitTotalPrice(Long kitId) {
        return inventoryContextFacade.getKitTotalPrice(kitId);
    }

    public List<Object[]> getKitProductIdsQuantitiesAndPrices(Long kitId) {
        return inventoryContextFacade.getKitProductIdsQuantitiesAndPrices(kitId);
    }

}
