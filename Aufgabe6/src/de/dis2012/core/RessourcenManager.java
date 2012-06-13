package de.dis2012.core;

import de.dis2012.interfaces.XA;

	

public class RessourcenManager implements XA {
	
	private int countRessourcenManager = 0;
	
	public RessourcenManager(String name) {
		
	}

	@Override
	public boolean write(int transactionId, int pid, String data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean prepare(int transactionId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void commit(int transactionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollback(int transactionId) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Registrierung beim ersten write Befehl der Anwendung/Transaktion beim Koordinator
	 */
	public void register(RessourcenManager ressourcenmanager) {
		
		RessourcenManager ressourcenManager = new RessourcenManager("" + countRessourcenManager);
	}

}
