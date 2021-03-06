package de.dis2012.core;

public class Client {
	private int transactionId; 
	PersistanceManager manager;
	String clientname;
	
	public Client(String clientname) {
		this.clientname = clientname;
	}
	
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
	public void connectToPManager() {
		manager = PersistanceManager.getInstance();
		this.setTransactionId(manager.beginTransaction());
		System.out.println("[Client] verbinden zu PersistanceManager mit TransactionId: " + this.getTransactionId());
	}

	public void write(int transactionId, int pageId, String data) {
		manager.write(transactionId, pageId, data);
		System.out.println("[Client] Schreibe Daten");
		System.out.println("[Client] 	transactionId: " + transactionId);
		System.out.println("[Client] 	pageId:        " + pageId);
		System.out.println("[Client] 	data:         '" + data + "'");
	}
	
	public void commit() {
		manager.commit(this.getTransactionId());
		//System.err.println(this.getTransactionId());
	}


	
	
}
