package de.dis2011;

import java.util.ArrayList;

import de.dis2011.data.Makler;
import de.dis2011.data.Person;
import de.dis2011.data.vertrag.Kaufvertrag;
import de.dis2011.data.vertrag.Mietvertrag;
import de.dis2011.data.vertrag.Vertrag;
import de.dis2011.data.immobilien.Wohnung;
import de.dis2011.data.immobilien.Haus;

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
			System.err.println("Sie wurden eingeloggt");
			
			while(true) {
				int response = maklerMenu.show();
				
				switch(response) {
					case NEW_IMMOBIL:
						showNewImmobile();
						break;
					case DELETE_IMMOBIL:
						showDeleteImmobile();
						break;
					case EDIT_IMMOBIL:
						showEditImmobile();
						break;
					case BACK:
						return;
				}
			}
		}else{
			System.err.println("leider falsches Passwort");
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
				maklerMenu.addEntry("Haus bearbeiten", EDIT_HAUS);
				maklerMenu.addEntry("Wohnung bearbeiten", EDIT_WOHNUNG);
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
				maklerMenu.addEntry("Haus Löschen", DELETE_HAUS);
				maklerMenu.addEntry("Wohnung Löschen", DELETE_WOHNUNG);
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
		boolean result = false;
		String eingabe_login = FormUtil.readString("Bitte login eingeben");
		String eingabe_password = FormUtil.readString("Bitte password eingeben");
		
		Makler m = Makler.load_from_login(eingabe_login);

		
		String user_pass = m.getPassword();
		
		//System.err.println(user_pass + "-" + eingabe_password);
		
		if( user_pass.equals(eingabe_password) ) {
			result = true;
		}else{
			result = false;
		}
		return result;

	}
	
	public static void newHaus() {		
		Haus w = new Haus();
		

		w.setId_makler(FormUtil.readInt("Markler ID"));
		w.setOrt(FormUtil.readString("ort"));
		w.setPlz(FormUtil.readString("plz"));
		w.setStrasse(FormUtil.readString("strasse"));
		w.setHaus_nr(FormUtil.readString("Haus Nr"));
		w.setFlaeche(FormUtil.readInt("Fläche"));
		w.setStockwerk(FormUtil.readInt("Stockwerk"));
		w.setKaufpreis(FormUtil.readInt("kaufpreis"));
		w.setGarten(FormUtil.readInt("garten"));

		
		w.save();
		
		System.out.println("Haus mit der ID "+w.getId()+" wurde erzeugt.");
		//System.err.println("noch nicht vorhanden");
	}
	
	public static void newWohnung() {
		Wohnung w = new Wohnung();
		

		w.setId_makler(FormUtil.readInt("Markler ID"));
		w.setOrt(FormUtil.readString("ort"));
		w.setPlz(FormUtil.readString("plz"));
		w.setStrasse(FormUtil.readString("strasse"));
		w.setHaus_nr(FormUtil.readString("Haus Nr"));
		w.setFlaeche(FormUtil.readInt("Fläche"));
		w.setStockwerk(FormUtil.readInt("Stockwerk"));
		w.setMietpreis(FormUtil.readInt("Mietpreis"));
		w.setZimmer(FormUtil.readInt("Zimmer"));
		w.setBalkon(FormUtil.readInt("Balkon"));
		w.setEbk(FormUtil.readInt("EBK"));

		
		w.save();
		
		System.out.println("Wohnung mit der ID "+w.getId()+" wurde erzeugt.");
		//System.err.println("noch nicht vorhanden");
	}
	
	public static void editHaus() {		
		Haus haus_old = new Haus();
		//Wohnung wohnung_new = new Wohnung();
		
		System.out.println("Welches Haus wollen Sie bearbeiten?");
		
		// läd eine Liste aller AMkler
		ArrayList<Haus> haus_alle = Haus.load_all_haus();
		
		// Liste der Makler ausgeben
		System.out.println("[0] Abbrechen");
		for(Haus haus_single : haus_alle){
			System.out.println("[" + haus_single.getId() + "] " + haus_single.getStrasse() + " " + haus_single.getHaus_nr());
		}
		
		int wahl = FormUtil.readInt("Nr.");
		
		if(wahl != 0){
			haus_old = Haus.load(wahl);

			haus_old.setId(haus_old.getId());
			
			System.out.println("Geben Sie den neuen Makler an:" + haus_old.getId_makler());
			haus_old.setId_makler(FormUtil.readInt("Makler ID"));
			System.out.println("Geben Sie den neuen Ort an:" + haus_old.getOrt());
			haus_old.setOrt(FormUtil.readString("Ort"));
			System.out.println("Geben Sie die neue PLZ an:" + haus_old.getPlz());
			haus_old.setPlz(FormUtil.readString("PLZ"));
			System.out.println("Geben Sie die neue Strasse an:" + haus_old.getStrasse());
			haus_old.setStrasse(FormUtil.readString("Strasse"));
			System.out.println("Geben Sie die neue HausNr an:" + haus_old.getHaus_nr());
			haus_old.setHaus_nr(FormUtil.readString("HausNr"));
			System.out.println("Geben Sie deie neue Fläche an:" + haus_old.getFlaeche());
			haus_old.setFlaeche(FormUtil.readInt("Fläche"));
			System.out.println("Geben Sie das neue Stockwerkn:" + haus_old.getStockwerk());
			haus_old.setStockwerk(FormUtil.readInt("Stockwerk"));
			System.out.println("Geben Sie den neuen Kaufpreis an:" + haus_old.getKaufpreis());
			haus_old.setKaufpreis(FormUtil.readInt("Kaufpreis"));
			System.out.println("Geben Sie die neuen Zimmer an:" + haus_old.getGarten());
			haus_old.setGarten(FormUtil.readInt("Zimmer"));

			/**
			 * ToDo
			 */
			//System.err.println(wohnung_old.getId() + "-" +wohnung_old.getIdWohnung());
			
			haus_old.save();
			
			System.out.println("Haus mit der ID "+haus_old.getId()+" wurde geupdatet.");
		}else {
			System.out.println("Keine Haus wurde bearbeitet");
		}
		//System.err.println("noch nicht vorhanden");
	}
	
	public static void editWohnung() {
		Wohnung wohnung_old = new Wohnung();
		//Wohnung wohnung_new = new Wohnung();
		
		System.out.println("Welche Wohnung wollen Sie bearbeiten?");
		
		// läd eine Liste aller AMkler
		ArrayList<Wohnung> wohnung_alle = Wohnung.load_all_wohnungen();
		
		// Liste der Makler ausgeben
		System.out.println("[0] Abbrechen");
		for(Wohnung wohnung_single : wohnung_alle){
			System.out.println("[" + wohnung_single.getId() + "] " + wohnung_single.getStrasse() + " " + wohnung_single.getHaus_nr());
		}
		
		int wahl = FormUtil.readInt("Nr.");
		
		if(wahl != 0){
			wohnung_old = Wohnung.load(wahl);

			wohnung_old.setId(wohnung_old.getId());
			
			System.out.println("Geben Sie den neuen Makler an:" + wohnung_old.getId_makler());
			wohnung_old.setId_makler(FormUtil.readInt("Makler ID"));
			System.out.println("Geben Sie den neuen Ort an:" + wohnung_old.getOrt());
			wohnung_old.setOrt(FormUtil.readString("Ort"));
			System.out.println("Geben Sie die neue PLZ an:" + wohnung_old.getPlz());
			wohnung_old.setPlz(FormUtil.readString("PLZ"));
			System.out.println("Geben Sie die neue Strasse an:" + wohnung_old.getStrasse());
			wohnung_old.setStrasse(FormUtil.readString("Strasse"));
			System.out.println("Geben Sie die neue HausNr an:" + wohnung_old.getHaus_nr());
			wohnung_old.setHaus_nr(FormUtil.readString("HausNr"));
			System.out.println("Geben Sie deie neue Fläche an:" + wohnung_old.getFlaeche());
			wohnung_old.setFlaeche(FormUtil.readInt("Fläche"));
			System.out.println("Geben Sie das neue Stockwerkn:" + wohnung_old.getStockwerk());
			wohnung_old.setStockwerk(FormUtil.readInt("Stockwerk"));
			System.out.println("Geben Sie den neuen Mietpreis an:" + wohnung_old.getMietpreis());
			wohnung_old.setMietpreis(FormUtil.readInt("Mietpreis"));
			System.out.println("Geben Sie die neuen Zimmer an:" + wohnung_old.getZimmer());
			wohnung_old.setZimmer(FormUtil.readInt("Zimmer"));
			System.out.println("Geben Sie den neuen Balkon an:" + wohnung_old.getBalkon());
			wohnung_old.setBalkon(FormUtil.readInt("Balkon"));
			System.out.println("Geben Sie den neuen EBK an:" + wohnung_old.getEbk());
			wohnung_old.setEbk(FormUtil.readInt("EBK"));

			/**
			 * ToDo
			 */
			//System.err.println(wohnung_old.getId() + "-" +wohnung_old.getIdWohnung());
			
			wohnung_old.save();
			
			System.out.println("Wohnung mit der ID "+wohnung_old.getId()+" wurde geupdatet.");
		}else {
			System.out.println("Keine Wohnung wurde bearbeitet");
		}
		//System.err.println("noch nicht vorhanden");
	}
	
	public static void deleteHaus() {		
		System.out.println("Welches Haus wollen Sie löschen?");
		
		// läd eine Liste aller AMkler
		ArrayList<Haus> haus_alle = Haus.load_all_haus();
		
		// Liste der Makler ausgeben
		System.out.println("[0] Abbrechen");
		for(Haus haus_single : haus_alle){
			System.out.println("[" + haus_single.getId() + "] " + haus_single.getStrasse() + " " + haus_single.getHaus_nr());
		}
		
		int wahl = FormUtil.readInt("Nr.");
		
		Haus.delete(wahl);
	}
	
	public static void deleteWohnung() {
		System.out.println("Welche Wohnung wollen Sie löschen?");
		
		// läd eine Liste aller AMkler
		ArrayList<Wohnung> wohnung_alle = Wohnung.load_all_wohnungen();
		
		// Liste der Makler ausgeben
		System.out.println("[0] Abbrechen");
		for(Wohnung wohnung_single : wohnung_alle){
			System.out.println("[" + wohnung_single.getId() + "] " + wohnung_single.getStrasse() + " " + wohnung_single.getHaus_nr());
		}
		
		int wahl = FormUtil.readInt("Nr.");
		
		Wohnung.delete(wahl);
	}
	

}










