package model;

public class Bijoux {

	protected String name;
	protected String brand;
	protected String description;
	protected double price;
	protected String materiel;
	protected String imagePath;
	public Bijoux(String name, String brand, String description, double price, String materiel,String imagePath) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.materiel = materiel;
        this.imagePath = imagePath;
    }
	
	public String toString() {
		return "Name: " + name +"brand: " + brand +"description: " + description + "price: " + price +"maetrial: "+materiel;
	}
}
