import controller.MainController;
import model.Invoice;
import model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InvoiceTest {
    @Mock
    private MainController mainController;

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        invoice = new Invoice(1L, 1L, 100L, "INV-001", "invoicePath.pdf", 300.0, "Pending", null, null);
    }

    @Test
    void testGenerateInvoice() {
        Order order = mock(Order.class);
        when(order.getOrderId()).thenReturn(100L);

        assertEquals("INV-001", invoice.getInvoiceNumber());
        assertEquals(300.0, invoice.getTotalAmount());
    }
}
