package com.inventiapp.stocktrack.iam.application.internal.commandservices;

import com.inventiapp.stocktrack.iam.application.internal.outboundservices.hashing.HashingService;
import com.inventiapp.stocktrack.iam.application.internal.outboundservices.tokens.TokenService;
import com.inventiapp.stocktrack.iam.domain.model.aggregates.User;
import com.inventiapp.stocktrack.iam.domain.model.commands.SignInCommand;
import com.inventiapp.stocktrack.iam.domain.model.commands.SignUpCommand;
import com.inventiapp.stocktrack.iam.domain.model.entities.Role;
import com.inventiapp.stocktrack.iam.domain.model.valueobjects.Roles;
import com.inventiapp.stocktrack.iam.domain.services.UserCommandService;
import com.inventiapp.stocktrack.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.inventiapp.stocktrack.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of UserCommandService
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  HashingService hashingService,
                                  TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        if (!hashingService.matches(command.password(), user.get().getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        var token = tokenService.generateToken(command.email());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new RuntimeException("Email already exists");
        }

        // Get roles from repository
        List<Role> roles = new ArrayList<>();
        for (Role role : command.roles()) {
            var foundRole = roleRepository.findByName(role.getName());
            foundRole.ifPresent(roles::add);
        }

        // If no valid roles found, assign default role
        if (roles.isEmpty()) {
            var defaultRole = roleRepository.findByName(Roles.ROLE_SELLER);
            defaultRole.ifPresent(roles::add);
        }

        var encodedPassword = hashingService.encode(command.password());
        var user = new User(command.email(), encodedPassword, roles);
        
        return Optional.of(userRepository.save(user));
    }
}

