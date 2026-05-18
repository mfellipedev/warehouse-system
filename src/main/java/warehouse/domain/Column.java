package warehouse.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public record Column(int column) {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Column(int column) {
        if (column <= 0 || column > 10) {
            throw new IllegalArgumentException("Coluna so pode estar entre 1 e 10");
        }
        this.column = column;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Column column1 = (Column) o;
        return column == column1.column;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(column);
    }

    @JsonValue
    public int getColumn() {
        return column;
    }
}
