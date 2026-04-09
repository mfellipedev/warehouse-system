package warehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import warehouse.domain.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InventoryTest {
    private Street street;
    private Level level;
    private Column column;
    private Product product;
    private Address address;

    @BeforeEach
    void setup() {
        street = new Street("A");
        level = new Level(3);
        column = new Column(10);
        address = new Address(street, column, level);
        product = new Product("Camisa", "2023402342");
    }

    @Test
    void shouldNotCreateInventoryWhenAddressIsNull() {
        Address address = null;
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Inventory(address, product, 30));
    }

    @Test
    void shouldNotCreateInventoryWhenProductIsNull() {
        Product product = null;
        assertThrows(NullPointerException.class, () -> new Inventory(address, product, 30));
    }

    @Test
    void shouldNotCreateInventoryWhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Inventory(address, product, -10));
    }

    @Test
    void shouldNotCreateInventoryWhenQuantityExceedsTheLimitOnLevel3() {
        assertThrows(IllegalArgumentException.class, () -> new Inventory(address, product, 41));
    }
    @Test
    void shouldNotCreateInventoryWhenQuantityExceedsTheLimitOnLevel2() {
        warehouse.domain.Level level2 = new Level(2);
        address = new Address(street, column, level2);
        assertThrows(IllegalArgumentException.class, () -> new Inventory(address, product, 35));
    }
    @Test
    void shouldNotCreateInventoryWhenQuantityExceedsTheLimitOnLevel1() {
        warehouse.domain.Level level1 = new Level(1);
        address = new Address(street, column, level1);
        assertThrows(IllegalArgumentException.class, () -> new Inventory(address, product, 21));
    }
    @Test
    void shouldCreateInventory(){
        assertDoesNotThrow(()->new Inventory(address,product,30));
    }
}
