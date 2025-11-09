package com.inventiapp.stocktrack.inventory.domain.model.commands;

/**
 * Command que representa la intención de crear un Provider en el dominio.
 * Los campos reflejan lo mínimo necesario para construir el aggregate Provider.
 *
 * Se deja un método de validación simple (validate) para que la capa de aplicación
 * pueda validar reglas básicas antes de persistir o mapear al aggregate.
 */
public record CreateProviderCommand(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String ruc
) {

    /**
     * Validación básica del comando. Lanza IllegalArgumentException si hay campos requeridos faltantes.
     * La capa de aplicación puede llamar a este método antes de ejecutar el caso de uso.
     */
    public void validate() {
        if (isBlank(firstName)) throw new IllegalArgumentException("firstName is required");
        if (isBlank(lastName)) throw new IllegalArgumentException("lastName is required");
        if (isBlank(email)) throw new IllegalArgumentException("email is required");
        if (isBlank(ruc)) throw new IllegalArgumentException("ruc is required");
        // phoneNumber es opcional en este ejemplo
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Convenience factory: crea el comando y lo valida. Útil desde controllers o tests.
     */
    public static CreateProviderCommand ofAndValidate(String firstName, String lastName, String phoneNumber, String email, String ruc) {
        CreateProviderCommand cmd = new CreateProviderCommand(firstName, lastName, phoneNumber, email, ruc);
        cmd.validate();
        return cmd;
    }
}
