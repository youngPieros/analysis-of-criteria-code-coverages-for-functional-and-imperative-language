package ie;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY , isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Restaurant {
    private String name;
    private String description;
    private  HashMap <String,Double> location;
    private List< Food > menu;


    public String getName(){
        return name;
    }

    public void setName(String _name){
        this.name = _name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String _description){
        this.description = _description;
    }

    public HashMap <String,Double> getLocation(){
        return location;
    }

    public void setLocation(HashMap <String,Double> _location){
        this.location = _location;
    }

    public List< Food > getMenu(){
        return menu;
    }

    public void setMenu(List< Food > _menu){
        this.menu = _menu;
    }

    public void addFood( Food _food) throws IOException {
        boolean repetitiveFood = false;
        for(int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(_food.getName())){
                repetitiveFood = true;
            }
        }
        if(!repetitiveFood)
            menu.add(_food);
        else
            System.out.println("Food already exists in menu!");
    }

    public Food findFood(String food){
        for(int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(food))
                return menu.get(i);
        }
        return null;
    }

    public Double FoodPopularityAverage(){
        Double sum = 0.0;
        for(int i = 0; i < menu.size(); i++){
            sum += menu.get(i).getPopularity();
        }
        return (Double) (sum / menu.size());
    }

    public Double Distance(){
        return Math.sqrt(Math.pow(location.get("x"),2.0) + Math.pow(location.get("y"),2.0));
    }

}
