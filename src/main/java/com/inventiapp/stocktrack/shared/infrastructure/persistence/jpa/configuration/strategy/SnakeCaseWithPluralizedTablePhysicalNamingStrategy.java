package com.inventiapp.stocktrack.shared.infrastructure.persistence.jpa.configuration.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import static io.github.encryptorcode.pluralize.Pluralize.pluralize;

/**
 * Custom Hibernate PhysicalNamingStrategy that converts identifiers to snake_case
 * and pluralizes table names for database compatibility.
 * <p>
 * This strategy ensures that Java entity names are transformed into appropriate
 * database naming conventions, such as converting CamelCase to snake_case and
 * pluralizing table names.
 * </p>
 */
public class SnakeCaseWithPluralizedTablePhysicalNamingStrategy implements PhysicalNamingStrategy {
    /**
     * Converts the logical catalog name to its physical representation in snake_case.
     *
     * @param identifier      the logical identifier
     * @param jdbcEnvironment the JDBC environment
     * @return the physical identifier in snake_case, or null if the input is null
     */
    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    /**
     * Converts the logical schema name to its physical representation in snake_case.
     *
     * @param identifier      the logical identifier
     * @param jdbcEnvironment the JDBC environment
     * @return the physical identifier in snake_case, or null if the input is null
     */
    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    /**
     * Converts the logical table name to its physical representation in snake_case and pluralized.
     *
     * @param identifier      the logical identifier
     * @param jdbcEnvironment the JDBC environment
     * @return the physical identifier in snake_case and pluralized, or null if the input is null
     */
    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(this.toPlural(identifier));
    }

    /**
     * Converts the logical sequence name to its physical representation in snake_case.
     *
     * @param identifier      the logical identifier
     * @param jdbcEnvironment the JDBC environment
     * @return the physical identifier in snake_case, or null if the input is null
     */
    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    /**
     * Converts the logical column name to its physical representation in snake_case.
     *
     * @param identifier      the logical identifier
     * @param jdbcEnvironment the JDBC environment
     * @return the physical identifier in snake_case, or null if the input is null
     */
    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    /**
     * Converts the given identifier to snake_case format.
     * <p>
     * This method transforms CamelCase strings to snake_case by inserting underscores
     * between lowercase and uppercase letters, and converting the entire string to lowercase.
     * </p>
     *
     * @param identifier the identifier to convert; may be null
     * @return the identifier in snake_case, or null if the input is null
     */
    private Identifier toSnakeCase(final Identifier identifier) {
        if (identifier == null) return null;
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = identifier.getText()
                .replaceAll(regex, replacement)
                .toLowerCase();
        return Identifier.toIdentifier(newName);
    }

    /**
     * Converts the given identifier to its plural form.
     *
     * @param identifier the identifier to pluralize
     * @return the pluralized identifier
     */
    private Identifier toPlural(final Identifier identifier) {
        final String newName = pluralize(identifier.getText());
        return Identifier.toIdentifier(newName);
    }
}