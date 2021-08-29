package ir.ac.ut.ece.ie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import static org.junit.Assert.*;

public class OrderManagerTest {

	OrderManager orderManager;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		orderManager = new OrderManager("src/main/resources/restaurants.json");
		String command="addRestaurant {\"name\": \"albek\", \"description\": \"luxury\", \"location\": {\"x\": 0, \"y\": 1}, \"menu\": [{\"name\": \"pasta\", \"description\": \"it’s so yummy!\", \"popularity\": 0.9, \"price\": 300000}, {\"name\": \"omlet\", \"description\": \"it’s bad taste!\", \"popularity\": 0.5, \"price\":3100}]}";
		orderManager.separate(command);
		orderManager.runCommand();

		command="addRestaurant {\"name\": \"shideh\", \"description\": \"good\", \"location\": {\"x\": 5, \"y\": 3}, \"menu\": [{\"name\": \"rice\", \"description\": \"it’s mediocre!\", \"popularity\": 0.9, \"price\": 300000}, {\"name\": \"alfredo\", \"description\": \"it’s good taste!\", \"popularity\": 0.8, \"price\":31000}]}";
		orderManager.separate(command);
		orderManager.runCommand();

		command="addRestaurant {\"name\": \"amado\", \"description\": \"luxury\", \"location\": {\"x\": 4, \"y\": 3}, \"menu\": [{\"name\": \"chicken popcorn\", \"description\": \"it’s so good!\", \"popularity\": 0.9, \"price\": 301000}, {\"name\": \"zink berger\", \"description\": \"it’s good taste!\", \"popularity\": 0.8, \"price\":30000}]}";
		orderManager.separate(command);
		orderManager.runCommand();

		command="addFood {\"name\": \"rolet\", \"description\": \"it’s bad!\", \"popularity\": 0.5, \"restaurantName\": \"Hesturan\", \"price\": 20200}";
		orderManager.separate(command);
		orderManager.runCommand();

		command="addFood {\"name\": \"pizza\", \"description\": \"it’s delicious!\", \"popularity\": 0.95, \"restaurantName\": \"albek\", \"price\": 30200}";
		orderManager.separate(command);
		orderManager.runCommand();

		command="addToCart {\"foodName\": \"Kabab\", \"restaurantName\": \"Hesturan\"}";
		orderManager.separate(command);
		orderManager.runCommand();

		/*command="addToCart {\"foodName\": \"chicken popcorn\", \"restaurantName\": \"amado\"}";
		orderManager.separate(command);
		orderManager.runCommand();*/

		command="addToCart {\"foodName\": \"rolet\", \"restaurantName\": \"Hesturan\"}";
		orderManager.separate(command);
		orderManager.runCommand();

		command="addToCart {\"foodName\": \"rolet\", \"restaurantName\": \"Hesturan\"}";
		orderManager.separate(command);
		orderManager.runCommand();

		command="getRecommendedRestaurants";
		orderManager.separate(command);
		orderManager.runCommand();



	}

	@Test
	public void testFinalizeOrder(){
		String command="finalizeOrder";
		orderManager.separate(command);
		orderManager.runCommand();
		//------
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		PrintStream old = System.out;

		System.setOut(ps);

		command="getCart";
		orderManager.separate(command);
		orderManager.runCommand();

		System.out.flush();
		System.setOut(old);

		assertTrue(baos.toString().contains("empty order!"));
		//


	}

	@Test
	public void testGetRecommendedRestaurants() {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		PrintStream old = System.out;

		System.setOut(ps);

		String command="getRecommendedRestaurants";
		orderManager.separate(command);
		orderManager.runCommand();

		System.out.flush();
		System.setOut(old);

		assertFalse(baos.toString().contains("shideh"));
		assertTrue(baos.toString().contains("amado"));
		assertTrue(baos.toString().contains("Hesturan"));
		assertTrue(baos.toString().contains("albek"));


	}

}
