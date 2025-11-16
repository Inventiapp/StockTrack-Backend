package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a product is deleted.
 * @summary
 * This event is published after a product aggregate is removed.
 * It contains the id of the removed product and an optional reason.
 * @since 1.0
 */
@Getter
public class ProductDeletedEvent extends ApplicationEvent {
    private final Long productId;

    /**
     * Constructor.
     *
     * @param source    the event source (usually the aggregate or service)
     * @param productId the id of the deleted product
     */
    public ProductDeletedEvent(Object source, Long productId) {
        super(source);
        this.productId = productId;
    }
}