package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Kaufvertrag extends Vertrag 
{
	private int anzahlRaten;
	private int ratenzins;
	private int hausID;
	private int kaeuferID;
	
	@Override
	public void add()
	{
        try
        {
            super.add();
   		 	// Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            
            String insertSQL = "INSERT INTO Kaufvertrag(id, anzahlraten, ratenzins, haus, kaeufer) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(insertSQL);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            // pstmt.setInt(1, getId());
            pstmt.setInt(1, getId());
            pstmt.setInt(2, getAnzahlRaten());
            pstmt.setInt(3, getRatenzins());
            pstmt.setInt(4, getHausID());
            pstmt.setInt(5, getKaeuferID());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
	}
	
	public int getAnzahlRaten() {
		return anzahlRaten;
	}
	public void setAnzahlRaten(int anzahlRaten) {
		this.anzahlRaten = anzahlRaten;
	}
	public int getRatenzins() {
		return ratenzins;
	}
	public void setRatenzins(int ratenzins) {
		this.ratenzins = ratenzins;
	}
	public int getHausID() {
		return hausID;
	}
	public void setHausID(int hausID) {
		this.hausID = hausID;
	}
	public int getKaeuferID() {
		return kaeuferID;
	}
	public void setKaeuferID(int kaeuferID) {
		this.kaeuferID = kaeuferID;
	}

	public static void listAll() {

        try
        {
   		 	// Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            
            String query = "SELECT kv.id, " +
            				"kv.anzahlraten, " +
            				"kv.ratenzins, " +
            				"kv.haus," +
            				"kv.kaeufer," +
            				"v.vertragsnr, v.datum, v.ort " +
            				"FROM kaufvertrag kv " +
            				"INNER JOIN vertrag v ON kv.id = v.id;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("Kaufvertraege:");
            System.out.println("ID | Anzahl Raten | Ratenzins | HausID | KaeuferID | Wohnung | MieterID | Vertragsnummer | Datum | Ort");
            while(rs.next())
            {
            	StringBuilder sb = new StringBuilder();
            	sb.append(rs.getInt(1)).append(" | ")
            	  .append(rs.getDate(2)).append(" | ")
            	  .append(rs.getInt(3)).append(" | ")
            	  .append(rs.getInt(4)).append(" | ")
            	  .append(rs.getInt(5)).append(" | ")
            	  .append(rs.getInt(6)).append(" | ")
            	  .append(rs.getDate(7)).append(" | ")
            	  .append(rs.getString(8));
            	System.out.println(sb.toString());
            }
            System.out.println();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
	}
	
}
