package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Cart {
	public HashMap<Bijoux, Integer> getCart() {
		return cart;
	}

	public void setCart(HashMap<Bijoux, Integer> cart) {
		this.cart = cart;
	}

	private HashMap<Bijoux,Integer> cart;
	
	public Cart() {
		this.cart = new HashMap<>();
	}
	
	public int  addToCart(Bijoux b) {
		int stockmax = b.getStock();
		int currentQuantity = cart.getOrDefault(b, 0);
		while((currentQuantity+1)<=stockmax ) {
			currentQuantity++;
			this.cart.put(b, currentQuantity);
			return currentQuantity;			
		}
		return currentQuantity;
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
	
	public String toString() {
		StringBuilder res = new StringBuilder();
		for (Bijoux b : this.cart.keySet()) {
			res.append(b.toString() +" quantité: " + this.cart.get(b) + "\n");
		}
		return res.toString();
	}
	

}
