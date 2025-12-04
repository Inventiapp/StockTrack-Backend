package com.inventiapp.stocktrack.inventory.interfaces.rest.transform;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Kit;
import com.inventiapp.stocktrack.inventory.interfaces.rest.resources.KitResource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Assembler to create a KitResource from a Kit entity.
 * @since 1.0
 */
public class KitResourceFromEntityAssembler {
    /**
     * Converts a Kit entity to a KitResource.
     * @param entity Kit entity to convert
     * @return KitResource created from the entity
     */
    public static KitResource toResourceFromEntity(Kit entity) {
        List<KitResource.KitItemResource> items = entity.getItems().stream()
                .map(item -> new KitResource.KitItemResource(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());
        
        // Calculate total price: sum of (unit price * quantity) for each item
        // The price in KitItem is unit price, so we multiply by quantity
        Double totalPrice = items.stream()
                .mapToDouble(item -> item.price() * item.quantity())
                .sum();
        
        return new KitResource(
                entity.getId(),
                entity.getName(),
                items,
                totalPrice,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

