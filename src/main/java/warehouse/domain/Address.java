package warehouse.domain;

import java.util.Objects;

public class Address {
    private final Street street;
    private final Column column;
    private final Level level;
    private final String id;

    public Address(Street street, Column column, Level level) {

        this.street = Objects.requireNonNull(street, "A rua não pode ser null");
        this.column = Objects.requireNonNull(column, "A coluna não pode ser null");
        this.level = Objects.requireNonNull(level, "O nível não pode ser null");
        this.id = generateID();
    }

    private String generateID() {
        return street.street() + "-" + level.level() + "-" + column.getColumn();
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level.level();
    }

    public Column getColumn() {
        return column;
    }

    public Street getStreet() {return street;}

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
