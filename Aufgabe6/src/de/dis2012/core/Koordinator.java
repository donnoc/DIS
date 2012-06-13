package de.dis2012.core;

import de.dis2012.interfaces.TX;


public class Koordinator implements TX{
	
	int transactionId = 0;
	
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public void sendeTransactionId() {
	
	}

	@Override
	public int begin() {

		transactionId++;
		
		System.out.println("[Koordinator] TransaktionsId: " + transactionId + " vergeben");		
		System.out.println("[Koordinator] Starte Transaktion mit der TransaktionsId: " + transactionId);
		
		return transactionId;
	}

	@Override
	public boolean commit(int transactionId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rollback(int transactionId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registration(RessourcenManager ressourcenmanager,
			int transactionId) {
		// TODO Auto-generated method stub
		return false;
	}


	public void begin2PhaseCommit() {
		
	}
}
