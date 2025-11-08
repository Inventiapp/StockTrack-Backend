package com.inventiapp.stocktrack.inventory.domain.model.aggregates;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateCategoryCommand;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Category Aggregate Root
 *
 * @summary
 * The Category class is an aggregate root that represents a category.
 * It is responsible for handling the CreateCategoryCommand command.
 * A category is an inventory resource with a name and description.
 * @since 1.0
 */
@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor
public class Category extends AuditableAbstractAggregateRoot<Category> {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    /**
     * @summary Constructor.
     * It creates a new Category instance based on the CreateCategoryCommand command.
     * @param command - the CreateCategoryCommand command
     */
    public Category(CreateCategoryCommand command) {
        if (command.name() == null || command.name().isBlank()) {
            throw new IllegalArgumentException("Category name is required");
        }
        this.name = command.name().trim();
        this.description = command.description() != null ? command.description().trim() : null;
    }
}
