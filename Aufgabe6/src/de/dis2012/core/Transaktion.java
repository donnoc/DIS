package de.dis2012.core;

public class Transaktion {

	private int transactionId;
	private String data;
	
	public Transaktion(String data) {
		this.setData(data);
	}
	

	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
