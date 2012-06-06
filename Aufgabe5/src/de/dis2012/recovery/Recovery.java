package de.dis2012.recovery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import de.dis2012.core.log.Logdatensatz;

public class Recovery {
	
	Hashtable<Integer, Logdatensatz> recover = new Hashtable<Integer, Logdatensatz>();
	
	public void analyse() {
		
	}
	
	public  void readFiles(){
		/**
		 * lese logDateien aus
		 */
		File f = new File("/Users/sven_51/Documents/UNI_SoSe_2012/DIS/UE/workspace/Aufgabe5/");
		File[] fileArray = f.listFiles();

		for(int i=0; i<fileArray.length; i++) {

			if(fileArray[i].getName().substring(fileArray[i].getName().length() - 8, fileArray[i].getName().length()).equals("_log.txt")){

				String file = "/Users/sven_51/Documents/UNI_SoSe_2012/DIS/UE/workspace/Aufgabe5/"
						+ fileArray[i].getName();

				try {
					BufferedReader in = new BufferedReader(new FileReader(file));
					String zeile = null;
					
					String[] transactionIdArray = new String[4];
					int j = 0;
					while ((zeile = in.readLine()) != null) {
						transactionIdArray[j] = zeile;
						System.out.println("Gelesene Zeile: " + zeile);
						j++;
					}
					
					String redo = transactionIdArray[4];
					
					if(redo == "1") {
						//this.writeTxt(Integer.parseInt(transactionIdArray[2]), Integer.parseInt(transactionIdArray[0]), Integer.parseInt(transactionIdArray[1]), transactionIdArray[3]);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		

	}
	
	public void writeTxt (int insertPageId, int insertSequenznummer, int insertTransactionId, String insertData, int insertRedo) {
		
		File f = new File(insertPageId + ".txt");
		
		try {
			if (!f.exists()) {
				FileWriter fw = new FileWriter(insertPageId + ".txt");
				fw.write(insertSequenznummer + "\n" + insertTransactionId + "\n" + insertPageId + "\n" + insertData + "\n" + insertRedo);
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

}
