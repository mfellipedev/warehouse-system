package warehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import warehouse.domain.Product;
import warehouse.domain.Warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InventoryManagerTest {
    private Warehouse warehouse;
    private InventoryManager inventoryManager;

    @BeforeEach
    void setup() {
        warehouse = new Warehouse(26, 3, 8);
        inventoryManager = new InventoryManager(warehouse);
    }

    @Test
    void shouldReturnRemainingWhenProductsAreEquals() {
        inventoryManager.store("A-1-1", new Product("Camisa", "202400332"), 10);
        assertEquals(9, inventoryManager.store("A-1-1", inventoryManager.getInventory("A-1-1").getProduct(), 19));
    }

    @Test
    void shouldReturnZeroWhenFirstStoreSuccess() {
        assertEquals(0, inventoryManager.store("A-1-1", new Product("Camisa", "202400332"), 10));
    }

    @Test
    void shouldNotStoreWhenProductsAreDifferent() {
        inventoryManager.store("A-1-1", new Product("Camisa", "202400332"), 10);
        assertThrows(IllegalArgumentException.class, () -> inventoryManager.store("A-1-1", new Product("Moletom", "20229099"), 20));
    }
}
//