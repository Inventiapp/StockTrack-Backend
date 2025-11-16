package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Category;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.CategoryResource;

/**
 * Assembler to create a CategoryResource from a Category entity.
 * @since 1.0
 */
public class CategoryResourceFromEntityAssembler {
    /**
     * Converts a Category entity to a CategoryResource.
     * @param entity Category entity to convert
     * @return CategoryResource created from the entity
     */
    public static CategoryResource toResourceFromEntity(Category entity) {
        return new CategoryResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

