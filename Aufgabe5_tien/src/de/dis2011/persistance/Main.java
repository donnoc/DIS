package de.dis2011.persistance;

import de.dis2011.persistance.core.PersistenceClient;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PersistenceClient client1 = new PersistenceClient(1);
		PersistenceClient client2 = new PersistenceClient(2);
		PersistenceClient client3 = new PersistenceClient(3);
		PersistenceClient client4 = new PersistenceClient(4);
		PersistenceClient client5 = new PersistenceClient(5);

		new Thread(client1).start();
		new Thread(client2).start();
		new Thread(client3).start();
		new Thread(client4).start();
		new Thread(client5).start();
	}

}
