package com.inventiapp.stocktrack.iam.domain.model.entities;

import com.inventiapp.stocktrack.iam.domain.model.valueobjects.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

/**
 * Entity representing a user role
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Roles name;

    public Role(Roles name) {
        this.name = name;
    }

    /**
     * Get the default role for new users
     * @return Role with ROLE_SELLER
     */
    public static Role getDefaultRole() {
        return new Role(Roles.ROLE_SELLER);
    }

    /**
     * Convert a string name to a Role object
     * @param name The role name as string
     * @return Role object
     */
    public static Role toRoleFromName(String name) {
        return new Role(Roles.valueOf(name));
    }

    /**
     * Validate and return a role set. If empty, returns default role
     * @param roles List of roles to validate
     * @return List with at least the default role
     */
    public static List<Role> validateRoleSet(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of(getDefaultRole());
        }
        return roles;
    }

    /**
     * Get the string name of the role
     * @return Role name as string
     */
    public String getStringName() {
        return name.name();
    }
}

