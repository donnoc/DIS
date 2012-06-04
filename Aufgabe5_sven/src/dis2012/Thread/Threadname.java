package dis2012.Thread;

public class Threadname extends Thread {

	String threadname;
	
	public Threadname(String name) {
		this.threadname = name;
	}
	
	public void run() {
		while(true) {
			try{
				Thread.sleep(2000);
			} catch(InterruptedException e) {
				return;
			}
			
			System.out.println("Threadname: " + threadname );
		}

	}
	
}
