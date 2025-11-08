package com.inventiapp.stocktrack.inventory.domain.model.aggregates;

import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Ruc;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Provider extends AuditableAbstractAggregateRoot<Provider> {
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    private Ruc ruc;

    public Provider(String firstName, String lastName, String phoneNumber, String email, Ruc ruc) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ruc = ruc;
    }

    public void updateContactInfo(String firstName, String lastName, String phoneNumber, String email) {
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("firstName required");
        if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("lastName required");
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void updateRuc(Ruc newRuc) {
        if (newRuc == null) throw new IllegalArgumentException("RUC required");
        this.ruc = newRuc;
    }
}
