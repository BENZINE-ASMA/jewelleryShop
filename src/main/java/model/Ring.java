package model;

public class Ring extends Bijoux {
	 private int size;

	public Ring(String name, String brand, String description, double price, String materiel,int size,String imagePath,int stock) {
		super(name, brand, description, price, materiel,imagePath, stock);
		this.size = size;		
	}
	
	public String toString() {
		String ringDesc = super.toString();
		ringDesc = ringDesc +" size: " + size;
		return ringDesc;
	}

}
