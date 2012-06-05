package de.dis2012;

import de.dis2012.core.Client;

public class Main {

	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		Client hans = new Client();
		Client klaus = new Client();
		
		hans.connectToPManager();
		klaus.connectToPManager();
		
		hans.write(10, 10, "zehn");
		hans.write(25, 25, "fünfundzwanzig");
		hans.write(3, 3, "drei");
		klaus.write(14, 14, "vierzehn");
		klaus.write(5, 5, "fünf");
		klaus.write(8, 8, "acht");
		klaus.write(87, 87, "siebenundachtzig");
		

		
	}

	
}
