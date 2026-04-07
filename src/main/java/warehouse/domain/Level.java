package warehouse.domain;

public record Level(int level) {
    public Level {
        if (level <= 0 || level > 3) {
            throw new IllegalArgumentException("Level nao pode ser maior que 3, menor ou igual a 0");
        }
    }
}
