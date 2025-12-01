package com.inventiapp.stocktrack.inventory.domain.model.entities;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Kit;
import com.inventiapp.stocktrack.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * KitItem Entity
 * 
 * @summary
 * Represents a product item within a kit with its associated price.
 * Each kit can contain multiple products, each with its own price.
 * @since 1.0
 */
@Entity
@Table(name = "kit_items")
@Getter
@NoArgsConstructor
public class KitItem extends AuditableModel {

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kit_id", nullable = false)
    private Kit kit;

    /**
     * Constructor for KitItem.
     * @param kit The kit this item belongs to
     * @param productId The product ID
     * @param price The price of the product in this kit
     */
    public KitItem(Kit kit, Long productId, Double price) {
        if (kit == null) {
            throw new IllegalArgumentException("Kit cannot be null");
        }
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        this.kit = kit;
        this.productId = productId;
        this.price = price;
    }
}

