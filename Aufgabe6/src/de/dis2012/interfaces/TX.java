package de.dis2012.interfaces;

import de.dis2012.core.RessourcenManager;

/**
 *  Interface TX
 *  Aktionen der Transaktionen
 *  
 * @author sven_51
 *
 */
public interface TX {
	
	
	public int begin();
	public boolean commit(int transactionId);
	public boolean rollback(int transactionId);
	public boolean registration(RessourcenManager ressourcenmanager, int transactionId);

}
