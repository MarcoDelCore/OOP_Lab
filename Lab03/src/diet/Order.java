package diet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents and order issued by an {@link Customer} for a {@link Restaurant}.
 *
 * When an order is printed to a string is should look like:
 * <pre>
 *  RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
 *  	MENU_NAME_1->MENU_QUANTITY_1
 *  	...
 *  	MENU_NAME_k->MENU_QUANTITY_k
 * </pre>
 */
public class Order {
	Customer client;
	Restaurant restaurant;
	String deliveryTime;
	OrderStatus status;
	PaymentMethod pay;
	List<Menu> menu;
	Map<String, Integer> quantity;
	
	public Order(Customer c, Restaurant r, String t) {
		client = c;
		restaurant = r;
		deliveryTime = t;
		status = OrderStatus.ORDERED;
		pay = PaymentMethod.CASH;
		menu = new ArrayList<>();
		quantity = new TreeMap<>();
	}

	/**
	 * Possible order statuses
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED
	}

	/**
	 * Accepted payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD
	}

	/**
	 * Set payment method
	 * @param pm the payment method
	 */
	public void setPaymentMethod(PaymentMethod pm) {
		pay = pm;
	}

	/**
	 * Retrieves current payment method
	 * @return the current method
	 */
	public PaymentMethod getPaymentMethod() {
		return pay;
	}

	/**
	 * Set the new status for the order
	 * @param os new status
	 */
	public void setStatus(OrderStatus os) {
		status = os;
	}

	/**
	 * Retrieves the current status of the order
	 *
	 * @return current status
	 */
	public OrderStatus getStatus() {
		return status;
	}

	/**
	 * Add a new menu to the order with a given quantity
	 *
	 * @param menu	menu to be added
	 * @param quantity quantity
	 * @return the order itself (allows method chaining)
	 */
	public Order addMenus(String menu, int quantity) {
		Menu tmp = restaurant.getMenu(menu);
		for (String s : this.quantity.keySet()) {
			if (menu == s) {
				this.quantity.computeIfPresent(menu, (k,v) -> quantity);
				return this;
			}
		}
		this.menu.add(tmp);
		this.quantity.put(menu, quantity);
		
		return this;
	}
	
	public String toString() {
		menu.sort((a,b) -> {
			int d = a.getName().compareTo(b.getName());
			return d;
		});
		StringBuilder tmp = new StringBuilder();
		String info = restaurant.getName() + ", " + client.toStr() + " : (" + deliveryTime + "):";
		tmp.append(info).append("\n");
		System.out.println(info);
		for (Menu m : this.menu) {
			info = "\t" + m.getName() + "->" + this.quantity.get(m.getName());
			tmp.append(info).append("\n");
			System.out.println(info);
		}
		return tmp.toString();
	}
	
	
	
}
