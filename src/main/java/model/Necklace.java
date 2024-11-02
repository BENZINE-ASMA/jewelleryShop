package model;

public class Necklace extends Bijoux {
	private double length;
	
	public Necklace(Long id,String name, String brand, String description, double price, String materiel, double l,String imagePath,int stock) {
		super(id,name, brand, description, price, materiel, imagePath,stock);
		this.length=l;
	}
	
	public String toString() {
		String necklaceDesc = super.toString();
		necklaceDesc = necklaceDesc +" length: " + length;
		return necklaceDesc;
	}


	
}
