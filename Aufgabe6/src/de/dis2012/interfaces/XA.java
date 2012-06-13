package de.dis2012.interfaces;

/**
 * Aktionen des Koordinators
 * @author sven_51
 *
 */
public interface XA {

	public boolean write(int transactionId, int pid, String data);
	
	public boolean prepare(int transactionId);
	
	public void commit(int transactionId);
	
	public void rollback(int transactionId);
	
}
