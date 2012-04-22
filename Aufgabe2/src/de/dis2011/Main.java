package de.dis2011;

import java.util.ArrayList;

import de.dis2011.data.Makler;
import de.dis2011.data.Person;
import de.dis2011.data.vertrag.Kaufvertrag;
import de.dis2011.data.vertrag.Mietvertrag;
import de.dis2011.data.vertrag.Vertrag;

/**
 * Hauptklasse
 */
public class Main {
	
	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		showMainMenu();
	}
	
	/**
	 * MENUE: HAUPT
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		//Menüoptionen
		final int MENU_MAKLER   = 0;
		final int MENU_IMMOBIL  = 1;
		final int MENU_CONTRACT = 2;
		final int QUIT          = 3;
		
		//Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenü");
		mainMenu.addEntry("Makler-Verwaltung", MENU_MAKLER);
		mainMenu.addEntry("Immobilien-Verwaltung", MENU_IMMOBIL);
		mainMenu.addEntry("Vertragsmodus", MENU_CONTRACT);
		mainMenu.addEntry("Beenden", QUIT);
		
		//Verarbeite Eingabe
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
				case MENU_MAKLER:
					showMaklerMenu();
					break;
				case MENU_IMMOBIL:
					showImmobilienMenu();
					break;
				case MENU_CONTRACT:
					showVertragsMenu();
					break;
				case QUIT:
					return;
			}
		}
	}
	
	/**
	 * MENUE
	 * Zeigt die Maklerverwaltung
	 */
	public static void showMaklerMenu() {
		//Menüoptionen
		final int NEW_MAKLER    = 0;
		final int DELETE_MAKLER = 1;
		final int EDIT_MAKLER   = 2;
		final int BACK          = 3;
		
		//Maklerverwaltungsmenü
		Menu maklerMenu = new Menu("Makler-Verwaltung");
		maklerMenu.addEntry("Neuer Makler", NEW_MAKLER);
		maklerMenu.addEntry("Makler löschen", DELETE_MAKLER);
		maklerMenu.addEntry("Makler bearbeiten", EDIT_MAKLER);
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		//Verarbeite Eingabe
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_MAKLER:
					newMakler();
					break;
				case DELETE_MAKLER:
					deleteMakler();
					break;
				case EDIT_MAKLER:
					editMakler();
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 * MENUE
	 * Zeigt die Immobilenverwaltung
	 */
	public static void showImmobilienMenu() {
		//Menüoptionen
		final int NEW_IMMOBIL    = 0;
		final int DELETE_IMMOBIL = 1;
		final int EDIT_IMMOBIL   = 2;
		final int BACK          = 3;
		
		//Maklerverwaltungsmenü
		Menu maklerMenu = new Menu("Immobilien-Verwaltung");
		maklerMenu.addEntry("Neue Immobile", NEW_IMMOBIL);
		maklerMenu.addEntry("Immobile löschen", DELETE_IMMOBIL);
		maklerMenu.addEntry("Immobile bearbeiten", EDIT_IMMOBIL);
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		
		if(maklerLogin()){
			System.out.println("Sie wurden eingeloggt");
			
			while(true) {
				int response = maklerMenu.show();
				
				switch(response) {
					case NEW_IMMOBIL:
						showNewImmobile();
						break;
					case DELETE_IMMOBIL:
						showEditImmobile();
						break;
					case EDIT_IMMOBIL:
						showDeleteImmobile();
						break;
					case BACK:
						return;
				}
			}
		}else{
			System.out.println("leider falsches Passwort");
		}

	}
	
	/**
	 * MENU > Neue Immobilie
	 */
	public static void showNewImmobile() {
		//Menüoptionen
				final int NEW_HAUS    = 0;
				final int NEW_WOHNUNG = 1;
				final int BACK        = 2;
				
				//Maklerverwaltungsmenü
				Menu maklerMenu = new Menu("Neue Immobilie");
				maklerMenu.addEntry("Neues Haus", NEW_HAUS);
				maklerMenu.addEntry("Neue Wohnung", NEW_WOHNUNG);
				maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
				
				//Verarbeite Eingabe
				while(true) {
					int response = maklerMenu.show();
					
					switch(response) {
						case NEW_HAUS:
							newHaus();
							break;
						case NEW_WOHNUNG:
							newWohnung();
							break;
						case BACK:
							return;
					}
				}
	}
	
	/**
	 * MENU > Immobilie bearbeiten
	 */
	public static void showEditImmobile() {
		//Menüoptionen
				final int EDIT_HAUS    = 0;
				final int EDIT_WOHNUNG = 1;
				final int BACK        = 2;
				
				//Maklerverwaltungsmenü
				Menu maklerMenu = new Menu("Immobilie Bearbeiten");
				maklerMenu.addEntry("Neues Haus", EDIT_HAUS);
				maklerMenu.addEntry("Neue Wohnung", EDIT_WOHNUNG);
				maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
				
				//Verarbeite Eingabe
				while(true) {
					int response = maklerMenu.show();
					
					switch(response) {
						case EDIT_HAUS:
							editHaus();
							break;
						case EDIT_WOHNUNG:
							editWohnung();
							break;
						case BACK:
							return;
					}
				}
	}
	
	/**
	 * MENU > Immobilie löschen
	 */
	public static void showDeleteImmobile() {
		//Menüoptionen
				final int DELETE_HAUS    = 0;
				final int DELETE_WOHNUNG = 1;
				final int BACK           = 2;
				
				//Maklerverwaltungsmenü
				Menu maklerMenu = new Menu("Immobilie Bearbeiten");
				maklerMenu.addEntry("Neues Haus", DELETE_HAUS);
				maklerMenu.addEntry("Neue Wohnung", DELETE_WOHNUNG);
				maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
				
				//Verarbeite Eingabe
				while(true) {
					int response = maklerMenu.show();
					
					switch(response) {
						case DELETE_HAUS:
							deleteHaus();
							break;
						case DELETE_WOHNUNG:
							deleteWohnung();
							break;
						case BACK:
							return;
					}
				}
	}
	
	/**
	 * MENUE
	 * Zeigt die Maklerverwaltung
	 */
	public static void showVertragsMenu() {
		//Menüoptionen
		final int NEW_PERSON        = 0;
		final int CONCLUDE_CONTRACT = 1;
		final int OVERVIEW          = 2;
		final int BACK              = 3;
		
		//Maklerverwaltungsmenü
		Menu maklerMenu = new Menu("Vertragsverwaltung");
		maklerMenu.addEntry("Neue Person", NEW_PERSON);
		maklerMenu.addEntry("Abschließen", CONCLUDE_CONTRACT);
		maklerMenu.addEntry("Übersicht", OVERVIEW);
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		//Verarbeite Eingabe
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_PERSON:
					newPerson();
					break;
				case CONCLUDE_CONTRACT:
					showContractMenu();
					break;
				case OVERVIEW:
					vertragsuebersicht();
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 * MENUE > Vertragsverwaltung
	 * Zeigt die Vertragsmodus
	 */
	public static void showContractMenu() {
		//Menüoptionen
		final int KAUFVERTRAG = 0;
		final int MIETVERTRAG = 1;
		final int BACK        = 2;
		
		//Maklerverwaltungsmenü
		Menu maklerMenu = new Menu("Abschlussverwaltung");
		maklerMenu.addEntry("Kaufvertrag", KAUFVERTRAG);
		maklerMenu.addEntry("Mietvertrag", MIETVERTRAG);
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		//Verarbeite Eingabe
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case KAUFVERTRAG:
					newKaufvertrag();
					break;
				case MIETVERTRAG:
					newMietvertrag();
					break;
				case BACK:
					return;
			}
		}
	}
	
	
	
	
	
	/**
	 * Hier beginnen die Funktionen 
	 */
	
	
	
	
	
	/**
	 * Legt einen neuen Kaufvertrag an
	 */
	public static void newKaufvertrag() {
		Kaufvertrag k = new Kaufvertrag();

		k.setVertragsnummer(FormUtil.readString("Vertragsnummer"));
		k.setDatum(FormUtil.readString("Datum"));
		k.setOrt(FormUtil.readString("Ort"));
		
		k.setId_haus(FormUtil.readInt("ID Haus"));
		k.setId_person(FormUtil.readInt("ID Person"));
		k.setRatenzins(FormUtil.readInt("Ratenzins"));
		k.setAnzahl_raten(FormUtil.readInt("Anzahl Raten"));
		k.save();
		
		System.out.println("Kaufvertrag mit der ID "+k.getId()+" wurde erzeugt.");
	}
	
	/**
	 * Legt einen neuen Mietvertrag an
	 */
	public static void newMietvertrag() {
		Mietvertrag m = new Mietvertrag();

		m.setVertragsnummer(FormUtil.readString("Vertragsnummer"));
		m.setDatum(FormUtil.readString("Datum"));
		m.setOrt(FormUtil.readString("Ort"));
		
		m.setId_wohnung(FormUtil.readInt("ID Wohnung"));
		m.setId_person(FormUtil.readInt("ID Person"));
		m.setMietbegin(FormUtil.readInt("Mietbeginn"));
		m.setDauer(FormUtil.readInt("Dauer"));
		m.setNebenkosten(FormUtil.readInt("Nebenkosten"));
		m.save();
		
		System.out.println("Mietvertrag mit der ID "+m.getId()+" wurde erzeugt.");
	}
	
	/**
	 * Legt eine neue Person an, nachdem der Benutzer
	 * die entprechenden Daten eingegeben hat.
	 */
	public static void newPerson() {
		Person p = new Person();
		
		p.setVorname(FormUtil.readString("Vorname"));
		p.setNachname(FormUtil.readString("Nachname"));
		p.setAdresse(FormUtil.readString("Adresse"));
		p.save();
		
		System.out.println("Person mit der ID "+p.getId()+" wurde erzeugt.");
	}
	
	/**
	 * Übersicht der Verträge
	 */
	public static void vertragsuebersicht() {
		ArrayList<Vertrag> vetrag_alle = Vertrag.load_all_vertraege();
		
		// Liste der Verträge ausgeben
		System.out.println("Liste der VertragsNr.");
		for(Vertrag vertrag_single : vetrag_alle){
			System.out.println("[" + vertrag_single.getId() + "] " + vertrag_single.getVertragsNr());
		}
	}
	
	/**
	 * Legt einen neuen Makler an, nachdem der Benutzer
	 * die entprechenden Daten eingegeben hat.
	 */
	public static void newMakler() {
		Makler m = new Makler();
		
		m.setName(FormUtil.readString("Name"));
		m.setAddress(FormUtil.readString("Adresse"));
		m.setLogin(FormUtil.readString("Login"));
		m.setPassword(FormUtil.readString("Passwort"));
		m.save();
		
		System.out.println("Makler mit der ID "+m.getId()+" wurde erzeugt.");
	}
	
	/**
	 * Verwaltet einen Makler und lässt seine Daten ändern
	 */
	public static void editMakler() {
		Makler makler_old = new Makler();
		Makler makler_new = new Makler();
		
		System.out.println("Welchen Makler wollen Sie bearbeiten?");
		
		// läd eine Liste aller AMkler
		ArrayList<Makler> makler_alle = Makler.load_all_makler();
		
		// Liste der Makler ausgeben
		System.out.println("[0] Abbrechen");
		for(Makler makler_single : makler_alle){
			System.out.println("[" + makler_single.getId() + "] " + makler_single.getName());
		}
		
		int wahl = FormUtil.readInt("Nr.");
		
		if(wahl != 0){
			makler_old = Makler.load(wahl);

			makler_new.setId(makler_old.getId());
			
			System.out.println("Geben Sie den neuen Namen ein:" + makler_old.getName());
			makler_new.setName(FormUtil.readString("Name"));
			
			System.out.println("Geben Sie eine neue Adresse ein: " + makler_old.getAddress());
			makler_new.setAddress(FormUtil.readString("Adresse"));
			
			System.out.println("Geben Sie einen neuen Login ein: " + makler_old.getLogin());
			makler_new.setLogin(FormUtil.readString("Login"));
			
			System.out.println("Geben Sie ein neues Passwort ein: " + makler_old.getPassword());
			makler_new.setPassword(FormUtil.readString("Passwort"));
			

			makler_new.save();
			
			System.out.println("Makler mit der ID "+makler_new.getId()+" wurde geupdatet.");
		}else {
			System.out.println("Kein Makler wurde bearbeitet");
		}
		
		
		
	}
	
	/**
	 * Editiert den Makler
	 * kann Makler editieren oder löschen
	 */
	public static void deleteMakler() {
		String login_password = "aaa";
		
		// Passwortabfrage
		String password = FormUtil.readString("Bitte Passwort eingeben (" + login_password + ")");
		
		//prüfen, ob Passwort richtig ist
		if( password.equals(login_password)){
			System.out.println("Welchen Makler wollen Sie löschen?");
			
			// läd eine Liste aller AMkler
			ArrayList<Makler> makler_alle = Makler.load_all_makler();
			
			// Liste der Makler ausgeben
			System.out.println("[0] Abbrechen");
			for(Makler makler_single : makler_alle){
				System.out.println("[" + makler_single.getId() + "] " + makler_single.getName());
			}
			
			int id = FormUtil.readInt("Nr.");
			if(id != 0){
				Makler.delete(id);
			}
			
		}else{
			System.out.println("Leider falsch");
		}

		
	}

	
	public static boolean maklerLogin(){
	
		String eingabe_login = FormUtil.readString("Bitte login eingeben");
		String eingabe_password = FormUtil.readString("Bitte password eingeben");
		
		Makler m = Makler.load_from_login(eingabe_login);
		
		System.out.println(m.getName());
		System.out.println(m.getAddress());
		System.out.println(m.getLogin());
		System.out.println(m.getPassword());
		
		/** ToDo #####################################################################################################################################
		 * ###########################################################################################################################################
		 * 
		 * Der vergleich geht nicht obwohl das Passwort wirhctig ist
		 * 
		 */
		String user_pass = m.getPassword();
		if( user_pass.equals(eingabe_password) ) {
			return true;
		}else{
			return false;
		}

	}
	
	public static void newHaus() {		
		System.out.println("noch nicht vorhanden");
	}
	
	public static void newWohnung() {
		System.out.println("noch nicht vorhanden");
	}
	
	public static void editHaus() {		
		System.out.println("noch nicht vorhanden");
	}
	
	public static void editWohnung() {
		System.out.println("noch nicht vorhanden");
	}
	
	public static void deleteHaus() {		
		System.out.println("noch nicht vorhanden");
	}
	
	public static void deleteWohnung() {
		System.out.println("noch nicht vorhanden");
	}
	

}










