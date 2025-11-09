package com.inventiapp.stocktrack.inventory.domain.model.aggregates;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.events.ProviderCreatedEvent;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Ruc;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Provider aggregate root
 * @summary
 * This aggregate root represents a provider (supplier) in the Inventory bounded context.
 */
@Entity
@Table(name = "providers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Provider extends AuditableAbstractAggregateRoot<Provider> {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "ruc")
    @Embedded
    private Ruc ruc;

    /**
     * Domain constructor used to create a new Provider instance.
     *
     * @param firstName provider first name (required)
     * @param lastName  provider last name (required)
     * @param phoneNumber provider phone number (optional)
     * @param email provider email (required)
     * @param ruc provider RUC value object (required)
     */
    public Provider(String firstName, String lastName, String phoneNumber, String email, Ruc ruc) {
        validateRequired(firstName, "firstName");
        validateRequired(lastName, "lastName");
        validateRequired(email, "email");
        if (ruc == null) throw new IllegalArgumentException("RUC is required");

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.phoneNumber = (phoneNumber == null) ? null : phoneNumber.trim();
        this.email = email.trim().toLowerCase();
        this.ruc = ruc;
    }

    /**
     * Factory helper to construct a Provider from a CreateProviderCommand.
     *
     * @param cmd creation command carrying raw provider data
     * @return new Provider aggregate
     */
    public static Provider from(CreateProviderCommand cmd) {
        if (cmd == null) throw new IllegalArgumentException("CreateProviderCommand required");
        Ruc rucVo = new Ruc(cmd.ruc());
        return new Provider(cmd.firstName(), cmd.lastName(), cmd.phoneNumber(), cmd.email(), rucVo);
    }


    /**
     * Update the provider's contact information while preserving invariants.
     *
     * @param firstName new first name (required)
     * @param lastName  new last name (required)
     * @param phoneNumber new phone number (optional)
     * @param email new email (required)
     */
    public void updateContactInfo(String firstName, String lastName, String phoneNumber, String email) {
        validateRequired(firstName, "firstName");
        validateRequired(lastName, "lastName");
        validateRequired(email, "email");

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.phoneNumber = (phoneNumber == null) ? null : phoneNumber.trim();
        this.email = email.trim().toLowerCase();
    }

    /**
     * Replace the provider's RUC value object.
     *
     * @param newRuc new RUC value object (non-null)
     */
    public void updateRuc(Ruc newRuc) {
        if (newRuc == null) throw new IllegalArgumentException("RUC required");
        this.ruc = newRuc;
    }

    /**
     * Register a ProviderCreatedEvent to the aggregate's domain events.
     * Intended to be called AFTER persistence so getId() returns the DB-generated id.
     */
    public void registerCreatedEvent() {
        this.addDomainEvent(new ProviderCreatedEvent(this.getId(), this.getEmail(), this.getRuc().getValue()));
    }

    private static void validateRequired(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }
}
