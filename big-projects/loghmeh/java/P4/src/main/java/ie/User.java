package ie;

public class User {
    private Cart cart;

    public User(){
        cart = new Cart();
    }

    public Interface.Status addToCart(Resturant resturant, Food order){
        return cart.addOrder(resturant,order);
    }

    public Interface.Status printCart(){
        return cart.printCart();
    }

    public Interface.Status finalizeOrder(){
        return cart.finalizeOrder();
    }
}
