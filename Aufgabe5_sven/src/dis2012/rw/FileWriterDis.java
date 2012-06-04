package dis2012.rw;

import java.io.FileWriter;

public class FileWriterDis {

	public void write(String args[]) throws Exception { 
		/*String source = "Now is the time for all good men\\n" 
		+ " to come to the aid of their country\\n" 
		+ " and pay their due taxes."; 
		char buffer[] = new char[source.length()]; 
		source.getChars(0, source.length(), buffer, 0); */
		FileWriter f0 = new FileWriter("/src/dis2012/Seiten/file1.txt"); 
		
		
		/*for (int i=0; i < buffer.length; i += 2) { 
		f0.write(buffer[i]); 
		} 
		f0.close(); 
		FileWriter f1 = new FileWriter("file2.txt"); 
		f1.write(buffer); 
		f1.close(); */ 
		} 
}
