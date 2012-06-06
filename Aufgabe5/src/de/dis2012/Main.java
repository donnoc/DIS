package de.dis2012;

import de.dis2012.core.Client;
import de.dis2012.thread.Threadcreator;
import de.dis2012.thread.Threadname;

public class Main {

	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		
		Client hans = new Client("hans");
		Client klaus = new Client("klaus");
		Client gruber = new Client("gruber");
		Client dirk = new Client("dirk");
		Client ulf = new Client("ulf");
		
		hans.connectToPManager();
		klaus.connectToPManager();
		gruber.connectToPManager();
		dirk.connectToPManager();
	    ulf.connectToPManager();
		
		hans.write(hans.getTransactionId(), 10, "zehn");
		hans.write(hans.getTransactionId(), 25, "fünfundzwanzig");
		hans.write(hans.getTransactionId(), 3, "drei");
		hans.write(hans.getTransactionId(), 17, "zehn");
		hans.write(hans.getTransactionId(), 18, "fünfundzwanzig");
		hans.write(hans.getTransactionId(), 99, "neunundneunzig");
		hans.write(hans.getTransactionId(), 97, "nenundneunzig");
		hans.write(hans.getTransactionId(), 98, "nundneunzig");
		hans.commit();
		klaus.write(klaus.getTransactionId(), 14, "vierzehn");
		klaus.write(klaus.getTransactionId(), 5, "fünf");
		klaus.write(klaus.getTransactionId(), 8, "acht");
		klaus.write(klaus.getTransactionId(), 88, "siebenundachtzig");
		
		gruber.write(gruber.getTransactionId(), 14, "vierzehn");
		gruber.write(gruber.getTransactionId(), 5, "fünf");
		gruber.write(gruber.getTransactionId(), 3, "acht");
		gruber.write(gruber.getTransactionId(), 90, "siebenundachtzig");
		
		gruber.write(gruber.getTransactionId(), 14, "vierzehn");
		gruber.write(gruber.getTransactionId(), 5, "fünf");
		gruber.write(gruber.getTransactionId(), 3, "acht");
		gruber.write(gruber.getTransactionId(), 91, "siebenundachtzig");
		
		dirk.write(dirk.getTransactionId(), 14, "vierzehn");
		dirk.write(dirk.getTransactionId(), 5, "fünf");
		dirk.write(dirk.getTransactionId(), 3, "acht");
		dirk.write(dirk.getTransactionId(), 92, "siebenundachtzig");
	
		ulf.write(ulf.getTransactionId(), 14, "vierzehn");
		ulf.write(ulf.getTransactionId(), 5, "fünf");
		ulf.write(ulf.getTransactionId(), 3, "acht");
		ulf.write(ulf.getTransactionId(), 93, "siebenundachtzig");
	}

	
}
