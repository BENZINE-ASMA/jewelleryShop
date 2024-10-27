package model;

import java.util.Date;
import java.util.List;

public class Order {
	private List<Bijoux> cartItems;
	private OrderStatus status;
	private Date orderDate;
	private Client client;
	
	 public Order(Long orderId, List<Bijoux> items, Client client) {
	        this.setCartItems(items);
	        this.setClient(client);
	        this.setStatus(OrderStatus.EN_COURS);
	        this.setOrderDate(new Date());
	    }
	 public void validateOrder() {
	        this.setStatus(OrderStatus.VALIDEE);
	 }
	 public void orderDelivered() {
	        this.setStatus(OrderStatus.LIVREE);
	    }
	public List<Bijoux> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<Bijoux> cartItems) {
		this.cartItems = cartItems;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	

}
