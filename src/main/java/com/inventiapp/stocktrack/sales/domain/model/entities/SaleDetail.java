package com.inventiapp.stocktrack.sales.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inventiapp.stocktrack.sales.domain.model.aggregates.Sale;
import com.inventiapp.stocktrack.sales.domain.model.valueobjects.ProductId;
import com.inventiapp.stocktrack.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
public class SaleDetail extends AuditableModel {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "product_id"))
    private ProductId productId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unitPrice;

    @Column(nullable = false)
    private double totalPrice;

    public SaleDetail(Sale sale, ProductId productId, int quantity, double unitPrice) {
        this.sale = sale;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * quantity;
    }

    protected SaleDetail() {
    }
}