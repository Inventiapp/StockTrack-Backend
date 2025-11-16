package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Product;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.ProductResource;

/**
 * Assembler to convert a Product aggregate to a ProductResource.
 */
public class ProductResourceFromEntityAssembler {
    /**
     * Converts a Product entity into a ProductResource.
     *
     * @param product the Product aggregate
     * @return ProductResource for API responses
     */
    public static ProductResource toResource(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        return new ProductResource(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategoryId(),
                product.getProviderId(),
                product.getMinStock(),
                product.getUnitPrice(),
                product.getIsActive()
        );
    }
}
