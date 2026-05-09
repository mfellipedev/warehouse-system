package warehouse.domain;

import java.util.Objects;

public record Column(int column) {
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

    public int getColumn() {
        return column;
    }
}
