package model;


import java.util.HashMap;
import java.util.List;

public class Order {
	private Long orderId;
	private Cart clientCart;
	private OrderStatus status;
	private Client client;
	
	 public Order(Client client) { 
	        this.clientCart = new Cart();
	        this.setClient(client);
	        this.setStatus(OrderStatus.EN_COURS);
	       
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
	

}
