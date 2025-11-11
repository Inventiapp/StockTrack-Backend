package com.inventiapp.stocktrack.sales.domain.model.aggregates;

import com.inventiapp.stocktrack.sales.domain.model.commands.CreateSaleCommand;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "sales")
@Getter
public class Sale extends AuditableAbstractAggregateRoot<Sale> {

    @Getter
    @Column(nullable = false)
    private double totalAmount;

    public Sale(CreateSaleCommand command) {
        this.totalAmount = command.totalAmount();
    }

    protected Sale() {
    }


}
