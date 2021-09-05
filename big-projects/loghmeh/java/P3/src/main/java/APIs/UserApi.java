package APIs;

import models.*;
import repo.RestaurantRepo;
import utils.JsonMapper;

import java.util.*;
import java.util.stream.Collectors;

public class UserApi {
    private Card userShoppingList;
    private RestaurantRepo restaurantRepo;
    private Coordinate userLoc;

    public UserApi() {
        userShoppingList = new Card();
        restaurantRepo = RestaurantRepo.getInstance();
        userLoc = new Coordinate();
        userLoc.setX(0);
        userLoc.setY(0);
    }

    public String addToCart(String jsonString) {
        JsonMapper<Map> mapJsonMapper = new JsonMapper<>(Map.class);
        Map<String, String> foodInfo = mapJsonMapper.readJson(jsonString);
        if (restaurantRepo.searchFood(foodInfo.get("restaurantName"), foodInfo.get("foodName")) == null) {
            return "Food Not Found!";
        }
        if (userShoppingList.addItem(foodInfo.get("restaurantName"), foodInfo.get("foodName"))) {
            return "Food Added to List.";
        } else {
            return "You have food(s) from other restaurant!";
        }
    }

    public String getCard(){
        JsonMapper<Card> cardJsonMapper = new JsonMapper<>(Card.class);
        return cardJsonMapper.toJson(userShoppingList);
    }

    public String finalizeOrder(){
        JsonMapper<Card> cardJsonMapper = new JsonMapper<>(Card.class);
        String response = cardJsonMapper.toJson(userShoppingList);
        response += "\n Your shopping list have been finalized!";
        userShoppingList = new Card();
        return response;
    }

    private double findRestaurantFoodPopularity(Restaurant restaurant){
        double sum = 0;
        int count = 0;
        List<Food> restaurantFoodList = restaurant.getMenu();
        for(Food f : restaurantFoodList){
            sum += f.getPopularity();
            count += 1;
        }
        return sum/count;
    }

    private ArrayList<String> findMostThreePopularRestaurants(){
        ArrayList<Restaurant> restaurants = restaurantRepo.getRestaurantList();
        Map<String,Double> restaurantsPopularity = new HashMap<>();
        for(Restaurant r : restaurants){
            double restaurantFoodPopularity = findRestaurantFoodPopularity(r);
            double distanceFromUser = r.getLocation().findDistance(this.userLoc);
            double popularity = restaurantFoodPopularity/distanceFromUser;
            restaurantsPopularity.put(r.getName(), popularity);
        }
        Map<String, Double> mostThreePopularRestaurantsMap = restaurantsPopularity.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Double>comparingByValue().reversed()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        ArrayList<String> mostThreePopularRestaurants = new ArrayList<>(mostThreePopularRestaurantsMap.keySet());
        return mostThreePopularRestaurants;
    }

    public String getMostThreePopularRestaurants(){
        ArrayList mostThreePopularRestaurants = this.findMostThreePopularRestaurants();
        JsonMapper <ArrayList> arrayListJsonMapper = new JsonMapper<>(ArrayList.class);
        return arrayListJsonMapper.toJson(mostThreePopularRestaurants);
    }
}
