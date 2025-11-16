package com.inventiapp.stocktrack.inventory.application.internal.queryservices;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Product;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllProductsQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetProductByIdQuery;
import com.inventiapp.stocktrack.inventory.domain.services.ProductQueryService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of ProductQueryService.
 * @summary
 * Provides read operations for Product aggregate: list all and find by id.
 * Read methods are marked as read-only transactions.
 * @since 1.0
 */
@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Handle query to get a product by id.
     * @param query GetProductByIdQuery
     * @return optional with product if found
     */
    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        return productRepository.findById(query.productId());
    }

    /**
     * Handle query to get all products.
     * @param query GetAllProductsQuery
     * @return list of products
     */
    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        return productRepository.findAll();
    }
}