package warehouse.domain;

public record Product(String name, String sku) {

    public Product {
        validName(name);
        validSKU(sku);
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    private void validName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("O nome não pode ser um Null");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode estar vazio");
        }
        if (!name.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Nome não pode conter letras fora de A - Z");
        }

    }

    private void validSKU(String sku) {
        if (sku == null) {
            throw new IllegalArgumentException("O SKU é Null");
        }
        if (sku.length() > 10) {
            throw new IllegalArgumentException("O SKU não pode ter mais que 10 caracteres");
        }
        if (sku.length() < 5) {
            throw new IllegalArgumentException("O SKU não pode ter menos que 5 caracteres");
        }
        if (!sku.matches("[0-9]+")) {
            throw new IllegalArgumentException("O SKU não pode ter nada alem de números entre 0 e 9");
        }
    }
}

