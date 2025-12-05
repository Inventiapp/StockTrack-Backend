package com.inventiapp.stocktrack.iam.domain.services;

import com.inventiapp.stocktrack.iam.domain.model.aggregates.User;
import com.inventiapp.stocktrack.iam.domain.model.queries.GetAllUsersQuery;
import com.inventiapp.stocktrack.iam.domain.model.queries.GetUserByIdQuery;
import com.inventiapp.stocktrack.iam.domain.model.queries.GetUserByEmailQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for user query operations
 */
public interface UserQueryService {

    /**
     * Handle get all users query
     * @param query The query
     * @return List of all users
     */
    List<User> handle(GetAllUsersQuery query);

    /**
     * Handle get user by ID query
     * @param query The query with user ID
     * @return Optional User
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Handle get user by email query
     * @param query The query with email
     * @return Optional User
     */
    Optional<User> handle(GetUserByEmailQuery query);
}

