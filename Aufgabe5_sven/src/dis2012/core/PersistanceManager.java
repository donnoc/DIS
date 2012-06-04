package dis2012.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dis2012.Thread.*;

public class PersistanceManager {
	
	
	static final private PersistanceManager persistancemanager;
	
	private int transactionId= 0;
	
		static{
			try{
				persistancemanager = new PersistanceManager();
			} catch (Throwable e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		
		public PersistanceManager() {
			
		}
	
		static public PersistanceManager getInstance() {
			return persistancemanager;
		}
		
	public int getTransactionId() {
		
		return transactionId;
	}

	
	public void beginTransaction() {
		
		transactionId = transactionId + 1;
		
		/**
		final int COMMIT= 0;
		final int UPDATE= 1;
		final int DELETE= 2;
		int operation = 0;
		
		switch(operation) {
		case COMMIT: 
				commitFunc(1);
			break;
		case UPDATE: 
				updateFunc();
			break;
		case DELETE:
				deleteFunc();
			break;
		}**/
		
		System.out.println("Transactionid: " + transactionId);
	}
	
	public int commit(int taid) {
	   
		System.out.println("Thread with transactionid " + taid + "committed");
		
	   return 0;
	}
	
	public void update() {
		
	}
	
	public void delete() {
		
	}
	
	public void write(int taid, int tpageid, String data) {
		
		File f = new File(tpageid + ".txt");
		
		try{
			if(!f.exists()) {
				FileWriter fw = new FileWriter(tpageid + ".txt");
				fw.write(taid + "\n" + tpageid + "\n" + data );
				fw.flush();
				fw.close();
			} else {
				f.delete();
			}
		
		} catch(IOException e) {
			
		}
	}
}
	

