package ie;

public class Food {
    private String name;
    private String description;
    private Double popularity;
    private int price;

    public void setName(String _name){
        this.name = _name;
    }

    public String getName(){
        return name;
    }

    public void setDescription(String _description){
        this.description = _description;
    }

    public String getDescription(){
        return description;
    }

    public void setPopularity(Double _popularity){
        this.popularity = _popularity;
    }

    public Double getPopularity(){
        return popularity;
    }


    public void setPrice(int _price){
        this.price = _price;
    }

    public int getPrice(){
        return price;
    }
}
