import config
import random
import json


def select_random(array):
    return array[random.randint(0, len(array) - 1)]


class Location:
    def __init__(self, x=0, y=0):
        self.x = x
        self.y = y


class Restaurant:
    def __init__(self, name="", description="", location=Location(0, 0), menu=None):
        self.name = name
        self.description = description
        self.location = location
        self.menu = [] if menu is None else menu


class Food:
    def __init__(self, name="", description="", popularity=0.0, price=0):
        self.name = name
        self.description = description
        self.popularity = popularity
        self.price = price


class AddFood:
    def __init__(self, name="", restaurantName="", description="", popularity=0.0, price=0):
        self.name = name
        self.restaurantName = restaurantName
        self.description = description
        self.popularity = popularity
        self.price = price


class FoodSummary:
    def __init__(self, foodName="", restaurantName=""):
        self.foodName = foodName
        self.restaurantName = restaurantName


class Generator:
    restaurants = []
    foods = []

    @staticmethod
    def get_restaurants_with_menu():
        return list(filter(lambda r: r.menu != [], Generator.restaurants))

    @staticmethod
    def generate_new_restaurant(menu_size):
        name = config.RESTAURANT_PREFIX_NAMES + str(len(Generator.restaurants))
        description = select_random(config.RESTAURANT_DESCRIPTIONS)
        location = Location(random.randint(-10, 10), random.randint(-10, 10))
        menu = [Generator.generate_new_food_for_restaurant() for _ in range(menu_size)]
        restaurant = Restaurant(name=name, description=description, location=location, menu=menu)
        Generator.restaurants.append(restaurant)
        return restaurant

    @staticmethod
    def generate_new_food_for_restaurant():
        name = config.FOOD_PREFIX_NAMES + str(len(Generator.foods))
        description = select_random(config.FOOD_DESCRIPTIONS)
        popularity = int(100 * random.random()) / 100
        price = 1000 * (random.randint(0, 100)) + 10000
        food = Food(name=name, description=description, popularity=popularity, price=price)
        Generator.foods.append(food)
        return food

    @staticmethod
    def generate_new_add_food():
        restaurant = select_random(Generator.restaurants)
        food = Generator.generate_new_food_for_restaurant()
        return AddFood(name=food.name, restaurantName=restaurant.name, description=food.description, popularity=food.popularity, price=food.price)

    @staticmethod
    def generate_repetitive_add_food():
        restaurant = select_random(Generator.get_restaurants_with_menu())
        food = select_random(restaurant.menu)
        return AddFood(name=food.name, restaurantName=restaurant.name, description=food.description, popularity=food.popularity, price=food.price)

    @staticmethod
    def generate_get_food():
        restaurant = select_random(Generator.get_restaurants_with_menu())
        food = select_random(restaurant.menu)
        return FoodSummary(foodName=food.name, restaurantName=restaurant.name)

    @staticmethod
    def generate_cart(order_numbers):
        cart = []
        restaurant = select_random(Generator.get_restaurants_with_menu())
        for i in range(order_numbers):
            food = select_random(restaurant.menu)
            cart.append(FoodSummary(foodName=food.name, restaurantName=restaurant.name))
        return cart


class TestEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Location):
            return obj.__dict__
        elif isinstance(obj, Food):
            return obj.__dict__
        elif isinstance(obj, Restaurant):
            return obj.__dict__
        elif isinstance(obj, AddFood):
            return obj.__dict__
        elif isinstance(obj, FoodSummary):
            return obj.__dict__
        return super().default(obj)


def dumps(obj):
    return json.dumps(obj, cls=TestEncoder)


def build_command(command_config, args):
    return command_config['name'] + ('' if args is None else ' ' + args)


def generate_add_restaurants_commands(config):
    commands = []
    for i in range(config.ADD_REST['number_of_restaurants']):
        menu_size = 0 if (i+1) < config.ADD_REST['number_of_restaurants_with_empty_menu'] else random.randint(1, 4)
        restaurant = Generator.generate_new_restaurant(menu_size)
        commands.append(build_command(config.ADD_REST, dumps(restaurant)))
    return 2 * commands


def generate_add_food_commands(config):
    commands = []
    for i in range(config.ADD_FOOD['number_of_new_foods']):
        food = Generator.generate_new_add_food()
        commands.append(build_command(config.ADD_FOOD, dumps(food)))
    for i in range(config.ADD_FOOD['number_of_repetitive_foods']):
        food = Generator.generate_repetitive_add_food()
        commands.append(build_command(config.ADD_FOOD, dumps(food)))
    for i in range(config.ADD_FOOD['number_of_bad_foods']):
        food = Generator.generate_repetitive_add_food()
        food.restaurantName = food.restaurantName.replace('R', 'T')
        commands.append(build_command(config.ADD_FOOD, dumps(food)))
    return commands


def generate_get_restaurants_command(config):
    return [build_command(config.GET_RESTAURANTS, None)]


