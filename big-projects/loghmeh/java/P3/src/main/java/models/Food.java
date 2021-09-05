package models;

public class Food {
    private String name;
    private String description;
    private float popularity;
    private int price;

    public Food(){}
    public Food(String name, String description, float popularity, int price){
        this.name = name;
        this.description = description;
        this.popularity = popularity;
        this.price = price;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public float getPopularity(){
        return popularity;
    }
    public int getPrice(){
        return price;
    }
}
