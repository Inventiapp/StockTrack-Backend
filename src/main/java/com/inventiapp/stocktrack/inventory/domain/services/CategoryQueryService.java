package com.inventiapp.stocktrack.inventory.domain.services;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Category;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllCategoriesQuery;

import java.util.List;

/**
 * @name CategoryQueryService
 *
 * @summary
 * This interface represents the service to handle category queries.
 * @since 1.0
 */
public interface CategoryQueryService {
    /**
     * Handles the get all categories query.
     * @param query The get all categories query.
     * @return The list of categories.
     * @see GetAllCategoriesQuery
     */
    List<Category> handle(GetAllCategoriesQuery query);
}