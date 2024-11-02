package model;

import java.sql.Timestamp;

public class Invoice {
    private Long invoiceId;         
    private Long clientId;          
    private String invoiceNumber;  
    private String filePath;       
    private Timestamp invoiceDate; 
    private double totalAmount;    
    private String status;         
    private Timestamp updatedAt;   

    
    public Invoice(Long clientId, String invoiceNumber, String filePath, double totalAmount, String status) {
        this.clientId = clientId;
        this.invoiceNumber = invoiceNumber;
        this.filePath = filePath;
        this.totalAmount = totalAmount;
        this.status = status;
        this.invoiceDate = new Timestamp(System.currentTimeMillis()); // Sets to current time
    }

    
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

   
    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", clientId=" + clientId +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", filePath='" + filePath + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
