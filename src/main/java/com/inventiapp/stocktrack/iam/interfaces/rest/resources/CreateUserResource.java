package com.inventiapp.stocktrack.iam.interfaces.rest.resources;

import java.util.List;

/**
 * Resource for creating a new user from staff management.
 * Users created by admin always get ROLE_USER.
 * Permissions control access to specific modules.
 * @param email The email for the new user
 * @param password The password for the new user
 * @param permissions List of permission names (e.g., "dashboard_access", "inventory_access", "sales_access", "providers_access", "reports_access", "user_management_access")
 */
public record CreateUserResource(String email, String password, List<String> permissions) {
}

