package com.inventiapp.stocktrack.inventory.application.internal.commandservices;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Category;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateCategoryCommand;
import com.inventiapp.stocktrack.inventory.domain.services.CategoryCommandService;
import com.inventiapp.stocktrack.inventory.infrastructure.internal.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * CategoryCommandService Implementation
 *
 * @summary
 * Implementation of the CategoryCommandService interface.
 * It is responsible for handling category commands.
 *
 * @since 1.0
 */
@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryRepository categoryRepository;

    public CategoryCommandServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Optional<Category> handle(CreateCategoryCommand command) {
        // Validate that category name doesn't already exist
        if (categoryRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Category with name '" + command.name() + "' already exists");
        }

        var category = new Category(command);
        var savedCategory = categoryRepository.save(category);
        return Optional.of(savedCategory);
    }

}

