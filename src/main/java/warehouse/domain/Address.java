package warehouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Represents a unique physical address location within the warehouse.
 * It combines a specific street, column, and level to identify where products are stored.
 */

public class Address {
    private Street street;
    private Column column;
    private Level level;
    private String id;

    /**
     * Construct a new Address with validated components and automatically generates unique ID.
     * @param street The street letter designation (A-Z).
     * @param column The vertical columm location (1-10).
     * @param level The horizontal level (1-3).
     * @throws NullPointerException If any of the provided components are null.
     */

    public Address(Street street, Column column, Level level) {

        this.street = Objects.requireNonNull(street, "A rua não pode ser null");
        this.column = Objects.requireNonNull(column, "A coluna não pode ser null");
        this.level = Objects.requireNonNull(level, "O nível não pode ser null");
        this.id = generateID();
    }

    /**
     * No argument constructor required by Jackson for JSON deserialization.
     */
    protected Address() {
    }

    /**
     * Generates a unique string identifier based on the street, level and column.
     * @return A formatted string ID (A-1-1).
     */
    private String generateID() {
        return street.street() + "-" + level.level() + "-" + column.getColumn();
    }

    /**
     * @return The unique generated identifier for this address.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The integer value for the address level.
     */
    public int getLevel() {
        return level.level();
    }

    /**
     * @return The Column value object.
     */
    public Column getColumn() {
        return column;
    }

    /**
     * @return The Street value object.
     */
    public Street getStreet() {
        return street;
    }

    /**
     * Helper method to extract the raw column primitive integer.
     * Ignored by Jackson serialization to avoid schema conflicts.
     * @return The raw primitive integer of the column.
     */
    @JsonIgnore
    public int getColumnNumber() {
        return column.getColumn();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(this.street, address.street)
                && Objects.equals(this.column, address.column)
                && Objects.equals(this.level, address.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, column, level);
    }

    @Override
    public String toString() {
        return "ID Endereço: " + this.id + "| Rua: " + this.street.street() + "| Nível: " + this.level.level() + "| Coluna: " + this.column.getColumn() + "\n";
    }
}
