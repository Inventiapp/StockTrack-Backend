package com.inventiapp.stocktrack.inventory.application.internal.commandservices;

import com.inventiapp.stocktrack.inventory.domain.exceptions.ProductAlreadyExistsException;
import com.inventiapp.stocktrack.inventory.domain.exceptions.ProductNotFoundException;
import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Product;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProductCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.DeleteProductCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateProductCommand;
import com.inventiapp.stocktrack.inventory.domain.model.events.ProductCreatedEvent;
import com.inventiapp.stocktrack.inventory.domain.model.events.ProductDeletedEvent;
import com.inventiapp.stocktrack.inventory.domain.model.events.ProductUpdatedEvent;
import com.inventiapp.stocktrack.inventory.domain.services.ProductCommandService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of ProductCommandService.
 * @summary
 * Performs domain operations for Product aggregate: create, update and delete.
 * Exceptions from persistence layer are translated into domain-friendly exceptions.
 * @since 1.0
 */
@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    public ProductCommandServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Handles the creation of a product.
     * Validates that no product with the same name and provider exists.
     * Registers a ProductCreatedEvent on the aggregate.
     *
     * @param command CreateProductCommand with product data
     * @return generated product id
     * @throws ProductAlreadyExistsException if a product with the same name and provider already exists
     */
    @Override
    public Long handle(CreateProductCommand command) {

        if (productRepository.existsByNameAndProviderId(command.name(), command.providerId())) {
            throw new ProductAlreadyExistsException(command.name());
        }

        Product product = new Product(command);

        product.addDomainEvent(new ProductCreatedEvent(
                product,
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategoryId(),
                product.getProviderId(),
                product.getMinStock(),
                product.getUnitPrice(),
                product.getIsActive()
        ));

        try {
            Product saved = productRepository.save(product);
            return saved.getId();
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error saving product: " +
                    (ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage()));
        }
    }

    /**
     * Handles updating a product.
     * The aggregate's updateProduct(...) method updates the product information.
     * Registers a ProductUpdatedEvent on the aggregate.
     *
     * @param command UpdateProductCommand containing product id and updated values
     * @return Optional with updated product if exists
     * @throws ProductNotFoundException if the product does not exist
     */
    @Override
    public Optional<Product> handle(UpdateProductCommand command) {
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new ProductNotFoundException(command.productId()));

        product.updateProduct(command);

        product.addDomainEvent(new ProductUpdatedEvent(
                product,
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategoryId(),
                product.getProviderId(),
                product.getMinStock(),
                product.getUnitPrice(),
                product.getIsActive()
        ));

        try {
            return Optional.of(productRepository.save(product));
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error updating product: " +
                    (ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage()));
        }
    }

    /**
     * Handles deletion of a product.
     * Registers a ProductDeletedEvent on the aggregate before deleting from repository.
     *
     * @param command DeleteProductCommand containing product id
     * @throws ProductNotFoundException if the product does not exist
     */
    @Override
    public void handle(DeleteProductCommand command) {
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new ProductNotFoundException(command.productId()));

        product.addDomainEvent(new ProductDeletedEvent(product, product.getId()));

        try {
            productRepository.delete(product);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error deleting product: " +
                    (ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage()));
        }
    }
}