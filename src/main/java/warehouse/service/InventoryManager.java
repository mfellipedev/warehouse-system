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

    public Inventory getInventory(String id){
        return this.inventory.get(id);
    }
}
