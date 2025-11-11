package com.inventiapp.stocktrack.inventory.domain.model.aggregates;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.events.ProviderCreatedEvent;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Email;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.PhoneNumber;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Ruc;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.AttributeOverride;
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

    /**
     * PhoneNumber is embedded; override the internal 'value' column to 'phone_number'.
     * This field is required (nullable = false).
     */
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone_number", length = 9, nullable = false))
    private PhoneNumber phoneNumber;

    /**
     * Email is embedded; override the internal 'value' column to 'email'.
     * email is required and unique.
     */
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true, length = 255))
    private Email email;

    /**
     * RUC is embedded; override the internal 'value' column to 'ruc'.
     * This field is optional (nullable = true).
     */
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "ruc", nullable = true, length = 11))
    private Ruc ruc;

    /**
     * Domain constructor receiving value objects.
     *
     * @param firstName provider first name (required)
     * @param lastName  provider last name (required)
     * @param phoneNumber provider phone as value object (required)
     * @param email provider email as value object (required)
     * @param ruc provider RUC as value object (optional, may be null)
     */
    public Provider(String firstName,
                    String lastName,
                    PhoneNumber phoneNumber,
                    Email email,
                    Ruc ruc) {
        validateRequired(firstName, "firstName");
        validateRequired(lastName, "lastName");
        if (phoneNumber == null) throw new IllegalArgumentException("PhoneNumber is required");
        if (email == null) throw new IllegalArgumentException("Email is required");

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ruc = ruc; // may be null
    }

    /**
     * Factory helper to construct a Provider from a CreateProviderCommand.
     * Converts raw strings into the appropriate value objects.
     *
     * @param cmd creation command with raw strings
     * @return new Provider aggregate
     */
    public static Provider from(CreateProviderCommand cmd) {
        if (cmd == null) throw new IllegalArgumentException("CreateProviderCommand required");

        // phone is required in this design
        if (cmd.phoneNumber() == null || cmd.phoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("phoneNumber is required");
        }
        PhoneNumber phoneVo = new PhoneNumber(cmd.phoneNumber());

        Email emailVo = new Email(cmd.email());

        Ruc rucVo = null;
        if (cmd.ruc() != null && !cmd.ruc().trim().isEmpty()) {
            rucVo = new Ruc(cmd.ruc());
        }

        return new Provider(cmd.firstName(), cmd.lastName(), phoneVo, emailVo, rucVo);
    }

    /**
     * Update provider contact information while keeping invariants.
     *
     * @param firstName new first name (required)
     * @param lastName new last name (required)
     * @param phoneNumber new phone number value object (required)
     * @param email new email value object (required)
     */
    public void updateContactInfo(String firstName, String lastName, PhoneNumber phoneNumber, Email email) {
        validateRequired(firstName, "firstName");
        validateRequired(lastName, "lastName");
        if (phoneNumber == null) throw new IllegalArgumentException("PhoneNumber is required");
        if (email == null) throw new IllegalArgumentException("Email is required");

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Replace the provider's RUC.
     *
     * @param newRuc new RUC value object (optional, may be null)
     */
    public void updateRuc(Ruc newRuc) {
        this.ruc = newRuc;
    }

    /**
     * Register ProviderCreatedEvent after persistence (so id exists).
     */
    public void registerCreatedEvent() {
        String rucValue = (this.ruc == null) ? null : this.ruc.getValue();
        String emailValue = (this.email == null) ? null : this.email.getValue();
        this.addDomainEvent(new ProviderCreatedEvent(this.getId(), emailValue, rucValue));
    }

    private static void validateRequired(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }
}
