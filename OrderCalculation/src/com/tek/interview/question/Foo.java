package com.tek.interview.question;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* ****************************************************************************************
 
Please remove all bugs from the code below to get the following output:

<pre>

*******Order 1*******
1 book: 13.74
1 music CD: 16.49
1 chocolate bar: 0.94
Sales Tax: 2.84
Total: 28.33
*******Order 2*******
1 imported box of chocolate: 11.5
1 imported bottle of perfume: 54.62
Sales Tax: 8.62
Total: 57.5
*******Order 3*******
1 Imported bottle of perfume: 32.19
1 bottle of perfume: 20.89
1 packet of headache pills: 10.73
1 box of imported chocolates: 12.94
Sales Tax: 8.77
Total: 67.98
Sum of orders: 153.81
 
</pre>
 
******************************************************************************************** */

/*
 * represents an item, contains a price and a description.
 *
 */
class Item {

	private String description;
	private float price;

	public Item(String description, float price) {
		super();
		this.description = description;
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public float getPrice() {
		return price;
	}
}

/*
 * represents an order line which contains the @link Item and the quantity.
 *
 */
class OrderLine {

	private int quantity;
	private Item item;

	/*
	 * @param item Item of the order
	 * 
	 * @param quantity Quantity of the item
	 */
	public OrderLine(Item item, int quantity) throws Exception {
		if (item == null) {
			System.err.println("ERROR - Item is NULL");
			throw new Exception("Item is NULL");
		}
		assert quantity > 0;
		this.item = item; // bug 2
		this.quantity = quantity; // bug 3
	}

	public Item getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}
}

class Order {

	private List<OrderLine> orderLines = new ArrayList<OrderLine>(); // bug 1

	public void add(OrderLine o) throws Exception {
		if (o == null) {
			System.err.println("ERROR - Order is NULL");
			throw new IllegalArgumentException("Order is NULL");
		}
		orderLines.add(o);
		
	}

	public int size() {
		return orderLines.size();
	}

	public OrderLine get(int i) {
		return orderLines.get(i);
	}

	public void clear() {
		this.orderLines.clear();
	}
}

class calculator {

	public static double rounding(double value) {
		return ( (value * 100)) / 100; // bug15
	}

	/**
	 * receives a collection of orders. For each order, iterates on the order lines and calculate the total price which
	 * is the item's price * quantity * taxes.
	 * 
	 * For each order, print the total Sales Tax paid and Total price without taxes for this order
	 */
	public void calculate(Map<String, Order> o) {

		double grandtotal = 0;

		// Iterate through the orders
		for (Map.Entry<String, Order> entry : o.entrySet()) {
			System.out.println("*******" + entry.getKey() +"*******");
			//grandtotal = 0; // bug12

			Order r = (Order)entry.getValue();

			double totalTax = 0;
			double total = 0;
			
			
			// Iterate through the items in the order
			for (int i = 0; i <r.size(); i++) { // bug4

				// Calculate the taxes
				double tax = 0;
				
				if (r.get(i).getItem().getDescription().contains("imported")) {
					tax = rounding(r.get(i).getItem().getPrice() * 0.15); // Extra 5% tax on
					// imported items
				} else {
					tax = rounding(r.get(i).getItem().getPrice() * 0.10);
				}

				// Calculate the total price
				
				double totalprice = r.get(i).getItem().getPrice() + Math.round(tax*100.0)/100.0;// bug 7

				// Print out the item's total price

				System.out.println(r.get(i).getQuantity()+" "+r.get(i).getItem().getDescription() + ": " + Math.round(totalprice*100.0)/100.0); //bug 8, bug 16

				// Keep a running total
				totalTax += Math.round(tax*100.0)/100.0;//tax;
				total += r.get(i).getItem().getPrice();
			}

			// Print out the total taxes
			System.out.println("Sales Tax: " + Math.round(totalTax*100.0)/100.0);// bug 9

			total = total + totalTax;

			// Print out the total amount
			System.out.println("Total: " + Math.round((total-totalTax) * 100.0) / 100.0);// bug 13
			grandtotal += total-totalTax; // bug 14
		}

		System.out.println("Sum of orders: " + Math.round(grandtotal * 100.0) / 100.0);
	}
}

public class Foo {

	public static void main(String[] args) throws Exception {

		Map<String, Order> o = new TreeMap<String, Order>();

		Order c = new Order();

		double grandTotal = 0;

		c.add(new OrderLine(new Item("book", (float) 12.49),1));
		c.add(new OrderLine(new Item("music CD", (float) 14.99), 1));
		c.add(new OrderLine(new Item("chocolate bar", (float) 0.85), 1));

		o.put("Order 1", c); // bug 6 

		// Reuse cart for an other order
		
		//c.clear();// bug 5
		
		
		Order d = new Order();
		d.add(new OrderLine(new Item("imported box of chocolate", 10), 1));
		d.add(new OrderLine(new Item("imported bottle of perfume", (float) 47.50), 1));

		o.put("Order 2", d); // bug 6
		

		// Reuse cart for an other order
		//d.clear();
		Order e = new Order();
		e.add(new OrderLine(new Item("imported bottle of perfume", (float) 27.99), 1));// bug 10
		e.add(new OrderLine(new Item("bottle of perfume", (float) 18.99), 1));
		e.add(new OrderLine(new Item("packet of headache pills", (float) 9.75), 1));
		e.add(new OrderLine(new Item("box of imported chocolates", (float) 11.25), 1));// bug 11

		o.put("Order 3", e);

		new calculator().calculate(o);
		e.clear();
		c.clear();
		d.clear();

	}
}
