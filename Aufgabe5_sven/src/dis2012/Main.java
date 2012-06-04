package dis2012;

import dis2012.Menu.*;
import dis2012.Thread.*;
import dis2012.core.PersistanceManager;

import java.util.Scanner;


public class Main {

	
	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		//Menüoptionen
		final int MENU_COMMIT = 0;
		final int MENU_UPDATE= 1;
		final int MENU_TERMINATE = 2;
		final int MENU_THREADS = 3;
		final int MENU_BEGIN_TRANSACTION = 4;
		final int QUIT = 5;
		
		//Erzeuge Menü
		Menu mainMenu = new Menu("Menü");
		mainMenu.addEntry("commit", MENU_COMMIT);
		mainMenu.addEntry("update", MENU_UPDATE);
		mainMenu.addEntry("terminate", MENU_TERMINATE);
		mainMenu.addEntry("list threads", MENU_THREADS);
		mainMenu.addEntry("begins a new transaction", MENU_BEGIN_TRANSACTION);
		mainMenu.addEntry("quit", QUIT);
		
		
		//Verarbeite Eingabe
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
				case MENU_COMMIT:
					showCommitMenu();
					
					
					//if(pfa.authenticate()) {
						//MaklerEditor me = new MaklerEditor(service);
						//me.showMaklerMenu();
					//}
					break;
				case MENU_UPDATE:
					//if(ma.authenticate()) {
						//PersonEditor pe = new PersonEditor(service);
						//pe.showPersonMenu();
					//}
					break;
				case MENU_TERMINATE:
					//if(ma.authenticate()) {
						//ImmobilienEditor ie = new ImmobilienEditor(service, ma.getLastAuthenticatedMakler());
						//ie.showImmoMenu();
					//}
					break;
				case MENU_THREADS:
					Threadcreator tc= new Threadcreator();
						tc.createThread();
						tc.getThreads();
					break;
				case MENU_BEGIN_TRANSACTION:
					PersistanceManager p = new PersistanceManager();
					p.beginTransaction();
					System.out.println("transaction started");
					break;
				case QUIT:
					return;
			
			}
		}
	}
	
	
	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showCommitMenu() {
		final int READ_TAID = 0;
		final int QUIT = 1;
		
		//Erzeuge Menü
		Menu commitMenu = new Menu("Commit Menü");
		commitMenu.addEntry("transactionid: ", READ_TAID);
		commitMenu.addEntry("return to main Menü", QUIT);
		
		//Verarbeite Eingabe
				while(true) {
					int response = commitMenu.show();
					
					switch(response) {
						case READ_TAID:	
							int transactionid;
							PersistanceManager p = new PersistanceManager();
							
							Scanner in = new Scanner(System.in);
							transactionid = in.nextInt();
							p.commit(transactionid);
							in.close();

							showCommitMenu();
							
							break;
						case QUIT:
							showMainMenu();
					}
				}
		
	}
	
	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		
		//int taid;
		//String consolestring;
		//int number;
		
		String data = "blabla \n" + "blabla\n";
		
		PersistanceManager pm = new PersistanceManager();
		pm.write(123, 123, data);
		
		Threadcreator tc = new Threadcreator();
		tc.createThread();
		
		showMainMenu();
		
		//Scanner in = new Scanner(System.in);
		
		// consolestring = in.nextLine();
					
	}
	
}
