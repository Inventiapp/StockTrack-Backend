package com.inventiapp.stocktrack.inventory.domain.model.aggregates;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.events.ProviderCreatedEvent;
import com.inventiapp.stocktrack.inventory.domain.model.events.ProviderDeletedEvent;
import com.inventiapp.stocktrack.inventory.domain.model.events.ProviderUpdatedEvent;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Email;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.PhoneNumber;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Ruc;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

/**
 * Provider aggregate root
 * @summary
 * This aggregate root models a provider (supplier) with basic contact information and
 * a Peruvian tax identifier (RUC). Value objects are used for email, phone number and RUC
 * to centralize validation and formatting rules.
 * @since 1.0
 */
@Getter
@Entity
public class Provider extends AuditableAbstractAggregateRoot<Provider> {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    /**
     * Email as a value object.
     * The embedded attribute's single field is mapped to the 'email' column.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email", nullable = true))
    })
    private Email email;

    /**
     * Phone number as a value object.
     * The embedded attribute's single field is mapped to the 'phone_number' column.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "phone_number", nullable = true))
    })
    private PhoneNumber phoneNumber;

    /**
     * RUC as a value object.
     * The embedded attribute's single field is mapped to the 'ruc' column.
     * Length 11 is enforced by the value object, but column length is declared for DDL generation.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "ruc", length = 11, nullable = true, unique = true))
    })
    private Ruc ruc;

    /**
     * Default constructor required by JPA.
     * Keeps value object fields null so JPA can instantiate the aggregate. Domain factories
     * / application services should use the command-based constructor to create a valid aggregate.
     */
    public Provider() {
        super();
        this.firstName = Strings.EMPTY;
        this.lastName = Strings.EMPTY;
        this.email = null;
        this.phoneNumber = null;
        this.ruc = null;
    }

    /**
     * Constructs a Provider from a CreateProviderCommand.
     * Registers a ProviderCreatedEvent. Note: id may be null until persisted.
     *
     * @param command creation command containing provider data
     */
    public Provider(CreateProviderCommand command) {
        this();
        this.firstName = normalize(command.firstName());
        this.lastName = normalize(command.lastName());
        this.email = new Email(command.email());
        this.phoneNumber = new PhoneNumber(command.phoneNumber());
        this.ruc = new Ruc(command.ruc());

        // Register created event (aggregate-level). The id may be null if not yet persisted.
        this.addDomainEvent(new ProviderCreatedEvent(
                this,
                this.getId(),            // might be null until entity is saved
                this.firstName,
                this.lastName,
                this.phoneNumber != null ? this.phoneNumber.number() : null,
                this.email != null ? this.email.address() : null,
                this.ruc != null ? this.ruc.value() : null
        ));
    }

    /**
     * Update provider basic information using an UpdateProviderCommand.
     * Any non-null field in the command will be applied. ValueObjects will validate new values.
     *
     * @param command update command with optional fields
     * @return this provider instance (fluent)
     * @see UpdateProviderCommand
     */
    /**
     * Update provider basic information using an UpdateProviderCommand.
     * Registers ProviderUpdatedEvent with a snapshot of the current values.
     *
     * @param command update command with optional fields
     * @return this provider instance (fluent)
     */
    public Provider updateInformation(UpdateProviderCommand command) {
        if (command.firstName() != null) {
            this.firstName = normalize(command.firstName());
        }

        if (command.lastName() != null) {
            this.lastName = normalize(command.lastName());
        }

        if (command.email() != null) {
            this.email = new Email(command.email());
        }

        if (command.phoneNumber() != null) {
            this.phoneNumber = new PhoneNumber(command.phoneNumber());
        }

        if (command.ruc() != null) {
            this.ruc = new Ruc(command.ruc());
        }

        // Register updated event with the new snapshot.
        this.addDomainEvent(new ProviderUpdatedEvent(
                this,
                this.getId(),
                this.firstName,
                this.lastName,
                this.phoneNumber != null ? this.phoneNumber.number() : null,
                this.email != null ? this.email.address() : null,
                this.ruc != null ? this.ruc.value() : null
        ));

        return this;
    }

    /**
     * Marks the provider as deleted logically in domain terms by registering a ProviderDeletedEvent.
     * The actual removal from the database can be done by the application service/repository.
     *
     * @param reason optional reason for deletion (may be null)
     */
    public void markAsDeleted(String reason) {
        this.addDomainEvent(new ProviderDeletedEvent(this, this.getId(), reason));
    }

    /**
     * Returns the provider full name.
     *
     * @return concatenation of firstName and lastName
     */
    public String getFullName() {
        return (this.firstName + " " + this.lastName).trim();
    }

    /**
     * Normalizes text values to trimmed strings; converts null to empty string.
     *
     * @param value input string
     * @return normalized string
     */
    private String normalize(String value) {
        return value == null ? Strings.EMPTY : value.trim();
    }
}
