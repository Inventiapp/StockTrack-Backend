package com.inventiapp.stocktrack.inventory.domain.model.aggregates;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateKitCommand;
import com.inventiapp.stocktrack.inventory.domain.model.entities.KitItem;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Kit Aggregate Root
 * 
 * @summary
 * Represents a kit that contains multiple products, each with its own price.
 * A kit has a name and a collection of products (KitItems).
 * @since 1.0
 */
@Entity
@Table(name = "kits")
@Getter
@NoArgsConstructor
public class Kit extends AuditableAbstractAggregateRoot<Kit> {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(
            mappedBy = "kit",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<KitItem> items = new ArrayList<>();

    /**
     * Constructor for Kit.
     * Creates a new Kit instance based on the CreateKitCommand.
     * @param command The create kit command
     */
    public Kit(CreateKitCommand command) {
        if (command.name() == null || command.name().isBlank()) {
            throw new IllegalArgumentException("Kit name is required");
        }
        if (command.items() == null || command.items().isEmpty()) {
            throw new IllegalArgumentException("Kit must have at least one product");
        }
        
        this.name = command.name().trim();
        this.items = new ArrayList<>();
        
        for (var item : command.items()) {
            if (item.productId() == null || item.productId() <= 0) {
                throw new IllegalArgumentException("Product ID must be a positive number");
            }
            if (item.quantity() == null || item.quantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
            if (item.price() == null || item.price() <= 0) {
                throw new IllegalArgumentException("Price must be greater than 0");
            }
            this.items.add(new KitItem(this, item.productId(), item.quantity(), item.price()));
        }
    }
}

