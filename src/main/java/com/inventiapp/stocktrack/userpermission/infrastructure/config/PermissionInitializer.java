package com.inventiapp.stocktrack.userpermission.infrastructure.config;

import com.inventiapp.stocktrack.iam.domain.model.valueobjects.PermissionName;
import com.inventiapp.stocktrack.userpermission.domain.model.commands.CreatePermissionCommand;
import com.inventiapp.stocktrack.userpermission.domain.services.PermissionCommandService;
import com.inventiapp.stocktrack.userpermission.infrastructure.persistence.jpa.repositories.PermissionRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Initializes base permissions on application startup.
 * Ensures all required permissions exist in the database.
 * Uses PermissionName enum as the source of truth for permission definitions.
 */
@Component
public class PermissionInitializer {

    private static final Logger logger = LoggerFactory.getLogger(PermissionInitializer.class);

    private final PermissionCommandService permissionCommandService;
    private final PermissionRepository permissionRepository;

    public PermissionInitializer(PermissionCommandService permissionCommandService,
                                 PermissionRepository permissionRepository) {
        this.permissionCommandService = permissionCommandService;
        this.permissionRepository = permissionRepository;
    }

    @PostConstruct
    public void initializePermissions() {
        logger.info("Initializing base permissions from PermissionName enum...");

        for (PermissionName permissionName : PermissionName.values()) {
            if (!permissionRepository.existsByName(permissionName.getName())) {
                try {
                    var command = new CreatePermissionCommand(
                            permissionName.getName(),
                            permissionName.getDescription()
                    );
                    permissionCommandService.handle(command);
                    logger.info("Created permission: {} - {}", permissionName.getName(), permissionName.getDescription());
                } catch (Exception e) {
                    logger.error("Error creating permission {}: {}", permissionName.getName(), e.getMessage());
                }
            } else {
                logger.debug("Permission already exists: {}", permissionName.getName());
            }
        }

        logger.info("Permission initialization completed. Total permissions: {}", PermissionName.values().length);
    }
}

