package model;

public class Ring extends Bijoux {
	 private int size;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Ring(Long id,String name, String brand,String type, String description, double price, String materiel,int size,String imagePath,int stock) {
		super(id,name, brand,type, description, price, materiel,imagePath, stock);
		this.size = size;		
	}
	
	public String toString() {
		String ringDesc = super.toString();
		ringDesc = ringDesc +" size: " + size;
		return ringDesc;
	}

}
