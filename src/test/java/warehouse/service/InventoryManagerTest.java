package warehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import warehouse.domain.*;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryManagerTest {
    private Warehouse warehouse;
    private InventoryManager inventoryManager;
    private Street street;
    private Level level;
    private Column column;
    private Address address;
    private Product product;

    @BeforeEach
    void setup() {
        warehouse = new Warehouse(26, 3, 8);
        inventoryManager = new InventoryManager(warehouse);
        street = new Street("A");
        level = new Level(3);
        column = new Column(10);
        address = new Address(street, column, level);
        product = new Product("Camisa", "2023402342");
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

    @Test
    void shouldNotRemoveWhenAddressIsNull() {
        assertThrows(IllegalArgumentException.class, () -> inventoryManager.remove(null, product, 20));
    }

    @Test
    void shouldNotRemoveWhenProductIsNull() {
        product = null;
        assertThrows(IllegalArgumentException.class, () -> inventoryManager.remove("A-1-1", product, 20));
    }

    @Test
    void shouldNotRemoveWhenQuantityIsGreatherThanBalance() {
        Product productTest = new Product("Cinto", "202903223");
        inventoryManager.store("A-1-1", productTest, 20);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> inventoryManager.remove("A-1-1", productTest, 21));
        assertEquals("Quantidade invalida para remoção", exception.getMessage());
    }

    @Test
    void shouldNotRemoveWhenProductIsDifferent() {
        Product productTest = new Product("Cinto", "202903223");
        Product productDifferent = new Product("Meia", "202912901");
        inventoryManager.store("A-1-1", productTest, 20);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> inventoryManager.remove("A-1-1", productDifferent, 20));
        assertEquals("O produto não pode ser diferente do armazenado", exception.getMessage());
    }

    @Test
    void shouldUpdatingBalance() {
        Product productTest = new Product("Cinto", "202903223");
        inventoryManager.store("A-1-1", productTest, 20);
        inventoryManager.remove("A-1-1", productTest, 19);
        assertEquals(1, this.inventoryManager.getInventory("A-1-1").getQuantity());
    }

    @Test
    void shouldDeleteInventoryWhenBalanceEqualsToZero() {
        Product productTest = new Product("Cinto", "202903223");
        inventoryManager.store("A-1-1", productTest, 20);
        inventoryManager.remove("A-1-1", productTest, 20);
        assertNull(this.inventoryManager.getInventory("A-1-1"));
    }

    @Test
    void shouldNotTransferWhenProductIsDifferent() {
        Product productTest = new Product("Cinto", "202903223");
        inventoryManager.store("A-1-1", productTest, 20);
        Product productTest2 = new Product("Bermuda", "2029203923");
        inventoryManager.store("A-1-2", productTest2, 10);
        assertThrows(IllegalArgumentException.class, () -> inventoryManager.transfer("A-1-1", "A-1-2", 10));
    }

    @Test
    void shouldNotTransferWhenQuantityExceedsAvailableSpace() {
        Product productTest = new Product("Cinto", "202903223");
        inventoryManager.store("A-1-1", productTest, 20);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> inventoryManager.transfer("A-1-1", "A-3-3", 50));
        assertEquals("Quantidade maior que o suportado pelo endereço destino", exception.getMessage());
    }

    @Test
    void shouldNotTransferWhenParametersIsNull() {
        Address address1 = null;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> inventoryManager.transfer("KL-1-1", "A-2-2", 20));
        assertEquals("O endereço não pode ser encontrado!",exception.getMessage());
    }

    @Test
    void shouldTransfer(){
        Product productTest = new Product("Cinto", "202903223");
        inventoryManager.store("A-1-1", productTest, 20);
        assertTrue(inventoryManager.transfer("A-1-1","A-2-3",20));
    }
}