package dis2012.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import dis2012.core.PersistanceManager;

public class Logging {
	
	private int lsn= 0;
	private int taId= 0;
	private int pageId= 0;
	private int redo= 0; // 0 oder 1 für redo oder nicht

	public Logging() {		
	}
	
	public void setLSN(int lsn) {
		this.lsn = lsn;
	}
	
	public void setTaID(int taId) {
		this.taId = taId;
	}
	
	public void setPageID(int pageId) {
		this.pageId = pageId;
	}
	
	public void setReDo(int redo) {
		this.redo = redo;
	}
	
	
	public void logToConsoleCommit() {
		
		System.out.println("**********************************");
		System.out.println("LSN: " + lsn);
		System.out.println("TransaktionsID: " + taId);
		System.out.println("Redooperation: " + redo);
		System.out.println("**********************************");
	}
	

	public static void main(String []args) {
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));
			out.write("LSN: ");
			out.newLine();
			out.write("TransaktionsID: ");
			out.newLine();
			out.write("Redooperation: ");
			out.close();
		}
		catch (IOException e)
		{
			System.out.println("Exception ");		
		}
		
		return ;
	}

}
