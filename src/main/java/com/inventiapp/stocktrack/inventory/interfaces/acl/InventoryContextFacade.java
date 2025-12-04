package com.inventiapp.stocktrack.inventory.interfaces.acl;

import java.util.List;

public interface InventoryContextFacade {


    Long getProductById(Long productId);

    Boolean checkProductStockAvailability(Long productId, Integer requiredQuantity);

    void decreaseStock(Long productId, Integer quantity);

//    boolean existsProductById(Long productId);

    Double getProductUnitPrice(Long productId);

    /**
     * Get kit by id.
     * @param kitId The kit id
     * @return The kit id if exists, null otherwise
     */
    Long getKitById(Long kitId);

    /**
     * Get kit total price.
     * @param kitId The kit id
     * @return The total price of the kit (sum of all item prices), null if kit doesn't exist
     */
    Double getKitTotalPrice(Long kitId);

    /**
     * Get all product IDs, quantities and prices from a kit.
     * @param kitId The kit id
     * @return List of arrays [productId (Long), quantity (Integer), price (Double)] for each item in the kit, empty list if kit doesn't exist
     */
    List<Object[]> getKitProductIdsQuantitiesAndPrices(Long kitId);

    /**
     * Decrease stock for all products in a kit.
     * @param kitId The kit id
     * @param kitQuantity The quantity of kits to sell
     */
    void decreaseStockForKit(Long kitId, Integer kitQuantity);

}
