package dis2012.rw;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileReaderDis {

	public static void main(String args[]) throws Exception { 
		FileReader fr = new FileReader("FileReaderDis.java"); 
		BufferedReader br = new BufferedReader(fr); 
		String s; 
		while((s = br.readLine()) != null) { 
		System.out.println(s); 
		} 
		fr.close(); 
		} 
	
}
