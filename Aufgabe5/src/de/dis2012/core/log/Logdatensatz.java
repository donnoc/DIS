package de.dis2012.core.log;

public class Logdatensatz {
	int sequenznummer;
	int pageId;
	int transactionId;
	String data;
	
	public int getSequenznummer() {
		return sequenznummer;
	}

	public void setSequenznummer(int sequenznummer) {
		this.sequenznummer = sequenznummer;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
	public int getPageId() {
		return pageId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	
}
