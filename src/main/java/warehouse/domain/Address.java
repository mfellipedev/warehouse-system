package warehouse.domain;

import java.util.Objects;

public class Address {
    private final Street street;
    private final Column column;
    private final Level level;
    private final String id;

    public Address(Street street, Column column, Level level) {

        this.street = Objects.requireNonNull(street,"A rua não pode ser null");
        this.column = Objects.requireNonNull(column,"A coluna não pode ser null");
        this.level = Objects.requireNonNull(level,"O nível não pode ser null");
        this.id = generateID();
    }

    private String generateID() {
        return street.street() + "-" + column.getColumn() + "-" + level.level();
    }

    public String getId() {
        return id;
    }
}