def generate_get_restaurant_command(command_config):
    commands = []
    for i in range(command_config.GET_RESTAURANT['number_of_existence_ones']):
        restaurant = select_random(Generator.restaurants)
        name = '{' + '"name": "{}"'.format(restaurant.name) + '}'
        commands.append(build_command(command_config.GET_RESTAURANT, name))
    for i in range(command_config.GET_RESTAURANT['number_of_not_existence_ones']):
        name = '{' + '"name": "{}"'.format(config.NOT_EXISTENCE_RESTAURANT_NAME) + '}'
        commands.append(build_command(command_config.GET_RESTAURANT, name))
    return commands


def generate_get_food_commands(command_config):
    commands = []
    for i in range(command_config.GET_FOOD['number_of_commands']):
        food_data = Generator.generate_get_food()
        commands.append(build_command(command_config.GET_FOOD, dumps(food_data)))
    food_data = Generator.generate_get_food()
    food_data.foodName = config.NOT_EXISTENCE_FOOD_NAME
    commands.append(build_command(command_config.GET_FOOD, dumps(food_data)))
    food_data = Generator.generate_get_food()
    food_data.restaurantName = config.NOT_EXISTENCE_RESTAURANT_NAME
    commands.append(build_command(command_config.GET_FOOD, dumps(food_data)))
    food_data = FoodSummary(config.NOT_EXISTENCE_FOOD_NAME, config.NOT_EXISTENCE_RESTAURANT_NAME)
    commands.append(build_command(command_config.GET_FOOD, dumps(food_data)))
    return commands


def generate_add_to_cart_commands(command_config):
    cart = Generator.generate_cart(command_config.ADD_TO_CART['number_of_orders'])
    commands = [build_command(command_config.ADD_TO_CART, dumps(order)) for order in cart]
    last_order = cart[-1]
    another_restaurant = select_random(list(filter(lambda r: r.name != last_order.restaurantName, Generator.get_restaurants_with_menu())))
    bad_order = FoodSummary(foodName=select_random(another_restaurant.menu).name, restaurantName=another_restaurant.name)
    commands.append(build_command(command_config.ADD_TO_CART, dumps(bad_order)))
    bad_order = FoodSummary(foodName=config.NOT_EXISTENCE_FOOD_NAME, restaurantName=bad_order.restaurantName)
    commands = [build_command(command_config.ADD_TO_CART, dumps(bad_order))] + commands
    commands += [build_command(command_config.ADD_TO_CART, dumps(bad_order))]
    bad_order = FoodSummary(foodName=config.NOT_EXISTENCE_FOOD_NAME, restaurantName=config.NOT_EXISTENCE_RESTAURANT_NAME)
    commands = [build_command(command_config.ADD_TO_CART, dumps(bad_order))] + commands
    commands += [build_command(command_config.ADD_TO_CART, dumps(bad_order))]
    commands += ["bad_command!!"]
    return commands


def generate_get_cart(command_config):
    return [build_command(command_config.GET_CART, None)]


def generate_finalize_order(command_config):
    return [build_command(command_config.FINALIZE_ORDER, None)]


def generate_get_recommended_restaurants(command_config):
    return [build_command(command_config.GET_RECOMMENDED_RESTS, None)]


def generate_commands():
    commands = generate_get_recommended_restaurants(config.CommandsConfig)
    commands += generate_add_restaurants_commands(config.CommandsConfig)
    commands += generate_get_recommended_restaurants(config.CommandsConfig)
    commands += generate_add_food_commands(config.CommandsConfig)
    commands += generate_get_recommended_restaurants(config.CommandsConfig)
    commands += generate_get_restaurants_command(config.CommandsConfig)
    commands += generate_get_restaurant_command(config.CommandsConfig)
    commands += generate_get_food_commands(config.CommandsConfig)
    commands += generate_get_cart(config.CommandsConfig)
    commands += generate_finalize_order(config.CommandsConfig)
    commands += generate_add_to_cart_commands(config.CommandsConfig)
    commands += generate_get_cart(config.CommandsConfig)
    commands += generate_finalize_order(config.CommandsConfig)
    commands += generate_add_to_cart_commands(config.CommandsConfig)
    commands += generate_finalize_order(config.CommandsConfig)
    commands += generate_add_to_cart_commands(config.CommandsConfig)
    commands += generate_finalize_order(config.CommandsConfig)
    commands += generate_add_to_cart_commands(config.CommandsConfig)
    commands += generate_get_cart(config.CommandsConfig)
    commands += generate_finalize_order(config.CommandsConfig)
    commands += generate_add_to_cart_commands(config.CommandsConfig)
    commands += generate_finalize_order(config.CommandsConfig)
    commands += generate_get_cart(config.CommandsConfig)
    commands += generate_get_recommended_restaurants(config.CommandsConfig)
    return commands


def main():
    for command in generate_commands():
        print(command)


main()
