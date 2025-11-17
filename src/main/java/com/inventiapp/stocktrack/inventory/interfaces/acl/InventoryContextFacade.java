package com.inventiapp.stocktrack.inventory.interfaces.acl;

public interface InventoryContextFacade {


    Long getProductById(Long productId);

    Boolean checkProductStockAvailability(Long productId, Integer requiredQuantity);

    void decreaseStock(Long productId, Integer quantity);

//    boolean existsProductById(Long productId);

    Double getProductUnitPrice(Long productId);

}
