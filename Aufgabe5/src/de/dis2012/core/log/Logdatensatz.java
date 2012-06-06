package de.dis2012.core.log;

public class Logdatensatz {
	int sequenznummer;
	int pageId;
	int transactionId;
	int redo= 0; // 0 kein recovery 1 redo
	
	public int getRedo() {
		return redo;
	}

	public void setRedo(int redo) {
		this.redo = redo;
	}

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
