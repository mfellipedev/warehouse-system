package warehouse.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public record Street(String street) {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Street {
        if (!street.matches("[A-Z]") || street.isEmpty()) {
            throw new IllegalArgumentException("A rua deve ser uma única letra de A a Z.");
        }
    }

    @JsonValue // <--- Adicione isto
    public String getStreet() { return street; }

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
