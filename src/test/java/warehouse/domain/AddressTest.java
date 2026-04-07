package warehouse.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {
    @Test
    void shouldCreateValidID() {
        Street street = new Street("A");
        Column column = new Column(1);
        Level level = new Level(1);
        Address address = new Address(street, column, level);

        assertEquals("A-1-1", address.getId());
    }
    @Test
    void shouldNotCreateAddressWhenObjectsIsNull(){
        Street street = new Street("A");
        Column column = null;
        Level level = null;
        NullPointerException exception = assertThrows(NullPointerException.class,()-> new Address(street,column,level));
        assertEquals("A coluna não pode ser null",exception.getMessage());
    }
    @Test
    void shouldReturnValidID(){
        Street street = new Street("A");
        Column column = new Column(4);
        Level level = new Level(3);

        Address address = new Address(street,column,level);
        assertEquals("A-4-3",address.getId());
    }
}