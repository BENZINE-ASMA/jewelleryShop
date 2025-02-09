import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import controller.MainController;
import model.Bijoux;
import model.Cart;
import model.Client;
import model.Order;
import model.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;

class CartTest {
    private Cart cart;
    private Bijoux ring;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        ring = new Bijoux(1L, "Diamond Ring", "BrandX", "ring", "Gold ring", 200.0, "Gold", "imagePath", 10);
    }

    @Test
    void testAddToCart() {
        cart.addToCart(ring);
        assertEquals(1, cart.getCart().size());
        assertTrue(cart.getCart().containsKey(ring));
    }

    @Test
    void testRemoveFromCart() {
        cart.addToCart(ring);
        cart.deleteFromCart(ring.getId());
        assertEquals(0, cart.getCart().size());
    }
}



