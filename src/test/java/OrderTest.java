import model.Cart;
import model.Client;
import model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderTest {
    private Order order;
    private Client client;
    private Cart cart;

    @BeforeEach
    void setUp() {
        client = new Client(1L, "John", "Doe", "john.doe@email.com", "CLIENT", "password123");
        cart = new Cart();
        order = new Order(client);
        order.setCartItems(cart);
    }

    @Test
    void testOrderCreation() {
        assertNotNull(order);
        assertEquals(client, order.getClient());
        assertEquals("EN_COURS", order.getStatus().toString());
    }
}

