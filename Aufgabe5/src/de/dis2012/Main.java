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
		
		hans.write(10, 10, "haaalo");
		hans.write(25, 25, "du da");
		hans.write(3, 3, "du da");
		klaus.write(14, 14, "du da");
		klaus.write(5, 5, "du da");
		klaus.write(8, 8, "du da");
		klaus.write(87, 87, "du da");
		

		
	}

	
}
