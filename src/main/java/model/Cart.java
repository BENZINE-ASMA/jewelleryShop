package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Cart {
	private HashMap<Bijoux,Integer> cart;
	
	public Cart() {
		this.cart = new HashMap<>();
	}
	
	public void addToCart(Bijoux b) {
		int currentQuantity = cart.getOrDefault(b, 0);
		this.cart.put(b, currentQuantity++);
	}
	
	public void removeFromCart(Bijoux b) {
		if(cart.containsKey(b)) {
			int currentQuantity = cart.getOrDefault(b,0);
			if(currentQuantity >1) {
				cart.put(b,currentQuantity-1);
			}else {
				cart.remove(b);
			}
		}

	}
	public void clearCart() {
        cart.clear();
    }
	
	public double getTotalPrice() {
		double total = 0.0;
		for (Map.Entry<Bijoux,Integer> entry: cart.entrySet()) {
			Bijoux bijoux = entry.getKey();
			Integer quantity = entry.getValue();
			total += bijoux.getPrice()*quantity;
		}
		return total;
	}
	

}
