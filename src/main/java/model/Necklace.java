package model;

public class Necklace extends Bijoux {
	private double length;
	
	public Necklace(String name, String brand, String description, double price, String materiel, double l,String imagePath) {
		super(name, brand, description, price, materiel, imagePath);
		this.length=l;
	}
	
	public String toString() {
		String necklaceDesc = super.toString();
		necklaceDesc = necklaceDesc +" length: " + length;
		return necklaceDesc;
	}


	
}
