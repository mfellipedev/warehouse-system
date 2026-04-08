package warehouse.domain;

public record Column(int column) {
    public Column(int column) {
        if (column <= 0 || column > 10) {
            throw new IllegalArgumentException("Coluna so pode estar entre 1 e 10");
        }
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
}
