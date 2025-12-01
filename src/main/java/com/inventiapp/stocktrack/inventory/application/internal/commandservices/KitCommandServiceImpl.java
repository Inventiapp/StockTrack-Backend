package com.inventiapp.stocktrack.inventory.application.internal.commandservices;

import com.inventiapp.stocktrack.inventory.domain.exceptions.KitNotFoundException;
import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Kit;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateKitCommand;
import com.inventiapp.stocktrack.inventory.domain.model.commands.DeleteKitCommand;
import com.inventiapp.stocktrack.inventory.domain.model.events.KitCreatedEvent;
import com.inventiapp.stocktrack.inventory.domain.model.events.KitDeletedEvent;
import com.inventiapp.stocktrack.inventory.domain.services.KitCommandService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.repositories.KitRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * KitCommandService Implementation
 * 
 * @summary
 * Implementation of the KitCommandService interface.
 * It is responsible for handling kit commands.
 * 
 * @since 1.0
 */
@Service
public class KitCommandServiceImpl implements KitCommandService {

    private final KitRepository kitRepository;

    public KitCommandServiceImpl(KitRepository kitRepository) {
        this.kitRepository = kitRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Optional<Kit> handle(CreateKitCommand command) {
        // Validate that kit name doesn't already exist
        if (kitRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Kit with name '" + command.name() + "' already exists");
        }

        var kit = new Kit(command);
        var savedKit = kitRepository.save(kit);
        
        // Register domain event
        savedKit.addDomainEvent(new KitCreatedEvent(savedKit, savedKit.getId(), savedKit.getName()));
        
        return Optional.of(savedKit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void handle(DeleteKitCommand command) {
        Kit kit = kitRepository.findById(command.kitId())
                .orElseThrow(() -> new KitNotFoundException(command.kitId()));

        kit.addDomainEvent(new KitDeletedEvent(kit, kit.getId()));

        try {
            kitRepository.delete(kit);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error deleting kit: " +
                    (ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage()));
        }
    }
}

