package warehouse.service;

import warehouse.domain.Address;
import warehouse.domain.Street;

import java.util.*;

public class WarehouseQuery {
    private InventoryManager inventoryManager;

    public WarehouseQuery(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    protected WarehouseQuery() {
    }

    public List<Address> addressesQuery() {
        TreeMap<String, Address> addresses = this.inventoryManager.getWarehouse().returnAddresses();
        List<Address> listOfAddresses = new ArrayList<>(addresses.values());

        Collections.sort(listOfAddresses, Comparator.comparing((Address a) -> a.getStreet().street()).thenComparingInt(Address::getLevel).thenComparingInt(Address::getColumnNumber));

        return listOfAddresses;
    }

    public List<Street> streetsQuery() {
        return new ArrayList<>(this.inventoryManager.getWarehouse().returnStreets());
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

    public Map<String, String> completeAddressesQuery() {
        List<Address> listAddresses = this.addressesQuery();
        LinkedHashMap<String, String> mapAddresses = new LinkedHashMap<>();
        for (Address a : listAddresses) {
            Inventory inventory = inventoryManager.findInventory(a.getId());
            if (inventory != null) {
                mapAddresses.put(a.getId(), inventory.getProduct().toString() + " | Quantidade: " + inventory.getQuantity());
            } else {
                mapAddresses.put(a.getId(), "Endereço Vazio");
            }
        }
        return mapAddresses;
    }
}
