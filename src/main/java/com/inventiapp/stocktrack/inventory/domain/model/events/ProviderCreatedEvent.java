package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a provider is created.
 * @summary
 * This event is published after a provider aggregate is successfully created.
 * It contains the provider id and the provider snapshot (basic fields) useful for reporting.
 * @since 1.0
 */
@Getter
public class ProviderCreatedEvent extends ApplicationEvent {
    private final Long providerId;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;
    private final String ruc;

    /**
     * Constructor.
     *
     * @param source    the event source (usually the aggregate or service)
     * @param providerId the id of the created provider
     * @param firstName  provider first name
     * @param lastName   provider last name
     * @param phone      provider phone number (raw)
     * @param email      provider email (raw)
     * @param ruc        provider ruc (raw)
     */
    public ProviderCreatedEvent(Object source, Long providerId, String firstName, String lastName, String phone, String email, String ruc) {
        super(source);
        this.providerId = providerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.ruc = ruc;
    }
}
