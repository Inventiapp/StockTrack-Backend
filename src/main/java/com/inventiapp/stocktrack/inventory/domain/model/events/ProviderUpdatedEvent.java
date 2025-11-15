package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event fired when a provider is updated.
 * @summary
 * This event is published after a provider aggregate is updated.
 * It contains the provider id and the new snapshot of basic fields for reporting or synchronization.
 * @since 1.0
 */
@Getter
public class ProviderUpdatedEvent extends ApplicationEvent {
    private final Long providerId;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;
    private final String ruc;

    /**
     * Constructor.
     *
     * @param source     the event source (usually the aggregate or service)
     * @param providerId the id of the updated provider
     * @param firstName  updated first name
     * @param lastName   updated last name
     * @param phone      updated phone number
     * @param email      updated email
     * @param ruc        updated ruc
     */
    public ProviderUpdatedEvent(Object source, Long providerId, String firstName, String lastName, String phone, String email, String ruc) {
        super(source);
        this.providerId = providerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.ruc = ruc;
    }
}
