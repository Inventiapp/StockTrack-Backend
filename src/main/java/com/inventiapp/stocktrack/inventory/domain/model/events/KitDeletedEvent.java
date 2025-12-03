package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a kit is deleted.
 * 
 * @summary
 * This event is published after a kit aggregate is successfully deleted.
 * It contains the kit id.
 * @since 1.0
 */
@Getter
public class KitDeletedEvent extends ApplicationEvent {
    private final Long kitId;

    /**
     * Constructor.
     * 
     * @param source The event source (usually the aggregate or service)
     * @param kitId The id of the deleted kit
     */
    public KitDeletedEvent(Object source, Long kitId) {
        super(source);
        this.kitId = kitId;
    }
}

