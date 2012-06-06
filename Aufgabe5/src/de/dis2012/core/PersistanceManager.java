package de.dis2012.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import de.dis2012.core.log.Logdatensatz;
import de.dis2012.core.log.Puffer;

public class PersistanceManager {
	static final private PersistanceManager persistancemanager;
	private int transactionId = 0;
	private Puffer puffer;
	private int logSequenceNumber = 0;
	
	public int getLogSequenceNumber() {
		return logSequenceNumber;
	}

	public void setLogSequenceNumber(int logSequenceNumber) {
		this.logSequenceNumber = logSequenceNumber;
	}

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
		System.out.println("[PersistanceManager] begin Transaction");
		System.out.println("[PersistanceManager] 	with TransactionId: " + this.getTransactionId());
		return this.transactionId;
	}

	public void commit(int transactionId) {
		
		Logdatensatz datensatz = new Logdatensatz();
		int pufferStatus;
		Enumeration<Integer> pufferKeys;
		
		// holle alley keys die im puffer zu finden sind
		pufferKeys = this.puffer.getPuffer().keys();
					
		// DatensŠtze kšnnen auf die Platte gecshrieben werden
		while (pufferKeys.hasMoreElements()) {
			
			/*
			 *  lese logdPuffer aus 
			 */
			int key = pufferKeys.nextElement();
			System.out.println("[PersistanceManager]	key: "
					+ String.valueOf(key) + " --> "
					+ this.puffer.getPuffer().get(key).getData());

			String insertData = this.puffer.getPuffer().get(key).getData();
			int insertSequenznummer = this.puffer.getPuffer().get(key).getSequenznummer();
			int insertTransactionId = this.puffer.getPuffer().get(key).getTransactionId();
			int insertPageId = this.puffer.getPuffer().get(key).getPageId();
			// ende
			
			if(transactionId == insertTransactionId) {
				this.writeTxt(insertPageId, insertSequenznummer, insertTransactionId, insertData);
				this.puffer.setRedo(key);
			}
		}
		
		/**
		 * lese logDateien aus
		 */
		File f = new File("/Users/sven_51/Documents/UNI_SoSe_2012/DIS/UE/workspace/Aufgabe5/");
		File[] fileArray = f.listFiles();

		for(int i=0; i<fileArray.length; i++) {

			if(fileArray[i].getName().substring(fileArray[i].getName().length() - 3, fileArray[i].getName().length()).equals("txt")){

				String file = "/Users/sven_51/Documents/UNI_SoSe_2012/DIS/UE/workspace/Aufgabe5/"
						+ fileArray[i].getName();

				try {
					BufferedReader in = new BufferedReader(new FileReader(file));
					String zeile = null;
					
					String[] transactionIdArray = new String[5];
					int j = 0;
					while ((zeile = in.readLine()) != null) {
						transactionIdArray[j] = zeile;
						System.out.println("Gelesene Zeile: " + zeile);
						j++;
					}
					
					if(transactionId == Integer.parseInt(transactionIdArray[1])) {
						this.writeTxt(Integer.parseInt(transactionIdArray[2]), Integer.parseInt(transactionIdArray[0]), Integer.parseInt(transactionIdArray[1]), transactionIdArray[3]);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		
		
		System.out.println("[PersistanceManager] commit Thread with TransactionId: " + transactionId);
	}
	
	public void writeTxt (int insertPageId, int insertSequenznummer, int insertTransactionId, String insertData) {
		
		File f = new File(insertPageId + ".txt");
		
		try {
			if (!f.exists()) {
				FileWriter fw = new FileWriter(insertPageId + ".txt");
				fw.write(insertSequenznummer + "\n" + insertTransactionId + "\n" + insertPageId + "\n" + insertData);
				fw.flush();
				fw.close();
			} else {
				f.delete();
				FileWriter fw = new FileWriter(insertPageId + ".txt");
				fw.write(insertSequenznummer + "\n" + insertTransactionId + "\n" + insertPageId + "\n" + insertData);
				fw.flush();
				fw.close();
			}

		} catch (IOException e) {

		}
	}
	
	public void update() {
		
	}
	
	public void delete() {
		
	}
	
	public void write(int transactionId, int pageId, String data) {
		Logdatensatz datensatz = new Logdatensatz();
		int pufferStatus;
		Enumeration<Integer> pufferKeys;
		logSequenceNumber++;
		
		datensatz.setTransactionId(transactionId);
		datensatz.setPageId(pageId);
		datensatz.setData(data);
		datensatz.setSequenznummer(logSequenceNumber);
		
		// schreibe datensatz in den Puffer
		pufferStatus = this.puffer.putInPuffer(datensatz);
		
		// prŸfe, ob der Puffer voll ist
		if(pufferStatus == 0) {
			System.out.println("[PersistanceManager] Puffer Daten werden auf die Platte geschrieben");
			
			// holle alley keys die im puffer zu finden sind
			pufferKeys = this.puffer.getPuffer().keys();
		
			// DatensŠtze kšnnen auf die Platte gecshrieben werden
			while (pufferKeys.hasMoreElements()) {
				int key = pufferKeys.nextElement();
				System.out.println("[PersistanceManager]	key: " + String.valueOf(key) + " --> " + this.puffer.getPuffer().get(key).getData());
				
				String insertData = this.puffer.getPuffer().get(key).getData();
				int insertSequenznummer = this.puffer.getPuffer().get(key).getSequenznummer();
				int insertTransactionId = this.puffer.getPuffer().get(key).getTransactionId();
				int insertPageId = this.puffer.getPuffer().get(key).getPageId();
				int insertRedo = this.puffer.getPuffer().get(key).getRedo();
				
				File f = new File(pageId + ".txt");
				
				try{
					if(!f.exists()) {
						FileWriter fw = new FileWriter(insertPageId + "_log" + ".txt");
						fw.write(insertSequenznummer + "\n" + insertTransactionId + "\n" +
								insertPageId + "\n" + insertData + "\n" + insertRedo );
						fw.flush();
						fw.close();
					} else {
						f.delete();
						FileWriter fw = new FileWriter(insertPageId + "_log" + ".txt");
						fw.write(insertSequenznummer +  "\n" + insertTransactionId + "\n" + insertPageId + "\n" + insertData + "\n" + insertRedo );
						fw.flush();
						fw.close();
					}
				
				} catch(IOException e) {
					
				}
			}
			
			// puffer leeren
			this.puffer.setPuffer(new Hashtable<Integer, Logdatensatz>());
			// schreibe datensatz in den Puffer
			pufferStatus = this.puffer.putInPuffer(datensatz);
		}
		

	}
}
