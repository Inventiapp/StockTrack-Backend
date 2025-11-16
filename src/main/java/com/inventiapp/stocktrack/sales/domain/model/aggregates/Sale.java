package com.inventiapp.stocktrack.sales.domain.model.aggregates;

import com.inventiapp.stocktrack.sales.domain.model.commands.CreateSaleCommand;
import com.inventiapp.stocktrack.sales.domain.model.entities.SaleDetail;
import com.inventiapp.stocktrack.sales.domain.model.valueobjects.ProductId;
import com.inventiapp.stocktrack.sales.domain.model.valueobjects.StaffUserId;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Sale extends AuditableAbstractAggregateRoot<Sale> {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "staff_user_id"))
    private StaffUserId staffUserId;

    @Column(nullable = false)
    private double totalAmount;


    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SaleDetail> details = new ArrayList<>();

    public Sale(CreateSaleCommand command) {
        this.staffUserId = new StaffUserId(command.staffUserId());
        this.totalAmount = 0;
        this.details = new ArrayList<>();

        for (var item : command.details()) {
            this.addDetail(new ProductId(item.productId()), item.quantity(), item.unitPrice());
        }

        if (Math.abs(this.totalAmount - command.totalAmount()) > 0.01) {
            // Add exception handling as needed
            throw new IllegalArgumentException("Total amount does not match the sum of detail items");
        }
    }

    public void addDetail(ProductId productId, int quantity, double unitPrice) {
        SaleDetail detail = new SaleDetail(this, productId, quantity, unitPrice);
        this.details.add(detail);
        this.totalAmount += detail.getTotalPrice();
    }

    public void updateDetails(List<SaleDetail> newDetails) {
        this.details.clear();
        this.details.addAll(newDetails);
        this.totalAmount = newDetails.stream().mapToDouble(SaleDetail::getTotalPrice).sum();
    }

    protected Sale() {
    }


}
