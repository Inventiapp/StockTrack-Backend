package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a kit is created.
 * 
 * @summary
 * This event is published after a kit aggregate is successfully created.
 * It contains the kit id and name.
 * @since 1.0
 */
@Getter
public class KitCreatedEvent extends ApplicationEvent {
    private final Long kitId;
    private final String name;

    /**
     * Constructor.
     * 
     * @param source The event source (usually the aggregate or service)
     * @param kitId The id of the created kit
     * @param name Kit name
     */
    public KitCreatedEvent(Object source, Long kitId, String name) {
        super(source);
        this.kitId = kitId;
        this.name = name;
    }
}

