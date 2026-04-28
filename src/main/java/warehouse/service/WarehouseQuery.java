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

        Collections.sort(listOfAdresses, Comparator.comparing((Address a) -> a.getStreet().street()).thenComparingInt(Address::getLevel).thenComparingInt(Address::getColumnNumber));

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

    public Map<String, String> completeAdressesQuery() {
        List<Address> listAddresses = this.addressesQuery();
        LinkedHashMap<String, String> mapAddresses = new LinkedHashMap<>();
        for (Address a : listAddresses) {
            Inventory inventory = inventoryManager.findInventory(a.getId());
            if (inventory != null) {
                mapAddresses.put(a.getId(), inventory.getProduct().toString() + " | Quantidade: "+  inventory.getQuantity());
            } else {
                mapAddresses.put(a.getId(), "Endereço Vazio");
            }
        }
        return mapAddresses;
    }
}
