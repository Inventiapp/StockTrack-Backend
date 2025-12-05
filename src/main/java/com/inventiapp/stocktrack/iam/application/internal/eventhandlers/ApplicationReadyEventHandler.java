package com.inventiapp.stocktrack.iam.application.internal.eventhandlers;

import com.inventiapp.stocktrack.iam.domain.model.commands.SeedRolesCommand;
import com.inventiapp.stocktrack.iam.domain.services.RoleCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that seeds roles when the application starts
 */
@Service
public class ApplicationReadyEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);
    
    private final RoleCommandService roleCommandService;

    public ApplicationReadyEventHandler(RoleCommandService roleCommandService) {
        this.roleCommandService = roleCommandService;
    }

    /**
     * Handle ApplicationReadyEvent to seed roles
     * @param event The application ready event
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Starting to verify if roles seeding is needed for {} at {}", 
                applicationName, java.time.LocalDateTime.now());
        
        var seedRolesCommand = new SeedRolesCommand();
        roleCommandService.handle(seedRolesCommand);
        
        LOGGER.info("Roles seeding verification completed for {} at {}", 
                applicationName, java.time.LocalDateTime.now());
    }
}

