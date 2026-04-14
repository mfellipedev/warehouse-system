package warehouse.service;

import warehouse.domain.Address;
import warehouse.domain.Product;
import warehouse.domain.Warehouse;

import java.util.HashMap;

public class InventoryManager {
    private final HashMap<String, Inventory> inventory;
    private final Warehouse warehouse;

    public InventoryManager(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.inventory = new HashMap<>();

    }

    public int store(String id, Product product, int quantity) {
        Address address = warehouse.getAddress(id);
        Inventory inventoryActual = this.inventory.get(id);
        if (inventoryActual != null && inventoryActual.getProduct().equals(product)) {
            return inventoryActual.addQuantity(quantity);
        } else if (inventoryActual == null) {
            this.inventory.put(id, new Inventory(address, product, quantity));
            return 0;
        } else {
            throw new IllegalArgumentException("Não é possível armazenar produtos diferentes");
        }
    }

    public Inventory getInventory(String id) {
        return this.inventory.get(id);
    }

    public int remove(String id, Product product, int quantity) {
        Address address = this.warehouse.getAddress(id);
        if (product == null) {
            throw new IllegalArgumentException("Verifique se o endereço ou produto estão corretos");
        }
        Inventory inventory1 = getInventory(address.getId());
        if (inventory1 == null) {
            throw new IllegalArgumentException("Não existe um produto armazenado no endereço");
        }
        if (quantity <= 0 || inventory1.getQuantity() < quantity) {
            throw new IllegalArgumentException("Quantidade invalida para remoção");
        } else if (!inventory1.getProduct().equals(product)) {
            throw new IllegalArgumentException("O produto não pode ser diferente do armazenado");
        }
        int status = inventory1.updatingBalance(quantity);
        if (status == 0) {
            this.inventory.remove(address.getId());
        }
        return status;

    }


}
