package ie;

public class Order {
    private Food food;
    private int numOfFoods;

    public Order(Food food_, int numOfFoods_){
        food = food_;
        numOfFoods = numOfFoods_;
    }

    public String getFoodName() {
        return food.getName();
    }

    public int getNumOfFoods() {
        return numOfFoods;
    }

    public void setNumOfFoods(int numOfFoods) {
        this.numOfFoods = numOfFoods;
    }

    public void addNumOfFoods(int numOfFoods){
        this.numOfFoods += numOfFoods;
    }
}
