package warehouse.domain;

import java.util.Objects;

public record Street(String street) {
    public Street {
        if (!street.matches("[A-Z]") || street.isEmpty()) {
            throw new IllegalArgumentException("A rua deve ser uma única letra de A a Z.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Street street1 = (Street) o;
        return Objects.equals(street, street1.street);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(street);
    }

    @Override
    public String toString() {
        return this.street;
    }
}
