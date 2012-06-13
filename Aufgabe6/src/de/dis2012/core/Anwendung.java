package de.dis2012.core;

import java.util.HashMap;


public class Anwendung {

	HashMap <Integer, Daten> datensatz = new HashMap <Integer, Daten>(); 
	String data;
	
	/**
	 * Startet die Anwendung und fragt beim Koordinator eine TransactionId an.
	 */
	public void Start() {
	
		Koordinator koordinator = new Koordinator();
		System.out.println("[Anwendung] Mšchte Transaktion starten");
		Transaktion hans = new Transaktion("hallo du da");
		// vergebe TransaktionsIDs
		hans.setTransactionId(koordinator.begin());
	}

}
