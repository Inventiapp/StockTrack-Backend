package com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {}
