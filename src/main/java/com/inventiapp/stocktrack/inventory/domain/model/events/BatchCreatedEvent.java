package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Date;

/**
 * Event fired when a batch is created.
 * @summary
 * This event is published after a batch aggregate is successfully created.
 * It contains the batch id and the batch snapshot (basic fields) useful for reporting.
 * @since 1.0
 */
@Getter
public class BatchCreatedEvent extends ApplicationEvent {
    private final Long batchId;
    private final Long productId;
    private final Integer quantity;
    private final Date expirationDate;
    private final Date receptionDate;

    /**
     * Constructor.
     *
     * @param source        the event source (usually the aggregate or service)
     * @param batchId       the id of the created batch
     * @param productId     product id
     * @param quantity      stock quantity
     * @param expirationDate expiration date
     * @param receptionDate reception date
     */
    public BatchCreatedEvent(Object source,
                            Long batchId,
                            Long productId,
                            Integer quantity,
                            Date expirationDate,
                            Date receptionDate) {
        super(source);
        this.batchId = batchId;
        this.productId = productId;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.receptionDate = receptionDate;
    }
}

