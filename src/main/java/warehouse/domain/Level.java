package warehouse.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public record Level(int level) {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Level {
        if (level <= 0 || level > 3) {
            throw new IllegalArgumentException("Level nao pode ser maior que 3, menor ou igual a 0");
        }
    }

    @JsonValue // <--- Adicione isto
    public int level() { return level; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Level level1 = (Level) o;
        return level == level1.level;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level);
    }
}
