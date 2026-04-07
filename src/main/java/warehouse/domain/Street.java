package warehouse.domain;

public record Street(String street) {
    public Street {
        if (!street.matches("[A-Z]") || street.isEmpty()) {
            throw new IllegalArgumentException("A rua deve ser uma única letra de A a Z.");
        }
    }
}
