package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a provider is deleted.
 * @summary
 * This event is published after a provider aggregate is removed.
 * It contains the id of the removed provider and an optional reason.
 * @since 1.0
 */
@Getter
public class ProviderDeletedEvent extends ApplicationEvent {
    private final Long providerId;
    private final String reason;

    /**
     * Constructor.
     *
     * @param source     the event source (usually the aggregate or service)
     * @param providerId the id of the deleted provider
     * @param reason     optional reason for deletion (may be null)
     */
    public ProviderDeletedEvent(Object source, Long providerId, String reason) {
        super(source);
        this.providerId = providerId;
        this.reason = reason;
    }
}
