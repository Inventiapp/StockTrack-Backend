package com.inventiapp.stocktrack.sales.infrastructure.persistence.jpa.repositories;

import com.inventiapp.stocktrack.sales.domain.model.aggregates.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {

}
