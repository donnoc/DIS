package de.dis2012.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;

import de.dis2012.core.log.Logdatensatz;
import de.dis2012.core.log.Puffer;

public class PersistanceManager {
	static final private PersistanceManager persistancemanager;
	private int transactionId = 0;
	private Puffer puffer;
	
	public int getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
	static {
		try {
			persistancemanager = new PersistanceManager();
		}catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private PersistanceManager() {
		// initialisiert einen Puffer
		this.puffer = new Puffer();
	}
	
	static public PersistanceManager getInstance() {
		return persistancemanager;
	}
	
	public int beginTransaction() {
		setTransactionId(getTransactionId()+1);
		System.out.println("begin Transaction" + "\n" + "	with TransactionId: " + this.getTransactionId());
		return this.transactionId;
	}

	public void commit(int transactionId) {
		System.out.println("commit Thread with TransactionId: " + this.getTransactionId());
	}
	
	public void update() {
		
	}
	
	public void delete() {
		
	}
	
	public void write(int transactionId, int pageId, String data) {
		Logdatensatz datensatz = new Logdatensatz();
		int pufferStatus;
		Enumeration pufferKeys;
		
		
		datensatz.setTransactionId(transactionId);
		datensatz.setPageId(pageId);
		datensatz.setData(data);
		
		// schreibe datensatz in den Puffer
		pufferStatus = this.puffer.putInPuffer(datensatz);
		
		// pr�fe, ob der Puffer voll ist
		if(pufferStatus == 0) {
			System.out.println("[PersistanceManager] Puffer Daten werden auf die Platte geschrieben");
			
			// holle alley keys die im puffer zu finden sind
			pufferKeys = this.puffer.getPuffer().keys();
		
			// Datens�tze k�nnen auf die Platte gecshrieben werden
			while (pufferKeys.hasMoreElements()) {
				String key = String.valueOf(pufferKeys.nextElement());
				System.out.println(
					// wirft noch fehler aus
					"[PersistanceManager]	key: " + key + " --> " //+ this.puffer.getPuffer().get(key).getData()
				);
				
//				File f = new File(pageId + ".txt");
//				
//				try{
//					if(!f.exists()) {
//						FileWriter fw = new FileWriter(pageId + ".txt");
//						fw.write(transactionId + "\n" + pageId + "\n" + data );
//						fw.flush();
//						fw.close();
//					} else {
//						f.delete();
//					}
//				
//				} catch(IOException e) {
//					
//				}
			}

		}
		

	}
}