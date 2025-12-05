package com.inventiapp.stocktrack.iam.domain.model.valueobjects;

/**
 * Value object representing permission names for module access control.
 * These permissions control access to specific modules/features.
 */
public enum PermissionName {
    DASHBOARD_ACCESS("dashboard_access", "Access to dashboard"),
    INVENTORY_ACCESS("inventory_access", "Access to inventory management"),
    SALES_ACCESS("sales_access", "Access to sales module"),
    PROVIDERS_ACCESS("providers_access", "Access to providers management"),
    REPORTS_ACCESS("reports_access", "Access to reports"),
    USER_MANAGEMENT_ACCESS("user_management_access", "Access to user management");

    private final String name;
    private final String description;

    PermissionName(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static PermissionName fromString(String name) {
        for (PermissionName permission : values()) {
            if (permission.name.equals(name)) {
                return permission;
            }
        }
        throw new IllegalArgumentException("Unknown permission: " + name);
    }
}

