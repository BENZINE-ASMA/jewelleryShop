package model;

public class Bijoux {
	protected Long id;
	protected String name;
	protected String brand;
	protected String category;
	protected String description;
	protected double price;
	protected String materiel;
	protected String imagePath;
	protected int stock;

	public Bijoux(Long id, String name, String brand, String category, String description, double price, String materiel, String imagePath, int stock) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.description = description;
		this.price = price;
		this.materiel = materiel;
		this.imagePath = imagePath;
		this.stock = stock;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getBrand() { return brand; }
	public void setBrand(String brand) { this.brand = brand; }
	public String getCategory() { return category; }

	public void setCategory(String category) { this.category = category; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }
	public String getMateriel() { return materiel; }
	public void setMateriel(String materiel) { this.materiel = materiel; }
	public String getImagePath() { return imagePath; }
	public void setImagePath(String imagePath) { this.imagePath = imagePath; }
	public int getStock() { return stock; }
	public void setStock(int stock) { this.stock = stock; }

	public String formattedPrice() {
		return String.format("%.2f €", price);
	}
	public void decrementStock(int quantity) {
		if (stock >= quantity) {
			stock -= quantity;
		} else {
			stock = 0;
		}
	}

	@Override
	public String toString() {
		return "Name: " + name + " | Brand: " + brand + " | Price: " + formattedPrice() +
				" | Category: " + category + " | Material: " + materiel + " | Stock: " + stock + " | ImagePath: " + imagePath;
	}
}
