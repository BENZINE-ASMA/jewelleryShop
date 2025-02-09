import controller.MainController;
import model.Bijoux;
import model.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainControllerTest {
    @Mock
    private MainController mainController;

    @InjectMocks
    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProductToCart() {
        Bijoux necklace = new Bijoux(2L, "Pearl Necklace", "BrandY", "necklace", "Silver necklace", 150.0, "Silver", "imagePath", 5);
        cart.addToCart(necklace);
        assertEquals(1, cart.getCart().size());
    }
}