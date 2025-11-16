package com.inventiapp.stocktrack.inventory.domain.model.aggregates;

import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateBatchCommand;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Batch Aggregate Root
 *
 * Represents a batch in inventory.
 * Contains basic validation and inherits audit fields.
 */
@Entity
@Table(name = "batches")
@Getter
@NoArgsConstructor
public class Batch extends AuditableAbstractAggregateRoot<Batch> {

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Date expirationDate;

    @Column(nullable = false)
    private Date receptionDate;

    /**
     * Creates a new Batch aggregate from the CreateBatchCommand.
     *
     * @param command The create batch command
     */
    public Batch(CreateBatchCommand command) {

        if (command == null) throw new IllegalArgumentException("CreateBatchCommand is required");

        if (command.productId() == null || command.productId() <= 0) {
            throw new IllegalArgumentException("productId cannot be null or less than or equal to 0");
        }
        if (command.quantity() == null || command.quantity() < 0) {
            throw new IllegalArgumentException("quantity cannot be null or negative");
        }
        if (command.expirationDate() == null) {
            throw new IllegalArgumentException("expirationDate cannot be null");
        }
        if (command.receptionDate() == null) {
            throw new IllegalArgumentException("receptionDate cannot be null");
        }
        this.productId = command.productId();
        this.quantity = command.quantity();
        this.expirationDate = command.expirationDate();
        this.receptionDate = command.receptionDate();
    }
}