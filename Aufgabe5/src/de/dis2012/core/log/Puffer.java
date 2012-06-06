package de.dis2012.core.log;

import java.util.Hashtable;

public class Puffer {
	Hashtable<Integer, Logdatensatz> puffer = new Hashtable<Integer, Logdatensatz>();
	int status;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Puffer(){
		// initialisiert, dass Daten in den Puffer geschrieben werden kšnnen
		this.status = 1;
	}

	public Hashtable<Integer, Logdatensatz> getPuffer() {
		return puffer;
	}

	public void setPuffer(Hashtable<Integer, Logdatensatz> puffer) {
		System.out.println("[Puffer] Puffer wurde geleert");
		this.puffer = puffer;
		
		System.out.println("[Puffer] Status wird zurŸckgesetzt");
		this.setStatus(1);
	}

	/**
	 * putInPuffer
	 * 
	 * schriebt in den Puffer einen Logdatensatz und gibt eine 1 zurŸck, 
	 * falls noch platz fŸr weitere ist
	 * @param logdatensatz
	 * @return int 1: Zeigt an, noch platz bestand und der Datensatz eingefŸgt werden konnte
	 * @return int 0: Zeigt an, dass kein weiterer platz fŸr LogdatensŠtze besteht
	 */
	public int putInPuffer(Logdatensatz logdatensatz) {		

		if(puffer.size() < 5) {
			puffer.put(logdatensatz.getPageId(), logdatensatz);
			System.out.println("[Puffer] Log mit PageId " + logdatensatz.getPageId() + " wurde geschrieben");
			System.out.println("[Puffer] Es befinden sich " + this.puffer.size() + " Elemente im Puffer.");
			// gibt zurŸck, dass noch platz zum einfŸgen war
			status = 1;
		}else{
			System.out.println("[Puffer] Puffer ist voll");
			
			// gibt zurŸck, dass keine weiteren LogdatensŠtze gecshrieben werden kšnnen
			this.status = 0;
		}
		
		return this.status;
	}

	public void setRedo(int transactionId) {
		
		Logdatensatz logdatensatz = this.puffer.get(transactionId);
		
		logdatensatz.setRedo(1);
	}

	
}
