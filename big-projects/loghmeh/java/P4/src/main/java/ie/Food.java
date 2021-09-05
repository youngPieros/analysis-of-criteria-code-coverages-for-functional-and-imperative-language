package ie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class Food {
    @JsonView(Views.FoodWithoutRestaurantName.class)
    @JsonProperty(required = true)
    private String name;
    @JsonView(Views.FoodWithoutRestaurantName.class)
    @JsonProperty(required = true)
    private String description;
    @JsonView(Views.FoodWithoutRestaurantName.class)
    @JsonProperty(required = true)
    private float popularity;
    @JsonView(Views.FoodWithoutRestaurantName.class)
    @JsonProperty(required = true)
    private long price;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String restaurantName;

    public float getPopularity() {
        return popularity;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
