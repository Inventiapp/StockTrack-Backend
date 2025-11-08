package com.inventiapp.stocktrack.inventory.application.internal.queryservices;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Category;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllCategoriesQuery;
import com.inventiapp.stocktrack.inventory.domain.services.CategoryQueryService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CategoryQueryService Implementation
 *
 * @summary
 * Implementation of the CategoryQueryService interface.
 * It is responsible for handling category queries.
 *
 * @since 1.0
 */
@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public CategoryQueryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> handle(GetAllCategoriesQuery query) {
        return categoryRepository.findAll();
    }
}

