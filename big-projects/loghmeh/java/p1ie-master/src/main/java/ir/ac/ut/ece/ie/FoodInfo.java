package ir.ac.ut.ece.ie;

public class FoodInfo {

	private String name;
	private String description;
	//private String restaurant;
	private double popularity;
	private double price;

	public FoodInfo () {
		this.name = "";
		this.description = "";
		this.popularity = 0;
		this.price = 0;
	}

	public FoodInfo (String name, String description, double popularity, double price) {
		this.name = name;
		this.description = description;
		//this.restaurant = restaurant;
		this.popularity = popularity;
		this.price = price;
	}

	public String getName() {
		return name;
	}
	public void setName (String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public double getPopularity() {
		return popularity;
	}
	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	/*public String getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}*/


}