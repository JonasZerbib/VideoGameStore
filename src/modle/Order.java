package modle;

import java.util.*;

public class Order {

	// Fields
	protected int numOrder;
	protected int idClient;
	protected Vector<Game> orderList = null;// The games that has been ordered by our client with their amount

	public Order() {

	}

	public Order(int numOrder, int idClient, Vector<Game> orderList) {
		super();
		this.numOrder = numOrder;
		this.idClient = idClient;
		copyVector(orderList);

	}

	public Order(Order newOrder) {
		super();
		this.numOrder = newOrder.numOrder;
		this.idClient = newOrder.idClient;
		this.orderList = new Vector<Game>();

	}

	public int getNumOrder() {
		return numOrder;
	}

	public void setNumOrder(int numOrder) {
		this.numOrder = numOrder;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public Vector<Game> getOrderList() {
		return orderList;
	}

	public void setOrderList(Vector<Game> orderList) {
		copyVector(orderList);
	}

	// Function that copy our Vector
	private void copyVector(Vector<Game> orderListTwo) {
		this.orderList = new Vector<Game>();
		if(orderListTwo!=null)
		{
		for (int i = 0; i < orderListTwo.size(); i++) {
			this.orderList.add(orderListTwo.get(i));
		}
		return;
		}
		

	}

}
