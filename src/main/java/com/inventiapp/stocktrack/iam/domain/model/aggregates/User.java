package com.inventiapp.stocktrack.iam.domain.model.aggregates;

import com.inventiapp.stocktrack.iam.domain.model.entities.Role;
import com.inventiapp.stocktrack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.inventiapp.stocktrack.userpermission.domain.model.aggregates.Permission;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User aggregate root representing a system user
 */
@Entity
@Getter
@Setter
public class User extends AuditableAbstractAggregateRoot<User> {

    @NotBlank
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    /**
     * Permissions assigned directly to the user.
     * These control access to specific modules/features.
     * Relationship with Permission aggregate from userpermission bounded context.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    public User() {
        this.roles = new HashSet<>();
        this.permissions = new HashSet<>();
    }

    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, List<Role> roles) {
        this(email, password);
        addRoles(roles);
    }

    public User(String email, String password, List<Role> roles, Set<Permission> permissions) {
        this(email, password, roles);
        this.permissions = permissions != null ? new HashSet<>(permissions) : new HashSet<>();
    }

    /**
     * Add a single role to the user
     * @param role Role to add
     * @return this user instance
     */
    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    /**
     * Add multiple roles to the user
     * @param roles List of roles to add
     * @return this user instance
     */
    public User addRoles(List<Role> roles) {
        var validatedRoles = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoles);
        return this;
    }

    /**
     * Get roles as a list of strings
     * @return List of role names
     */
    public List<String> getRolesAsStringList() {
        return roles.stream()
                .map(role -> role.getName().name())
                .toList();
    }

    /**
     * Add a permission to the user
     * @param permission Permission to add
     * @return this user instance
     */
    public User addPermission(Permission permission) {
        if (permission != null) {
            this.permissions.add(permission);
        }
        return this;
    }

    /**
     * Add multiple permissions to the user
     * @param permissions Set of permissions to add
     * @return this user instance
     */
    public User addPermissions(Set<Permission> permissions) {
        if (permissions != null) {
            this.permissions.addAll(permissions);
        }
        return this;
    }

    /**
     * Remove a permission from the user
     * @param permission Permission to remove
     * @return this user instance
     */
    public User removePermission(Permission permission) {
        if (permission != null) {
            this.permissions.remove(permission);
        }
        return this;
    }

    /**
     * Check if user has a specific permission by name
     * @param permissionName The permission name to check (e.g., "dashboard_access")
     * @return True if user has the permission
     */
    public boolean hasPermission(String permissionName) {
        if (permissions == null || permissionName == null) {
            return false;
        }
        return permissions.stream()
                .anyMatch(permission -> permission.getName().equals(permissionName));
    }

    /**
     * Check if user has a specific Permission entity
     * @param permission The Permission entity to check
     * @return True if user has the permission
     */
    public boolean hasPermission(Permission permission) {
        if (permissions == null || permission == null) {
            return false;
        }
        return permissions.contains(permission);
    }

    /**
     * Check if user has any of the specified permissions by name
     * @param requiredPermissionNames List of permission names
     * @return True if user has at least one of the permissions
     */
    public boolean hasAnyPermission(List<String> requiredPermissionNames) {
        if (permissions == null || requiredPermissionNames == null || requiredPermissionNames.isEmpty()) {
            return false;
        }
        return requiredPermissionNames.stream().anyMatch(this::hasPermission);
    }

    /**
     * Get permissions as a list of strings (permission names)
     * @return List of permission names
     */
    public List<String> getPermissionsAsStringList() {
        if (permissions == null) {
            return new ArrayList<>();
        }
        return permissions.stream()
                .map(Permission::getName)
                .toList();
    }

    /**
     * Check if user is admin (has ROLE_ADMIN)
     * Admins have access to everything regardless of permissions
     * @return True if user is admin
     */
    public boolean isAdmin() {
        return roles.stream()
                .anyMatch(role -> role.getName() == com.inventiapp.stocktrack.iam.domain.model.valueobjects.Roles.ROLE_ADMIN);
    }
}

