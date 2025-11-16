package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a product is created.
 * @summary
 * This event is published after a product aggregate is successfully created.
 * It contains the product id and the product snapshot (basic fields) useful for reporting.
 */
@Getter
public class ProductCreatedEvent extends ApplicationEvent {
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
     * @param source        the event source (usually the aggregate or service)
     * @param productId     the id of the created product
     * @param name          product name
     * @param description   product description
     * @param categoryId    product category id
     * @param providerId    product provider id
     * @param minStock      minimum quantity of the product
     * @param unitPrice     unit price of the product
     * @param isActive      product status
     */
    public ProductCreatedEvent(Object source,
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
