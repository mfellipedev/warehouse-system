package warehouse.service;

import warehouse.domain.Address;
import warehouse.domain.Product;

import java.util.Objects;

public class Inventory {
    private final Address address;
    private final Product product;
    private int quantity;

    public Inventory(Address address, Product product, int quantity) {
        this.address = Objects.requireNonNull(address, "Um endereço não pode ser null");
        this.product = Objects.requireNonNull(product, "Um produto não pode ser null");
        validQuantity(quantity);
        this.quantity = quantity;
    }

    private void validQuantity(int quantity) {
        int limitLevel = (this.address.getLevel() * 10) + 10;
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade não pode ser menor ou igual a 0");
        } else if (quantity > limitLevel) {
            throw new IllegalArgumentException("Quantidade maior que a suportada pelo nível atual");
        }


    }


}
