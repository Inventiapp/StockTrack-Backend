package com.inventiapp.stocktrack.inventory.domain.model.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

/**
 * ProviderCreatedEvent domain event
 *
 * @summary
 * The ProviderCreatedEvent class represents the domain event that is triggered
 * when a new Provider aggregate is created in the Inventory bounded context.
 * It carries key provider information to notify other parts of the system.
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProviderCreatedEvent {

    private Long providerId;
    private String email;
    private String ruc;

    /**
     * Constructs a new ProviderCreatedEvent instance.
     *
     * @param providerId The unique identifier of the provider.
     * @param email The provider's email address.
     * @param ruc The provider's RUC value.
     */
    public ProviderCreatedEvent(Long providerId, String email, String ruc) {
        this.providerId = providerId;
        this.email = email;
        this.ruc = ruc;
    }
}
