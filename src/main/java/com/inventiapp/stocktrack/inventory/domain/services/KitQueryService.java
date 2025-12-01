package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Kit;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllKitsQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetKitByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * @name KitQueryService
 * 
 * @summary
 * This interface represents the service to handle kit queries.
 * @since 1.0
 */
public interface KitQueryService {
    /**
     * Handles the get all kits query.
     * @param query The get all kits query.
     * @return The list of kits.
     * @see GetAllKitsQuery
     */
    List<Kit> handle(GetAllKitsQuery query);

    /**
     * Handles the get kit by id query.
     * @param query The get kit by id query.
     * @return Optional containing the kit if found.
     * @see GetKitByIdQuery
     */
    Optional<Kit> handle(GetKitByIdQuery query);
}

