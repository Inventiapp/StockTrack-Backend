package com.inventiapp.stocktrack.inventory.domain.exceptions;

/**
 * Exception thrown when a category is not found.
 * @summary
 * This exception is thrown when a category is not found in the database.
 * @see RuntimeException
 */
public class CategoryNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception.
     * @param categoryId The ID of the category that was not found.
     */
    public CategoryNotFoundException(Long categoryId) {
        super(String.format("Category with id '%s' not found", categoryId));
    }
}
