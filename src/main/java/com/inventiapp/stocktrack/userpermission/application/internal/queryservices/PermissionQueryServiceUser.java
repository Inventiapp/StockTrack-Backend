package com.inventiapp.stocktrack.userpermission.application.internal.queryservices;

import com.inventiapp.stocktrack.userpermission.domain.model.aggregates.Permission;
import com.inventiapp.stocktrack.userpermission.domain.model.queries.GetAllPermissionQuery;
import com.inventiapp.stocktrack.userpermission.domain.model.queries.GetPermissionByIdQuery;
import com.inventiapp.stocktrack.userpermission.domain.model.queries.GetPermissionByNameQuery;
import com.inventiapp.stocktrack.userpermission.domain.services.PermissionQueryService;
import com.inventiapp.stocktrack.userpermission.infrastructure.persistence.jpa.repositories.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionQueryServiceUser implements PermissionQueryService {

    private final PermissionRepository permissionRepository;

    public PermissionQueryServiceUser(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<Permission> handle(GetAllPermissionQuery query) {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> handle(GetPermissionByIdQuery query) {
        return permissionRepository.findById(query.id());
    }

    @Override
    public Optional<Permission> handle(GetPermissionByNameQuery query) {
        return permissionRepository.findByName(query.name());
    }
}