package com.inventiapp.stocktrack.userpermission.interfaces.rest;

import com.inventiapp.stocktrack.userpermission.domain.model.queries.GetAllPermissionQuery;
import com.inventiapp.stocktrack.userpermission.domain.model.queries.GetPermissionByIdQuery;
import com.inventiapp.stocktrack.userpermission.domain.services.PermissionCommandService;
import com.inventiapp.stocktrack.userpermission.domain.services.PermissionQueryService;
import com.inventiapp.stocktrack.userpermission.interfaces.rest.resources.CreatePermissionResource;
import com.inventiapp.stocktrack.userpermission.interfaces.rest.resources.PermissionResource;
import com.inventiapp.stocktrack.userpermission.interfaces.rest.transform.CreatePermissionCommandFromResourceAssembler;
import com.inventiapp.stocktrack.userpermission.interfaces.rest.transform.PermissionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Permissions", description = "Permission Management Endpoints")
public class PermissionController {

    private final PermissionCommandService permissionCommandService;
    private final PermissionQueryService permissionQueryService;

    // Inyección de dependencias por constructor
    public PermissionController(PermissionCommandService permissionCommandService, PermissionQueryService permissionQueryService) {
        this.permissionCommandService = permissionCommandService;
        this.permissionQueryService = permissionQueryService;
    }

    /**
     * Endpoint para crear un nuevo permiso.
     */
    @PostMapping
    public ResponseEntity<PermissionResource> createPermission(@RequestBody CreatePermissionResource resource) {
        // 1. Convertir Resource (DTO) a Command
        var command = CreatePermissionCommandFromResourceAssembler.toCommandFromResource(resource);
        
        // 2. Enviar el comando al servicio
        var permissionId = permissionCommandService.handle(command);
        
        // 3. Si tuvo éxito, buscar el permiso recién creado para devolverlo
        var getPermissionQuery = new GetPermissionByIdQuery(permissionId);
        var permission = permissionQueryService.handle(getPermissionQuery).orElseThrow();
        
        // 4. Convertir la Entidad a Resource (DTO) para la respuesta
        var permissionResource = PermissionResourceFromEntityAssembler.toResourceFromEntity(permission);
        
        return new ResponseEntity<>(permissionResource, HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener un permiso por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResource> getPermissionById(@PathVariable Long id) {
        var getPermissionQuery = new GetPermissionByIdQuery(id);
        var permission = permissionQueryService.handle(getPermissionQuery);
        
        if (permission.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var permissionResource = PermissionResourceFromEntityAssembler.toResourceFromEntity(permission.get());
        return ResponseEntity.ok(permissionResource);
    }

    /**
     * Endpoint para obtener todos los permisos.
     */
    @GetMapping
    public ResponseEntity<List<PermissionResource>> getAllPermissions() {
        var getAllQuery = new GetAllPermissionQuery();
        var permissions = permissionQueryService.handle(getAllQuery);
        
        var permissionResources = permissions.stream()
                .map(PermissionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(permissionResources);
    }
}