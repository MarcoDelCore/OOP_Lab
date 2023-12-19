package diet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant class with given opening times and a set of menus.
 */
public class Restaurant {
	String name;
	int hmOpen, mmOpen, hmClose, mmClose, haOpen, maOpen, haClose, maClose;
	String mOpen, mClose, aOpen, aClose;
	Map<String, Menu> menu;
	List<Order> orders;
	
	public Restaurant(String name) {
		this.name = name;
		this.menu = new TreeMap<>();
		orders = new ArrayList<>();
	}
	/**
	 * retrieves the name of the restaurant.
	 *
	 * @return name of the restaurant
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define opening times.
	 * Accepts an array of strings (even number of elements) in the format {@code "HH:MM"},
	 * so that the closing hours follow the opening hours
	 * (e.g., for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00,
	 * arguments would be {@code "08:15", "14:00", "19:00", "00:00"}).
	 *
	 * @param hm sequence of opening and closing times
	 */
	public void setHours(String ... hm) {
		if (hm.length == 4) {
			mOpen = hm[0];
			mClose = hm[1];
			aOpen = hm[2];
			aClose = hm[3];
			hmOpen = Integer.parseInt(hm[0].split(":")[0]);
			mmOpen = Integer.parseInt(hm[0].split(":")[1]);
			hmClose = Integer.parseInt(hm[1].split(":")[0]);
			mmClose = Integer.parseInt(hm[1].split(":")[1]);
			haOpen = Integer.parseInt(hm[2].split(":")[0]);
			maOpen = Integer.parseInt(hm[2].split(":")[1]);
			haClose = Integer.parseInt(hm[3].split(":")[0]);
			maClose = Integer.parseInt(hm[3].split(":")[1]);
		}
		else {
			mOpen = aOpen = hm[0];
			mClose = aClose = hm[1];
			hmOpen = haOpen = Integer.parseInt(hm[0].split(":")[0]);
			mmOpen = maClose = Integer.parseInt(hm[0].split(":")[1]);
			hmClose = haClose = Integer.parseInt(hm[1].split(":")[0]);
			mmClose = maClose = Integer.parseInt(hm[1].split(":")[1]);
		}
	}

	/**
	 * Checks whether the restaurant is open at the given time.
	 *
	 * @param time time to check
	 * @return {@code true} is the restaurant is open at that time
	 */
	public boolean isOpenAt(String time){
		int h = Integer.parseInt(time.split(":")[0]);
		int m = Integer.parseInt(time.split(":")[1]);
		if (h>hmOpen && h<hmClose || h>haOpen && h<haClose) return true;
		if (h>hmClose && h<haOpen || h<hmOpen  || h>haClose) return false;
		if (h==hmOpen) {
			if (m>=mmOpen) return true;
			else return false;
		}
		if (h == haOpen) {
			if (m>=maOpen) return true;
			else return false;
		}
		if (h == haClose) {
			if (m<=maClose) return true;
			else return false;
		}
		if (h == hmClose) {
			if (m<=mmClose) return true;
			else return false;
		}
		
		return false;
	}

	/**
	 * Adds a menu to the list of menus offered by the restaurant
	 *
	 * @param menu	the menu
	 */
	public void addMenu(Menu menu) {
		this.menu.put(menu.getName(), menu);
	}

	/**
	 * Gets the restaurant menu with the given name
	 *
	 * @param name	name of the required menu
	 * @return menu with the given name
	 */
	public Menu getMenu(String name) {
		return this.menu.get(name);
	}

	/**
	 * Retrieve all order with a given status with all the relative details in text format.
	 *
	 * @param status the status to be matched
	 * @return textual representation of orders
	 */
	public String ordersWithStatus(OrderStatus status) {
		StringBuilder tmp = new StringBuilder();
		this.orders.sort((a,b) -> {
			int d = a.client.getLastName().compareTo(b.client.getLastName());
			if (d != 0) return d;
			d = a.client.getFirstName().compareTo(b.client.getFirstName());
			if ( d != 0) return d;
			d = a.deliveryTime.split(":")[0].compareTo(b.deliveryTime.split(":")[0]);
			if (d != 0) return d;
			return a.deliveryTime.split(":")[1].compareTo(b.deliveryTime.split(":")[1]);
		});
		for (Order o : orders) {
			if (o.status == status) tmp.append(o.toString());
		}
		return tmp.toString();
	}
	
}
