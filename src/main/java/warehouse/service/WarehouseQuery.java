package warehouse.service;

import warehouse.domain.Address;
import warehouse.domain.Street;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseQuery {
    private final InventoryManager inventoryManager;

    public WarehouseQuery(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public List<Address> addressesQuery() {
        Map<String, Address> addresses = this.inventoryManager.getWarehouse().returnAddresses();
        List<Address> listOfAdresses = new ArrayList<>(addresses.values());
        return listOfAdresses;
    }

    public List<Street> streetsQuery() {
        List<Street> listStreets = new ArrayList<>(this.inventoryManager.getWarehouse().returnStreets());
        return listStreets;
    }

    public List<String> queryProductsInStock(String product) {
        Map<String, Inventory> inventory = new HashMap<>(this.inventoryManager.returnInventory());
        List<String> listProductInStock = new ArrayList<>();

        for (Inventory inv : inventory.values()) {
            if (inv.getProduct().sku().equals(product)) {
                listProductInStock.add(inv.getAddress().getId() + "- " + inv.getQuantity());
            }
        }
        return listProductInStock;
    }

}
