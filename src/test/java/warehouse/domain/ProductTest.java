package warehouse.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    @Test
    @DisplayName("Não deve permitir que nome seja null")
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Product(null, "12312312s"));
    }

    @Test
    @DisplayName("Não deve permitir a criação com nome vazio")
    void shouldNotCreateProductWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Product("", "123456"));
    }

    @Test
    @DisplayName("Deve conter apenas caracteres entre A e Z")
    void shouldThrowExceptionWhenNameHasSpecialCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new Product("-@", "123123"));
    }

    @Test
    @DisplayName("Não deve permitir caracteres que nao estejam entre 0 e 9")
    void shouldThrowExceptionWhenSkuContainsNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Moletom", "-12312312"));
    }

    @Test
    @DisplayName("SKU não pode conter menos que 5 caracteres")
    void shouldThrowExceptionWhenSkuLengthSmaller5() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Product("Moletom", "1234"));
        assertEquals("O SKU não pode ter menos que 5 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("SKU não pode conter mais que 10 caracteres")
    void shouldThrowWhenSkuLengthGreatheThan10() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Moletom", "12345678901"));
    }

    @Test
    @DisplayName("Product deveria ser valido")
    void shouldCreateValidProduct() {
        Product product = assertDoesNotThrow(() -> new Product("Moletom", "202500780"));
        assertEquals("Moletom", product.getName());
        assertEquals("202500780", product.getSku());
    }
}
