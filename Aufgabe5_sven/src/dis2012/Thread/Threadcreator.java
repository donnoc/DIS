package dis2012.Thread;
import java.lang.reflect.Array;

import dis2012.Thread.Threadname;

public class Threadcreator {
	
	public String array[] = new String[10];

	public Threadcreator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void createThread() {

		
		Thread t1 = new Threadname("Thread1");
		Thread t2 = new Threadname("Thread2");
		
		t1.start();
		t2.start();
		
		array[0] = t1.toString();
		array[1] = t2.toString();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		
		t1.interrupt();
		t2.interrupt();
	}
	
	public void getThreads() {
		
		for(int i = 0; i<10; i++) {
			System.out.println("Arrayname: " + array[i]);
		}
	}
}
