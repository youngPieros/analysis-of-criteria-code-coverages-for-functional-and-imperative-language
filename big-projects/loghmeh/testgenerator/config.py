class CommandsConfig:
    ADD_REST = {'name': 'addRestaurant', 'number_of_restaurants': 30, 'number_of_restaurants_with_empty_menu': 10}
    ADD_FOOD = {'name': 'addFood', 'number_of_new_foods': 1, 'number_of_repetitive_foods': 1, 'number_of_bad_foods': 1}
    GET_RESTAURANTS = {'name': 'getRestaurants'}
    GET_RESTAURANT = {'name': 'getRestaurant', 'number_of_existence_ones': 10, 'number_of_not_existence_ones': 1}
    GET_FOOD = {'name': 'getFood', 'number_of_commands': 10}
    ADD_TO_CART = {'name': 'addToCart', 'number_of_orders': 10}
    GET_CART = {'name': 'getCart'}
    FINALIZE_ORDER = {'name': 'finalizeOrder'}
    GET_RECOMMENDED_RESTS = {'name': 'getRecommendedRestaurants'}


RESTAURANT_PREFIX_NAMES = 'R'
FOOD_PREFIX_NAMES = 'F'
RESTAURANT_DESCRIPTIONS = ['luxury', 'classic', 'italy', 'coffee shop']
FOOD_DESCRIPTIONS = ['so tasty', 'it’s so delicious', 'it’s so yummy!']
NOT_EXISTENCE_RESTAURANT_NAME = 'NULL_RESTAURANT'
NOT_EXISTENCE_FOOD_NAME = 'NULL_FOOD'
