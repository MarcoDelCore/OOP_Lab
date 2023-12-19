package diet;

import java.util.*;


/**
 * Represents a takeaway restaurant chain.
 * It allows managing restaurants, customers, and orders.
 */
public class Takeaway {
	Food food;
	Map<String, Restaurant> restaurants;
	List<Customer> customers;
	List<Order> orders;

	/**
	 * Constructor
	 * @param food the reference {@link Food} object with materials and products info.
	 */
	public Takeaway(Food food){
		this.food = food;
		restaurants = new TreeMap<>();
		customers = new ArrayList<>();
		orders = new ArrayList<>();
	}

	/**
	 * Creates a new restaurant with a given name
	 *
	 * @param restaurantName name of the restaurant
	 * @return the new restaurant
	 */
	public Restaurant addRestaurant(String restaurantName) {
		Restaurant r = new Restaurant(restaurantName);
		restaurants.put(restaurantName, r);
		return r;
	}

	/**
	 * Retrieves the names of all restaurants
	 *
	 * @return collection of restaurant names
	 */
	public Collection<String> restaurants() {
		return restaurants.keySet();
	}

	/**
	 * Creates a new customer for the takeaway
	 * @param firstName first name of the customer
	 * @param lastName	last name of the customer
	 * @param email		email of the customer
	 * @param phoneNumber mobile phone number
	 *
	 * @return the object representing the newly created customer
	 */
	public Customer registerCustomer(String firstName, String lastName, String email, String phoneNumber) {
		Customer tmp = new Customer(firstName, lastName, email, phoneNumber);
		customers.add(tmp);
		customers.sort((c1,c2) -> {
			int d = c1.getLastName().compareTo(c2.getLastName());
			if (d != 0) return d;
			return c1.getFirstName().compareTo(c2.getFirstName());
		});
		return tmp;
	}

	/**
	 * Retrieves all registered customers
	 *
	 * @return sorted collection of customers
	 */
	public Collection<Customer> customers(){
		return customers;
	}


	/**
	 * Creates a new order for the chain.
	 *
	 * @param customer		 customer issuing the order
	 * @param restaurantName name of the restaurant that will take the order
	 * @param time	time of desired delivery
	 * @return order object
	 */
	public Order createOrder(Customer customer, String restaurantName, String time) {
		Restaurant tmp = restaurants.get(restaurantName);
		String deliveryTime = new String();
		Integer h=Integer.parseInt(time.split(":")[0]);
		int m = Integer.parseInt(time.split(":")[1]);
//		if ((time.compareTo(tmp.morningOpen)>=0 && time.compareTo(tmp.morningClose)<=0) 
//				|| (time.compareTo(tmp.afternoonOpen)>=0 && time.compareTo(tmp.afternoonClose)<=0)) {
//			deliveryTime = time;
		if (tmp.isOpenAt(time)){
			deliveryTime = String.format("%02d:%02d", h, m);
		} else if (h>=tmp.hmClose && h<=tmp.haOpen) {
			deliveryTime = String.format("%02d:%02d", tmp.haOpen, tmp.maOpen);
		} else {
			deliveryTime = String.format("%02d:%02d", tmp.hmOpen, tmp.mmOpen);
		}
		Order o = new Order(customer, tmp, deliveryTime);
		orders.add(o);
		restaurants.get(restaurantName).orders.add(o);
		return o;
	}

	/**
	 * Find all restaurants that are open at a given time.
	 *
	 * @param time the time with format {@code "HH:MM"}
	 * @return the sorted collection of restaurants
	 */
	public Collection<Restaurant> openRestaurants(String time){
		Collection<Restaurant> res = new ArrayList<>();
		for (Restaurant r : this.restaurants.values()) {
			if (r.isOpenAt(time)) res.add(r);
		}
		return res;
	}
	
	public void print() {
		for (Customer c : customers) {
			System.out.println(c.toStr());
		}
	}
}
