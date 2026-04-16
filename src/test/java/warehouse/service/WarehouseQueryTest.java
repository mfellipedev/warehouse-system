package warehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import warehouse.domain.Warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehouseQueryTest {
    private InventoryManager inventoryManager;
    private Warehouse warehouse;
    private WarehouseQuery warehouseQuery;

    @BeforeEach
    void setup(){
        this.warehouse = new Warehouse(26,3,10);
        this.inventoryManager = new InventoryManager(warehouse);
        this.warehouseQuery = new WarehouseQuery(inventoryManager);
    }

    @Test
    void shouldReturnListSizeEqualsToZeroWhenProductIsNull(){
        assertEquals(0, warehouseQuery.queryProductsInStock(null).size());
    }
}
