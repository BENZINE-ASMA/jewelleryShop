package model;

import java.sql.Timestamp;

public class Invoice {
    private Long invoiceId;         
    private Long clientId;
    private Long orderId;
    private String invoiceNumber;  
    private String filePath;
    private Timestamp invoiceDate;
    private Timestamp updateDate;
    private double totalAmount;    
    private String status;         


    
    public Invoice(Long clientId,Long orderId ,String invoiceNumber, String filePath, double totalAmount, String status) {
        this.clientId = clientId;
        this.orderId = orderId;
        this.invoiceNumber = invoiceNumber;
        this.filePath = filePath;
        this.totalAmount = totalAmount;
        this.status = status;
        this.invoiceDate = new Timestamp(System.currentTimeMillis());
        this.updateDate = new Timestamp(System.currentTimeMillis());

    }
    public Invoice(Long invoiceId, Long clientId, Long orderId,String invoiceNumber, String filePath, double totalAmount, String status,Timestamp invoiceDate,Timestamp updateDate) {
        this.invoiceId = invoiceId;
        this.clientId = clientId;
        this.orderId = orderId;
        this.invoiceNumber = invoiceNumber;
        this.filePath = filePath;
        this.totalAmount = totalAmount;
        this.status = status;
        this.invoiceDate =invoiceDate;
        this.updateDate = updateDate;

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
        return updateDate;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updateDate = updatedAt;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public Timestamp getUpdateDate() {
        return this.updateDate;
    }
    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", clientId=" + clientId +
                ", orderId=" + orderId +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", filePath='" + filePath + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }
}
