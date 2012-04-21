package de.dis2011;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import de.dis2011.data.Haus;
import de.dis2011.data.Immobilie;
import de.dis2011.data.Kaufvertrag;
import de.dis2011.data.Makler;
import de.dis2011.data.Mietvertrag;
import de.dis2011.data.Person;
import de.dis2011.data.Vertrag;
import de.dis2011.data.Wohnung;

/**
 * Hauptklasse
 */
public class Main
{
    /**
     * Startet die Anwendung
     */
    public static void main(String[] args)
    {
        showMainMenu();
    }

    /**
     * Zeigt das Hauptmenü
     */
    public static void showMainMenu()
    {
        // Menüoptionen
        final int MENU_MAKLER = 0;
        final int MENU_IMMOBILIE = 1;
        final int MENU_VERTRAG = 2;
        final int QUIT = 3;

        // Erzeuge Menü
        Menu mainMenu = new Menu("Hauptmenü");
        mainMenu.addEntry("Makler-Verwaltung", MENU_MAKLER);
        mainMenu.addEntry("Immobilien-Verwaltung", MENU_IMMOBILIE);
        mainMenu.addEntry("Vertrags-Verwaltung", MENU_VERTRAG);
        mainMenu.addEntry("Beenden", QUIT);

        // Verarbeite Eingabe
        while (true)
        {
            int response = mainMenu.show();

            switch (response)
            {
                case MENU_MAKLER:
                    showMaklerMenu();
                    break;
                case MENU_IMMOBILIE:
                    showImmobilieMenu();
                    break;
                case MENU_VERTRAG:
                    showVertragMenu();
                    break;
                case QUIT:
                    return;
            }
        }
    }

    /**
     * Zeigt die Maklerverwaltung
     */
    public static void showMaklerMenu()
    {
        // Menüoptionen
        final int NEW_MAKLER = 0;
        final int EDIT_MAKLER = 1;
        final int DEL_MAKLER = 2;
        final int BACK = 3;

        // Maklerverwaltungsmenü
        Menu maklerMenu = new Menu("Makler-Verwaltung");
        maklerMenu.addEntry("Neuer Makler", NEW_MAKLER);
        maklerMenu.addEntry("Makler bearbeiten", EDIT_MAKLER);
        maklerMenu.addEntry("Makler löschen", DEL_MAKLER);
        maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);

