package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a product is updated.
 * @summary
 * This event is published after a product aggregate is updated.
 * It contains the product id and the new snapshot of basic fields for reporting or synchronization.
 * @since 1.0
 */
@Getter
public class ProductUpdatedEvent extends ApplicationEvent {

    private final Long productId;
    private final String name;
    private final String description;
    private final String categoryId;
    private final String providerId;
    private final Integer minStock;
    private final Double unitPrice;
    private final Boolean isActive;

    /**
     * Constructor.
     *
     * @param source      the event source
     * @param productId   the id of the updated product
     * @param name        updated product name
     * @param description updated product description
     * @param categoryId  updated category identifier
     * @param providerId  updated provider identifier
     * @param minStock    updated minimum stock level
     * @param unitPrice   updated unit price
     * @param isActive    updated active status
     */
    public ProductUpdatedEvent(Object source,
                               Long productId,
                               String name,
                               String description,
                               String categoryId,
                               String providerId,
                               Integer minStock,
                               Double unitPrice,
                               Boolean isActive) {
        super(source);
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.providerId = providerId;
        this.minStock = minStock;
        this.unitPrice = unitPrice;
        this.isActive = isActive;
    }
}