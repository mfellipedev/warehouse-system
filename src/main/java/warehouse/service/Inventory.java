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

    public int availableSpace() {
        int space = (this.address.getLevel() * 10) + 10 - this.quantity;
        if (space <= 0) {
            return 0;
        }
        return space;
    }

    public Address getAddress() {
        return address;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    private void validQuantity(int quantity) {
        int limitLevel = (this.address.getLevel() * 10) + 10;
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade não pode ser menor ou igual a 0");
        } else if (quantity > limitLevel) {
            throw new IllegalArgumentException("Quantidade maior que a suportada pelo nível atual");
        }
    }

    public int addQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade minima deve ser maior que 0");
        }
        if (availableSpace() <= 0) {
            throw new IllegalArgumentException("Não há espaço disponível no endereço informado");
        }
        int space = availableSpace();
        this.quantity += Math.min(quantity, space);
        return Math.max(0, quantity - space); //Retorna o restante do armazenamento
    }

    protected int updatingBalance(int quantity) {
        return this.quantity -= quantity;
    }


}
