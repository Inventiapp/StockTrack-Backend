package com.inventiapp.stocktrack.userpermission.infrastructure.persistence.jpa.repositories;

import com.inventiapp.stocktrack.userpermission.domain.model.aggregates.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    /**
     * Busca un permiso por su nombre.
     * Spring Data JPA crea la consulta automáticamente basado en el nombre del método.
     */
    Optional<Permission> findByName(String name);

    /**
     * Verifica si existe un permiso por su nombre.
     */
    boolean existsByName(String name);
}