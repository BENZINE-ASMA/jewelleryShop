package shared;

import java.util.Map;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

import model.Bijoux;
import model.Cart;
import model.Client;
import model.Order;

public class InvoiceGenerator {
    private Order clientOrder;
    private Cart clientCart;
    private Client loggedInClient;

    public InvoiceGenerator(Order order, Client client, Cart clientCart) {
        this.clientOrder = order;
        this.loggedInClient = client;
        this.clientCart =clientCart;
    }

    public  void generateInvoice(String path) {
        String filePath = "src/main/java/ressources/output/" +path;
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);


            document.add(new Paragraph("Facture").setFontSize(20).setBold().setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(""));

            // Add billing address on the left side
            Paragraph billingAddress = new Paragraph("Adresse de facturation\n" +
                    "Client: " + this.loggedInClient.getFirstName() + " " + this.loggedInClient.getLastName() + "\n" +
                    "Email: " + this.loggedInClient.getEmail() + "\n" +
                    "Numéro client: " + this.loggedInClient.getId());
            billingAddress.setTextAlignment(TextAlignment.RIGHT);
            document.add(billingAddress);

            document.add(new Paragraph("Invoice Details:").setFontSize(10).setBold());
            document.add(new Paragraph("Bonjour "+ this.loggedInClient.getFirstName()));
            document.add(new Paragraph("Nous vous remercions pour votre achat chez Privé by Zalando. Voici un\r\n"
            		+ "récapitulatif de votre commande :"));
            document.add(new Paragraph("Price per Item: $5.00"));
            document.add(new Paragraph("Total: $50.00"));

            Table table = new Table(5);

            table.addCell(new Cell().add(new Paragraph("Unité"))); // Quantity
            table.addCell(new Cell().add(new Paragraph("Référence"))); // Item ID
            table.addCell(new Cell().add(new Paragraph("Article"))); // Item name
            table.addCell(new Cell().add(new Paragraph("Prix Unitaire eur"))); // Unit price
            table.addCell(new Cell().add(new Paragraph("Prix Total eur"))); // Total price per item

            // Adding data for each item in the order
            for (Map.Entry<Bijoux, Integer> entry : clientCart.getCart().entrySet()) {
                Bijoux item = entry.getKey();
                int quantity = entry.getValue();

                table.addCell(new Cell().add(new Paragraph(String.valueOf(quantity))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getId()))));
                table.addCell(new Cell().add(new Paragraph(item.getName() + " of brand " + item.getBrand())));
                table.addCell(new Cell().add(new Paragraph( String.format("%.2f", item.getPrice()))));
                table.addCell(new Cell().add(new Paragraph(  String.format("%.2f",(item.getPrice() * quantity)))));
            }
            document.add(table);
            document.close();
            System.out.println("PDF generated successfully at: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   /* public static  void main(String[] args) {
    	Client c = new Client();
    	InvoiceGenerator ig = new InvoiceGenerator(new Order(c),c) ;
    	//ig.generateInvoice();
    		
    	
    }
    */
}