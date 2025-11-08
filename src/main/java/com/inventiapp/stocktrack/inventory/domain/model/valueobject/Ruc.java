package com.inventiapp.stocktrack.inventory.domain.model.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Ruc {

    @Column(name = "ruc", nullable = false, length = 11)
    private String value;

    protected Ruc() {} // JPA

    public Ruc(String value) {
        if (value == null || !isValidRuc(value)) {
            throw new IllegalArgumentException("RUC inválido");
        }
        this.value = value;
    }

    private boolean isValidRuc(String ruc) {
        // Validación simple: longitud 11 y sólo dígitos.
        return ruc.matches("\\d{11}");
        // Si quieres, puedes implementar validación de dígito verificador real.
    }

    public String value() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ruc)) return false;
        Ruc ruc = (Ruc) o;
        return Objects.equals(value, ruc.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}