        // Verarbeite Eingabe
        while (true)
        {
            int response = maklerMenu.show();

            switch (response)
            {
                case NEW_MAKLER:
                    addMakler();
                    break;
                case EDIT_MAKLER:
                    editMakler();
                    break;
                case DEL_MAKLER:
                    delMakler();
                    break;
                case BACK:
                    return;
            }
        }
    }

    /**
     * 
     */
    public static void showImmobilieMenu()
    {
        try
        {
            String eingabe = leseLogin();
            Makler m = Makler.load(eingabe);
            if (login(m))
            {
                // Menüoptionen
                final int NEW_IMMO = 0;
                final int EDIT_IMMO = 1;
                final int DEL_IMMO = 2;
                final int BACK = 3;

                // Maklerverwaltungsmenü
                Menu immoMenu = new Menu("Immobilien-Verwaltung");
                immoMenu.addEntry("Neue Immobilie", NEW_IMMO);
                immoMenu.addEntry("Immobilie bearbeiten", EDIT_IMMO);
                immoMenu.addEntry("Immobilie löschen", DEL_IMMO);
                immoMenu.addEntry("Zurück zum Hauptmenü", BACK);

                // Verarbeite Eingabe
                while (true)
                {
                    int response = immoMenu.show();

                    switch (response)
                    {
                        case NEW_IMMO:
                            addImmo(m);
                            break;
                        case EDIT_IMMO:
                            editImmo(m);
                            break;
                        case DEL_IMMO:
                            delImmo(m);
                            break;
                        case BACK:
                            return;
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Fehler bei der Eingabe");

        }
    }
    
    public static void showVertragMenu()
    {
        try
        {
        	// Menüoptionen
            final int NEW_PERSON = 0;
            final int NEW_TREATY = 1;
            final int LIST_TREATIES = 2;
            final int BACK = 3;

            // Maklerverwaltungsmenü
            Menu immoMenu = new Menu("Vertragsverwaltung");
            immoMenu.addEntry("Neue Person anlegen", NEW_PERSON);
            immoMenu.addEntry("Vertrag abschliessen", NEW_TREATY);
            immoMenu.addEntry("Vetraege auflisten", LIST_TREATIES);
            immoMenu.addEntry("Zurück zum Hauptmenü", BACK);

            // Verarbeite Eingabe
            while (true)
            {
                int response = immoMenu.show();

                switch (response)
                {
                    case NEW_PERSON:
                    	addPerson();
                        break;
                    case NEW_TREATY:
                    	addVertrag();
                        break;
                    case LIST_TREATIES:
                    	listVertraege();
                        break;
                    case BACK:
                        return;
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Fehler bei der Einagbe");

        }
    }
    
    public static void addPerson()
    {
        try
        {
	    	Person p = new Person();
	    	
	    	p.setVorname(FormUtil.readString("Vorname"));
	    	p.setNachname(FormUtil.readString("Nachname"));
	    	p.setAdresse(FormUtil.readString("Adresse"));
	    	
	    	p.add();
	    	System.out.println("Person erfolgreich angelegt.");
        }
        catch(Exception e)
        {
        	System.err.println("Fehler bei der Eingabe");
        }
    }
    
    public static void addVertrag()
    {
        try
        {
	    	int choice = FormUtil.readInt("Mietvertrag (1) oder Kaufvertrag (2)?");
	    	switch(choice)
	    	{
	    		case 1:	    	
	    			addMietvertrag();
	    			break;
	    		case 2:
	    			addKaufvertrag();
	    			break;
    			default:
    				System.err.println("Ungültige Wahl!");
    				return;
	    	}
        }
        catch (Exception e)
        {
            System.err.println("Fehler bei der Eingabe");
        }
    }
    
    public static void addMietvertrag()
    {
    	try {
	    	Mietvertrag mv = new Mietvertrag();
	    	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    	
	    	addVertragsinfo(mv);
    		mv.setMieterID(FormUtil.readInt("Mieter ID"));
    		mv.setWohnungID(FormUtil.readInt("Wohnung ID"));
			mv.setMietbeginn(df.parse(FormUtil.readString("Mietbeginn (dd-mm-yyyy)")));
			mv.setDauer(FormUtil.readInt("Dauer"));
			mv.setNebenkosten(FormUtil.readInt("Nebenkosten"));
			
			mv.add();

			System.out.println("Vertrag angelegt!");
			
		} catch (ParseException e) {
			System.err.println("Fehler bei der Datumseingabe - Falsches Format!");
		}
    }
    
    public static void addKaufvertrag()
    {
    	try {
    		Kaufvertrag kv = new Kaufvertrag();
    	
	    	addVertragsinfo(kv);
    		kv.setKaeuferID(FormUtil.readInt("Käufer ID"));
    		kv.setHausID(FormUtil.readInt("Haus ID"));
    		kv.setAnzahlRaten(FormUtil.readInt("Anzahl der Raten"));
    		kv.setRatenzins(FormUtil.readInt("Ratenzins"));
    		
			kv.add();
			
			System.out.println("Vertrag angelegt!");
			
		} catch (ParseException e) {
			System.err.println("Fehler bei der Datumseingabe - Falsches Format!");
		}
    }
    
    public static void addVertragsinfo(Vertrag v) throws ParseException
    {
    	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    	
		v.setVertragsnummer(FormUtil.readInt("Vertragsnummer"));
		v.setDatum(df.parse(FormUtil.readString("Datum des Abschlusses (dd-mm-yyyy)")));
		v.setOrt(FormUtil.readString("Ort"));
    }
    
    public static void listVertraege()
    {
    	Mietvertrag.listAll();
    	Kaufvertrag.listAll();
    }
    

    public static String leseLogin()
    {
        String eingabe = null;
        try
        {
            eingabe = FormUtil.readString("Login");
        }
        catch (Exception e)
        {
            System.err.println("Fehler bei der Eingabe");
        }
        return eingabe;
    }

    public static boolean login(Makler m)
    {
        boolean rueckgabe = false;
        if (m.getPassword().equals(FormUtil.readString("Passwort")))
        {
            rueckgabe = true;
        }
        else
        {
            System.out.println("Falsches Passwort eingegeben!\n");
        }
        return rueckgabe;
    }

    /**
     * Legt einen neuen Makler an, nachdem der Benutzer die entprechenden Daten
     * eingegeben hat.
     */
    public static void addMakler()
    {
        Makler m = new Makler();

        m.setName(FormUtil.readString("Name"));
        m.setAddress(FormUtil.readString("Adresse"));
        m.setLogin(FormUtil.readString("Login"));
        m.setPassword(FormUtil.readString("Passwort"));
        m.add();

        // System.out.println("Makler mit der ID " + m.getId() +
        // " wurde erzeugt.");
    }

    /**
     * Makler editieren
     */
    public static void editMakler()
    {
        try
        {
            String eingabe = leseLogin();
            Makler m = Makler.load(eingabe);
            if (login(m))
            {
                m.setName(FormUtil.readString("Name"));
                m.setAddress(FormUtil.readString("Adresse"));
                m.setLogin(FormUtil.readString("Login"));
                m.setPassword(FormUtil.readString("Passwort"));
                m.edit();
            }
        }
        catch (Exception e)
        {
            System.err.println("Fehler bei der Eingabe");
        }
    }

    /**
     * Makler entfernen
     */
    public static void delMakler()
    {
        try
        {
            String eingabe = leseLogin();
            Makler m = Makler.load(eingabe);
            if (login(m))
            {
                m.delete();
            }
        }
        catch (Exception e)
        {
            System.err.println("Fehler bei der Einagbe");
        }
    }

    public static void addImmo(Makler m)
    {
        // Menüoptionen
        final int IMMO_HAUS = 0;
        final int IMMO_WOHNUNG = 1;
        final int BACK = 2;

        // Maklerverwaltungsmenü
        Menu addImmoMenu = new Menu("Immobilie hinzufügen");
        addImmoMenu.addEntry("Haus", IMMO_HAUS);
        addImmoMenu.addEntry("Wohnung", IMMO_WOHNUNG);
        addImmoMenu.addEntry("Zurück zum Hauptmenü", BACK);

        // Verarbeite Eingabe
        while (true)
        {
            int response = addImmoMenu.show();
            switch (response)
            {
                case IMMO_HAUS:
                    addHaus(m);
                    break;
                case IMMO_WOHNUNG:
                    addWohnung(m);
                    break;
                case BACK:
                    return;
            }
        }
    }

    public static void addHaus(Makler m)
    {
        Immobilie Immo = new Immobilie();
        Immo.setOrt(FormUtil.readString("Ort"));
        Immo.setPlz(FormUtil.readInt("PLZ"));
        Immo.setStrasse(FormUtil.readString("Straße"));
        Immo.setHausnr(FormUtil.readInt("Hausnr"));
        Immo.setFlaeche(FormUtil.readInt("Fläche"));
        Immo.setMakler(m.getId());
        Immo.add();

        Haus haus = new Haus();
        haus.setStockwerke(FormUtil.readInt("Stockwerke"));
        haus.setKaufpreis(FormUtil.readInt("Kaufpreis"));
        haus.setGarten(FormUtil.readInt("Garten"));
        haus.setId(Immo.getId());
        haus.add();
    }

    public static void addWohnung(Makler m)
    {
        Immobilie Immo = new Immobilie();
        Immo.setOrt(FormUtil.readString("Ort"));
        Immo.setPlz(FormUtil.readInt("PLZ"));
        Immo.setStrasse(FormUtil.readString("Straße"));
        Immo.setHausnr(FormUtil.readInt("Hausnr"));
        Immo.setFlaeche(FormUtil.readInt("Fläche"));
        Immo.setMakler(m.getId());
        Immo.add();

        Wohnung whg = new Wohnung();
        whg.setStockwerk(FormUtil.readInt("Stockwerk"));
        whg.setMietpreis(FormUtil.readInt("Mietpreis"));
        whg.setZimmer(FormUtil.readInt("Zimmer"));
        whg.setBalkon(FormUtil.readInt("Balkon"));
        whg.setEbk(FormUtil.readInt("EBK"));
        whg.setId(Immo.getId());
        whg.add();
    }

    /**
     * Editiert eine Vorhandene Immobilie
     * 
     * @param m
     */
    public static void editImmo(Makler m)
    {
        int welcheImmo = FormUtil.readInt("Welche Immobilie soll editiert werden?");
        try
        {
            Immobilie immo = Immobilie.load(welcheImmo);
            if (!immo.darfEdit(m))
            {
            	System.err.println("Keine Zugriffsrechte auf diese Immobilie.");
            	return;
            }
            // Haus haus = new Haus();
            // haus.load(immo.getId());
            immo.setOrt(FormUtil.readString("Ort"));
            immo.setPlz(FormUtil.readInt("PLZ"));
            immo.setStrasse(FormUtil.readString("Straße"));
            immo.setHausnr(FormUtil.readInt("Hausnr"));
            immo.setFlaeche(FormUtil.readInt("Fläche"));
            immo.edit();

            try
            {
                Haus haus = Haus.load(immo.getId());
                if (haus.getId() == immo.getId())
                {
                    haus.setStockwerke(FormUtil.readInt("Stockwerke"));
                    haus.setKaufpreis(FormUtil.readInt("Kaufpreis"));
                    haus.setGarten(FormUtil.readInt("Garten"));
                    haus.edit();
                }
            }
            catch (Exception e)
            {
            }
            try
            {
                Wohnung wohn = Wohnung.load(immo.getId());
                if (wohn.getId() == immo.getId())
                {
                    wohn.setStockwerk(FormUtil.readInt("Stockwerk"));
                    wohn.setMietpreis(FormUtil.readInt("Mietpreis"));
                    wohn.setZimmer(FormUtil.readInt("Zimmer"));
                    wohn.setBalkon(FormUtil.readInt("Balkon"));
                    wohn.setEbk(FormUtil.readInt("EBK"));
                    wohn.edit();
                }
            }
            catch (Exception e)
            {
            }
        }
        catch (Exception e)
        {
            System.err.println("Fehler beim Editieren");
        }
    }

    /**
     * löscht eine Immobilie, wenn er der Verwalter ist
     * 
     * @param m
     */
    public static void delImmo(Makler m)
    {
        int welcheImmo = FormUtil.readInt("Welche Immobilie soll gelöscht werden?");
        try
        {
            Immobilie immo = Immobilie.load(welcheImmo);
            if (immo.darfEdit(m))
            {
                immo.delete(welcheImmo);
                System.out.println("Erfolgreich gelöscht!");
            }
            else
            {
                System.out.println("Keine Erlaubnis diese Immobilie zu löschen oder Immobilie nicht vorhanden!");
            }

        }
        catch (Exception e)
        {
            System.err.println("Fehler bei der Eingabe");
        }

    }

}
