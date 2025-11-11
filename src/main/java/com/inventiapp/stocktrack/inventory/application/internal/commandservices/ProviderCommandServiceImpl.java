package com.inventiapp.stocktrack.inventory.application.internal.commandservices;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Provider;
import com.inventiapp.stocktrack.inventory.domain.model.commands.CreateProviderCommand;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Email;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.PhoneNumber;
import com.inventiapp.stocktrack.inventory.domain.model.valueobject.Ruc;
import com.inventiapp.stocktrack.inventory.domain.services.ProviderCommandService;
import com.inventiapp.stocktrack.inventory.infrastructure.persistence.jpa.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProviderCommandServiceImpl implements ProviderCommandService {

    private final ProviderRepository providerRepository;

    public ProviderCommandServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    @Transactional
    public Optional<Provider> handle(CreateProviderCommand command) {
        command.validate();

        Email emailVo = new Email(command.email());
        PhoneNumber phoneVo = new PhoneNumber(command.phoneNumber());
        Ruc rucVo = null;
        if (command.ruc() != null && !command.ruc().trim().isEmpty()) {
            rucVo = new Ruc(command.ruc());
        }

        if (providerRepository.existsByEmail(emailVo)) {
            throw new IllegalArgumentException("Provider with email '" + emailVo + "' already exists");
        }
        if (rucVo != null && providerRepository.existsByRuc(rucVo)) {
            throw new IllegalArgumentException("Provider with RUC '" + rucVo + "' already exists");
        }

        Provider provider = new Provider(command.firstName(), command.lastName(), phoneVo, emailVo, rucVo);

        Provider saved = providerRepository.save(provider);
        saved.registerCreatedEvent();

        return Optional.of(saved);
    }
}
