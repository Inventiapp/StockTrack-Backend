package com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Email;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Ruc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    Optional<Provider> findByEmail(Email email);

    Optional<Provider> findByRuc(Ruc ruc);

    boolean existsByEmail(Email email);

    boolean existsByRuc(Ruc ruc);
}
