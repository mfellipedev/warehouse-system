package warehouse.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    @Test
    void shouldNotCreateWarehouseWhenStreetsGreatestThat26() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Warehouse(28,9,0));
    }

    @Test
    void shouldNotCrateWarehouseWhenStreetsSmallerThat1() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Warehouse(0,3,8));
    }

    @Test
    void shouldCreateStreets() {
        assertDoesNotThrow(() -> new Warehouse(26,3,10));
    }
    @Test
    void shouldCreateValidAddress(){
        Warehouse warehouse = new Warehouse(26,3,10);
        assertEquals(780,warehouse.quantityOfAddress());
    }
}
