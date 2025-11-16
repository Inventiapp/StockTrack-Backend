package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Product;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllProductsQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetProductByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service for Product aggregate.
 * Provides methods to fetch products by id or all products.
 */
public interface ProductQueryService {

    /**
     * Handle query to get a product by id.
     * @param query get product by id query
     * @return optional with found product
     */
    Optional<Product> handle(GetProductByIdQuery query);

    /**
     * Handle query to get all products.
     * @param query get all products query
     * @return list of products
     */
    List<Product> handle(GetAllProductsQuery query);
}
