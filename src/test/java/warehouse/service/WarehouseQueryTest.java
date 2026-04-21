package warehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import warehouse.domain.Product;
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
    void shouldReturnListSizeEqualsToZeroWhenProductNotExistOrIsNulll(){
        assertEquals(0, warehouseQuery.queryProductInStock(null).size());
    }
   @Test
    void shoulReturnListSizeEqualsToOneWhenQueryIsValid(){
       Product productTest = new Product("Moletom","202932032");
       this.inventoryManager.store("A-1-1",productTest,20);
       assertEquals(1,this.warehouseQuery.queryProductInStock("202932032").size());
       assertEquals("ID: A-1-1 | Quantidade: 20",this.warehouseQuery.queryProductInStock("202932032").get(0));
   }

@Test
    void shouldReturnAllAddresses(){
        assertEquals(780,this.warehouseQuery.addressesQuery().size());
}
@Test
    void shouldReturnCorrectOutput(){
        assertEquals("A-1-1",this.warehouseQuery.addressesQuery().get(0).getId());
}

@Test
    void shouldReturnCorrectOutputOfStreets(){
        assertEquals("A",this.warehouseQuery.streetsQuery().get(0).street());
}
}
