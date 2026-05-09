package warehouse.service;

import warehouse.domain.Address;
import warehouse.domain.Product;
import warehouse.domain.TransactionType;
import warehouse.domain.Warehouse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private final HashMap<String, Inventory> inventory;
    private final Warehouse warehouse;
    private LogManager logManager;
    private HashMap<String, Product> products;

    public InventoryManager(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.inventory = new HashMap<>();
        this.logManager = new LogManager();
        this.products = new HashMap<>();

    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Map<String, Inventory> returnInventory() {
        Map<String, Inventory> listOfIventory = new HashMap<>(this.inventory);
        return listOfIventory;
    }

    public int store(String id, Product product, int quantity) {
        return store(id, product, quantity, TransactionType.ARMAZENAMENTO);
    }

    public int store(String id, Product product, int quantity, TransactionType type) {
        Address address = warehouse.getAddress(id);
        Inventory inventoryActual = this.inventory.get(id);
        LocalDateTime dateActual = LocalDateTime.now();
        if (inventoryActual != null && inventoryActual.getProduct().equals(product)) {
            logManager.registerLog(dateActual, type, inventoryActual, quantity);
            return inventoryActual.addQuantity(quantity);
        } else if (inventoryActual == null) {
            Inventory newInventory = new Inventory(address, product, quantity);
            this.inventory.put(id, newInventory);
            logManager.registerLog(dateActual, type, newInventory, quantity);
            return 0;
        } else {
            throw new IllegalArgumentException("Não é possível armazenar produtos diferentes");
        }
    }

    public Inventory getInventory(String id) {
        Inventory inventoryReturn = this.inventory.get(id);
        if (inventoryReturn == null) {
            throw new IllegalArgumentException("Não existe nenhum produto armazenado no endereço");
        }
        return inventoryReturn;
    }

    public Inventory findInventory(String id) {
        Inventory findInventory = this.inventory.get(id);
        return findInventory;
    }

    public int remove(String id, String sku, int quantity) {
        return remove(id, sku, quantity, TransactionType.REMOCAO);
    }

    public int remove(String id, String sku, int quantity, TransactionType type) {
        Address address = this.warehouse.getAddress(id);
        Product product = searchProduct(id, sku);
        if (product == null) {
            throw new IllegalArgumentException("Verifique se o produto esta correto");
        }
        Inventory inventory1 = getInventory(address.getId());
        if (quantity <= 0 || inventory1.getQuantity() < quantity) {
            throw new IllegalArgumentException("Quantidade invalida para remoção");
        } else if (!inventory1.getProduct().equals(product)) {
            throw new IllegalArgumentException("O produto não pode ser diferente do armazenado");
        }
        logManager.registerLog(LocalDateTime.now(), type, inventory1, quantity);
        int status = inventory1.updatingBalance(quantity);
        if (status == 0) {
            this.inventory.remove(address.getId());
        }
        return status;
    }

    public boolean transfer(String origin, String dest, int quantity) {
        Address originAddress = this.warehouse.getAddress(origin);
        Address destAddress = this.warehouse.getAddress(dest);
        Inventory originInventory = getInventory(originAddress.getId());
        if (findInventory(dest) == null) {
            if (originInventory.spaceAddress(destAddress) < quantity) {
                throw new IllegalArgumentException("Quantidade maior que o suportado pelo endereço destino");
            }
        }
        Inventory destInventory = findInventory(dest);
        if (destInventory != null && !destInventory.getProduct().equals(originInventory.getProduct())) {
            throw new IllegalArgumentException("Não é possível armazenar dois produtos diferentes no mesmo endereço");
        }
        store(dest, originInventory.getProduct(), quantity, TransactionType.ENTRADA_TRANSFERENCIA);
        remove(origin, originInventory.getProduct().getSku(), quantity, TransactionType.SAIDA_TRANSFERENCIA);
        return true;
    }

    private Product searchProduct(String id, String sku) {
        Inventory inv = findInventory(id);

        if (inv == null) {
            return null;
        }

        Product product = inv.getProduct();

        if (product == null) {
            return null;
        }

        if (!product.sku().equals(sku)) {
            return null;
        }

        return product;
    }

    public LogManager returnLogManager() {
        return logManager;
    }

    public void addProduct(String sku, Product product) {
        if (sku == null || product == null) {
            throw new IllegalArgumentException("Parâmetros Inválidos");
        }
        this.products.put(sku, product);
    }

    public String getDashboard() {
        int totalProducts = this.products.size();
        int totalUnits = 0;
        int occupiedAddresses = this.inventory.size();

        for (Inventory inv : this.inventory.values()) {
            totalUnits += inv.getQuantity();
        }

        return String.format(
                "Total de produtos cadastrados: %d\nEndereços Ocupados: %d\nTotal de Unidades: %d\n",
                totalProducts, occupiedAddresses, totalUnits
        );
    }

}
