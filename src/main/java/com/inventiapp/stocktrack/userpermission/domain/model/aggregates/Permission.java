package com.inventiapp.stocktrack.userpermission.domain.model.aggregates;

import com.inventiapp.stocktrack.userpermission.domain.model.commands.CreatePermissionCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    /**
     * Constructor para crear un nuevo Permiso.
     * Se invoca a través del Command.
     */
    public Permission(CreatePermissionCommand command) {
        this.name = command.name();
        this.description = command.description();
    }

    // Métodos para actualizar la entidad (si los necesitas)
    public void updateName(String newName) {
        this.name = newName;
    }
}
