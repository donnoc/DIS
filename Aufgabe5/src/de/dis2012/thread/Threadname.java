package de.dis2012.thread;

public class Threadname extends Thread {

	String threadname;
	
	public Threadname(String name) {
		this.threadname = name;
	}
	
	public String getThreadname() {
		return threadname;
	}
	
	public void run() {
		System.out.println("Threadname: " + threadname );
		while(true) {
			try{
				Thread.sleep(2000);
			} catch(InterruptedException e) {
				return;
			}		
		}
	}
}
