package de.dis2012.thread;

public class Threadcreator {
	
	public String array[] = new String[10];

	public Threadcreator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void createThread(String threadname) {

		Thread t = new Threadname(threadname);
		
		t.start();
		/**
		Thread t1 = new Threadname("hans");
		Thread t2 = new Threadname("klaus");
		Thread t3 = new Threadname("gruber");
		Thread t4 = new Threadname("dirk");
		Thread t5 = new Threadname("ulf");	
		
		
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		array[0] = t1.toString();
		array[1] = t2.toString();
		array[0] = t3.toString();
		array[1] = t4.toString();
		array[0] = t5.toString();**/
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		
		//t1.interrupt();
		
		t.interrupt();
		
		/**t2.interrupt();
		t3.interrupt();
		t4.interrupt();
		t5.interrupt();**/
	}
	
	/**
	public void getThreads() {
		
		for(int i = 0; i<6; i++) {
			System.out.println("Threadname: " + array[i]);
		}
	}**/
}
