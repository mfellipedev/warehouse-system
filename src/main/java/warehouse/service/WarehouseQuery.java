package warehouse.service;

import warehouse.domain.Address;
import warehouse.domain.Street;

import java.util.*;

public class WarehouseQuery {
    private final InventoryManager inventoryManager;

    public WarehouseQuery(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public List<Address> addressesQuery() {
        TreeMap<String, Address> addresses = this.inventoryManager.getWarehouse().returnAddresses();
        List<Address> listOfAdresses = new ArrayList<>(addresses.values());

        Collections.sort(listOfAdresses, Comparator.comparing((Address a) -> a.getId().split("-")[0]).thenComparing((Address a) -> Integer.valueOf(a.getId().split("-")[1])));
        return listOfAdresses;
    }

    public List<Street> streetsQuery() {
        List<Street> listStreets = new ArrayList<>(this.inventoryManager.getWarehouse().returnStreets());
        return listStreets;
    }

    public List<String> queryProductInStock(String product) {
        Map<String, Inventory> inventory = new HashMap<>(this.inventoryManager.returnInventory());
        List<String> listProductInStock = new ArrayList<>();

        for (Inventory inv : inventory.values()) {
            if (inv.getProduct().sku().equals(product)) {
                listProductInStock.add("ID: " + inv.getAddress().getId() + " | " + "Quantidade: " + inv.getQuantity());
            }
        }
        return listProductInStock;
    }

}
