package com.inventiapp.stocktrack.iam.interfaces.acl;

import com.inventiapp.stocktrack.userpermission.domain.model.aggregates.Permission;
import com.inventiapp.stocktrack.userpermission.domain.model.queries.GetAllPermissionQuery;
import com.inventiapp.stocktrack.userpermission.domain.model.queries.GetPermissionByNameQuery;
import com.inventiapp.stocktrack.userpermission.domain.services.PermissionQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UserPermissionContextFacade
 */
@Service
public class UserPermissionContextFacade {

    private final PermissionQueryService permissionQueryService;

    /**
     * Constructor
     * @param permissionQueryService The {@link PermissionQueryService} instance
     */
    public UserPermissionContextFacade(PermissionQueryService permissionQueryService) {
        this.permissionQueryService = permissionQueryService;
    }

    /**
     * Get a permission by its name
     * @param permissionName The permission name (e.g., "dashboard_access")
     * @return An optional containing the {@link Permission} entity if found, otherwise empty
     */
    public Optional<Permission> getPermissionByName(String permissionName) {
        if (permissionName == null || permissionName.isBlank()) {
            return Optional.empty();
        }
        var query = new GetPermissionByNameQuery(permissionName);
        return permissionQueryService.handle(query);
    }

    /**
     * Get all permissions
     * @return A list of all {@link Permission} entities
     */
    public List<Permission> getAllPermissions() {
        var query = new GetAllPermissionQuery();
        return permissionQueryService.handle(query);
    }

    /**
     * Get permissions by their names
     * @param permissionNames The list of permission names
     * @return A list of {@link Permission} entities that match the names
     */
    public List<Permission> getPermissionsByNames(List<String> permissionNames) {
        return permissionNames.stream()
                .map(this::getPermissionByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Get all available permission names
     * @return A list of permission names as strings
     */
    public List<String> getAllPermissionNames() {
        return getAllPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
    }
}

