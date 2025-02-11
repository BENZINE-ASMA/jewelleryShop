package model;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class Order {
	private Long orderId;
	private Cart clientCart;
	private OrderStatus status;



	private Timestamp orderDate;
	private Client client;
	
	 public Order(Client client) { 
	        this.clientCart = new Cart();
	        this.setClient(client);
	        this.setStatus(OrderStatus.EN_COURS);
		 	this.orderDate = new Timestamp(System.currentTimeMillis());
	    }
	public Order(Long orderId,Long clientId,Cart clientCart, Timestamp orderDate, OrderStatus status) {
		this.clientCart = clientCart;
		this.client= new Client();
		this.client.setId(clientId);
		this.orderId=orderId;
		this.status= status;
		this.orderDate=orderDate;
	}
	 public void validateOrder() {
	        this.setStatus(OrderStatus.VALIDEE);
	 }
	 public void orderDelivered() {
	        this.setStatus(OrderStatus.LIVREE);
	    }
	public Cart getCartItems() {
		return clientCart;
	}
	public void setCartItems(Cart cartItems) {
		this.clientCart = cartItems;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Timestamp getOrderDate() {
		return this.orderDate;
	}

}
