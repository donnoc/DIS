package de.dis2011.persistance.data;

public class PageData {
	private int pageID;
	private int logSequenceNumber;
	private String data;
	
	private PageData() {}
	
	public static PageData createPage(int pageID, int logSequenceNumber, String data)
	{
		PageData result = new PageData();
		result.pageID = pageID;
		result.logSequenceNumber = logSequenceNumber;
		result.data = data;
		return result;
	}

	public int getPageID() {
		return pageID;
	}

	public int getLogSequenceNumber() {
		return logSequenceNumber;
	}

	public String getData() {
		return data;
	}	
}
