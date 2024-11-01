package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Order {
	private Cart clientCart;
	private OrderStatus status;
	private LocalDate currentDate;
	private Client client;
	
	 public Order(Client client) {
	        this.clientCart = new Cart();
	        this.setClient(client);
	        this.setStatus(OrderStatus.EN_COURS);
	        this.currentDate = LocalDate.now();
	       
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
	

}
