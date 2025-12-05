package com.inventiapp.stocktrack.iam.domain.model.valueobjects;

/**
 * Enum representing available roles in the StockTrack system.
 * ROLE_ADMIN: Master user (created via sign-up, full access)
 * ROLE_USER: Regular user (created by admin, permissions-based access)
 */
public enum Roles {
    ROLE_ADMIN,
    ROLE_USER
}

