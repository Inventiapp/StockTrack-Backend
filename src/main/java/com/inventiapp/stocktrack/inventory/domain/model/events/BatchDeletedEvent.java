package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a batch is deleted.
 * @summary
 * This event is published after a batch aggregate is removed.
 * It contains the id of the removed batch.
 * @since 1.0
 */
@Getter
public class BatchDeletedEvent extends ApplicationEvent {
    private final Long batchId;

    /**
     * Constructor.
     *
     * @param source    the event source (usually the aggregate or service)
     * @param batchId   the id of the deleted batch
     */
    public BatchDeletedEvent(Object source, Long batchId) {
        super(source);
        this.batchId = batchId;
    }
}

