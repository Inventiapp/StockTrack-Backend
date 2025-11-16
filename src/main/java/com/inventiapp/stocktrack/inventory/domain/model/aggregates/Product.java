package com.inventiapp.stocktrack.inventory.domain.model.aggregates;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProductCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateProductCommand;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

/**
 * Product Aggregate Root
 *
 * Represents a product in inventory.
 * Contains basic validation and inherits audit fields.
 */
@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
public class Product extends AuditableAbstractAggregateRoot<Product> {

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String categoryId;

    @Column(nullable = false)
    private String providerId;

    @Column(nullable = false)
    private Integer minStock;

    @Column(nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Boolean isActive;

    /**
     * Creates a new Product aggregate from the CreateProductCommand.
     *
     * @param command The create product command
     */
    public Product(CreateProductCommand command) {

        if (command == null) throw new IllegalArgumentException("CreateProductCommand is required");

        if (command.name() == null || command.name().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (command.categoryId() == null || command.categoryId().isBlank()) {
            throw new IllegalArgumentException("Category id is required");
        }
        if (command.providerId() == null || command.providerId().isBlank()) {
            throw new IllegalArgumentException("Provider id is required");
        }
        if (command.minStock() == null || command.minStock() < 0) {
            throw new IllegalArgumentException("minStock must be a non-negative number");
        }
        if (command.unitPrice() == null || command.unitPrice() <= 0) {
            throw new IllegalArgumentException("unitPrice must be greater than 0");
        }
        if (command.isActive() == null) {
            throw new IllegalArgumentException("isActive is required");
        }

        this.name = normalize(command.name());
        this.description = command.description() != null ? command.description().trim() : null;
        this.categoryId = command.categoryId().trim();
        this.providerId = command.providerId().trim();
        this.minStock = command.minStock();
        this.unitPrice = command.unitPrice();
        this.isActive = command.isActive();
    }

    /**
     * Updates the product using an UpdateProductCommand.
     * Only validated values from the command are applied; validation matches the command contract.
     *
     * @param command the update command
     */
    public void update(UpdateProductCommand command) {
        if (command == null) throw new IllegalArgumentException("UpdateProductCommand is required");

        if (command.productId() == null || command.productId() <= 0) {
            throw new IllegalArgumentException("productId must be a positive number");
        }
        if (command.name() == null || command.name().isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (command.categoryId() == null || command.categoryId().isBlank()) {
            throw new IllegalArgumentException("categoryId cannot be null or blank");
        }
        if (command.providerId() == null || command.providerId().isBlank()) {
            throw new IllegalArgumentException("providerId cannot be null or blank");
        }
        if (command.minStock() == null || command.minStock() < 0) {
            throw new IllegalArgumentException("minStock cannot be null or negative");
        }
        if (command.unitPrice() == null || command.unitPrice() <= 0) {
            throw new IllegalArgumentException("unitPrice must be greater than 0");
        }
        if (command.isActive() == null) {
            throw new IllegalArgumentException("isActive cannot be null");
        }

        this.name = normalize(command.name());
        this.description = command.description() != null ? command.description().trim() : null;
        this.categoryId = command.categoryId().trim();
        this.providerId = command.providerId().trim();
        this.minStock = command.minStock();
        this.unitPrice = command.unitPrice();
        this.isActive = command.isActive();
    }

    /**
     * Normalizes text values to trimmed strings; converts null to empty string.
     *
     * @param value input string
     * @return normalized string
     */
    private String normalize(String value) {
        return value == null ? Strings.EMPTY : value.trim();
    }
}