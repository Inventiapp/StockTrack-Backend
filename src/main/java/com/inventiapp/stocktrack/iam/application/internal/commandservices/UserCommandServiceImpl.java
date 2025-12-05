package com.inventiapp.stocktrack.iam.application.internal.commandservices;

import com.inventiapp.stocktrack.iam.application.internal.outboundservices.hashing.HashingService;
import com.inventiapp.stocktrack.iam.application.internal.outboundservices.tokens.TokenService;
import com.inventiapp.stocktrack.iam.domain.model.aggregates.User;
import com.inventiapp.stocktrack.iam.domain.model.commands.CreateUserCommand;
import com.inventiapp.stocktrack.iam.domain.model.commands.SignInCommand;
import com.inventiapp.stocktrack.iam.domain.model.commands.SignUpCommand;
import com.inventiapp.stocktrack.iam.domain.model.commands.UpdateUserCommand;
import com.inventiapp.stocktrack.iam.domain.model.entities.Role;
import com.inventiapp.stocktrack.iam.domain.model.valueobjects.Roles;
import com.inventiapp.stocktrack.iam.domain.services.UserCommandService;
import com.inventiapp.stocktrack.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.inventiapp.stocktrack.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.inventiapp.stocktrack.iam.interfaces.acl.UserPermissionContextFacade;
import com.inventiapp.stocktrack.userpermission.domain.model.aggregates.Permission;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand}, {@link SignUpCommand}, {@link CreateUserCommand}, and {@link UpdateUserCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final UserPermissionContextFacade userPermissionContextFacade;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  HashingService hashingService,
                                  TokenService tokenService,
                                  UserPermissionContextFacade userPermissionContextFacade) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.userPermissionContextFacade = userPermissionContextFacade;
    }

    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the user and the token.
     * </p>
     * @param command the sign-in command containing the email and password
     * @return an optional containing the user matching the email and the generated token
     * @throws RuntimeException if the user is not found or the password is invalid
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        if (!hashingService.matches(command.password(), user.get().getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        var userEntity = user.get();
        var token = tokenService.generateToken(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getRolesAsStringList()
        );
        return Optional.of(ImmutablePair.of(userEntity, token));
    }

    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and returns the created user.
     *     Sign-up creates an ADMIN user (business owner) with ALL permissions automatically.
     * </p>
     * @param command the sign-up command containing the email and password
     * @return the created user with ROLE_ADMIN and all permissions
     * @throws RuntimeException if the email already exists
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new RuntimeException("Email already exists");
        }

        List<Role> roles = new ArrayList<>();
        var adminRole = roleRepository.findByName(Roles.ROLE_ADMIN);
        if (adminRole.isPresent()) {
            roles.add(adminRole.get());
        } else {
            var newAdminRole = roleRepository.save(new Role(Roles.ROLE_ADMIN));
            roles.add(newAdminRole);
        }

        Set<Permission> allPermissions = new HashSet<>(userPermissionContextFacade.getAllPermissions());

        var encodedPassword = hashingService.encode(command.password());
        var user = new User(command.email(), encodedPassword, roles, allPermissions);
        
        return Optional.of(userRepository.save(user));
    }

    /**
     * Handle the create user command
     * <p>
     *     This method handles the {@link CreateUserCommand} command and returns the created user.
     *     Users created by admin always get ROLE_USER. Permissions control access to specific modules.
     * </p>
     * @param command the create user command containing the email, password and permissions
     * @return the created user with ROLE_USER and specified permissions
     * @throws RuntimeException if the email already exists
     */
    @Override
    public Optional<User> handle(CreateUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new RuntimeException("Email already exists");
        }

        List<Role> roles = new ArrayList<>();
        var userRole = roleRepository.findByName(Roles.ROLE_USER);
        if (userRole.isPresent()) {
            roles.add(userRole.get());
        } else {
            var newUserRole = roleRepository.save(new Role(Roles.ROLE_USER));
            roles.add(newUserRole);
        }

        Set<Permission> permissions = userPermissionContextFacade.getPermissionsByNames(command.permissions())
                .stream()
                .collect(Collectors.toSet());

        var encodedPassword = hashingService.encode(command.password());
        var user = new User(command.email(), encodedPassword, roles, permissions);
        
        return Optional.of(userRepository.save(user));
    }

    /**
     * Handle the update user command
     * <p>
     *     This method handles the {@link UpdateUserCommand} command and returns the updated user.
     *     All fields are optional - only provided fields will be updated.
     * </p>
     * @param command the update user command containing the user ID, email (optional), password (optional) and permissions (optional)
     * @return the updated user
     * @throws RuntimeException if the user is not found or the email is already taken by another user
     */
    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        var userOpt = userRepository.findById(command.userId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        var user = userOpt.get();

        if (command.email() != null && !command.email().isBlank()) {
            var existingUser = userRepository.findByEmail(command.email());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(command.userId())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(command.email());
        }

        if (command.password() != null && !command.password().isBlank()) {
            var encodedPassword = hashingService.encode(command.password());
            user.setPassword(encodedPassword);
        }

        if (command.permissions() != null) {
            Set<Permission> permissions = userPermissionContextFacade.getPermissionsByNames(command.permissions())
                    .stream()
                    .collect(Collectors.toSet());
            user.setPermissions(permissions);
        }

        return Optional.of(userRepository.save(user));
    }

    /**
     * Delete a user
     * <p>
     *     This method deletes a user by their ID.
     * </p>
     * @param userId the user ID to delete
     * @throws RuntimeException if the user is not found
     */
    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }
}

