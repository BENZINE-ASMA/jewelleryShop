package shared;

import java.util.Map;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import model.*;

public class InvoiceGenerator {
    private Order clientOrder;
    private Cart clientCart;
    private Client loggedInClient;
    private EmailSender emailSender;

    public InvoiceGenerator(Order order, Client client, Cart clientCart) {
        this.clientOrder = order;
        this.loggedInClient = client;
        this.clientCart = clientCart;
        this.emailSender = new EmailSender("asma.benzine010@gmail.com");
    }

    public void generateInvoice(String path) {
        String filePath = "src/main/resources/output/" + path;
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Invoice")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(" "));

            Paragraph billingAddress = new Paragraph("Billing Address\n" +
                    "Client: " + this.loggedInClient.getFirstName() + " " + this.loggedInClient.getLastName() + "\n" +
                    "Email: " + this.loggedInClient.getEmail() + "\n" +
                    "Client ID: " + this.loggedInClient.getId());
            billingAddress.setTextAlignment(TextAlignment.RIGHT);
            document.add(billingAddress);

            document.add(new Paragraph("Invoice Details:")
                    .setFontSize(10)
                    .setBold());
            document.add(new Paragraph("Hello " + this.loggedInClient.getFirstName()));
            document.add(new Paragraph("Thank you for your purchase at Precious. Here is a summary of your order:"));
            //document.add(new Paragraph("Price per Item: $5.00"));
            document.add(new Paragraph("Total: "+ this.clientCart.getTotalPrice()));

            Table table = new Table(5);
            table.addCell(new Cell().add(new Paragraph("Quantity")));
            table.addCell(new Cell().add(new Paragraph("Item ID")));
            table.addCell(new Cell().add(new Paragraph("Item Name")));
            table.addCell(new Cell().add(new Paragraph("Unit Price (EUR)")));
            table.addCell(new Cell().add(new Paragraph("Total Price (EUR)")));

            for (Map.Entry<Bijoux, Integer> entry : clientCart.getCart().entrySet()) {
                Bijoux item = entry.getKey();
                int quantity = entry.getValue();

                table.addCell(new Cell().add(new Paragraph(String.valueOf(quantity))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getId()))));
                table.addCell(new Cell().add(new Paragraph(item.getName() + " from brand " + item.getBrand())));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", item.getPrice()))));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", item.getPrice() * quantity))));
            }
            document.add(table);

            document.close();
            System.out.println("PDF generated successfully at: " + filePath);

            String subject = "Invoice for your order "+ clientOrder.getOrderId() ;
            String messageText = "Hello " + this.loggedInClient.getFirstName() + ",\n\n"
                    + "Thank you for your purchase at Precious.\n"
                    + "Please find attached the invoice corresponding to your order.\n\n"
                    + "Best regards,\n"
                    + "The Precious Team";

            this.emailSender.sendEmailWithAttachment(subject, messageText, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
