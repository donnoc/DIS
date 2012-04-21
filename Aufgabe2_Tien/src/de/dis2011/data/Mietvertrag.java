package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Mietvertrag extends Vertrag 
{
	private Date mietbeginn;
	private int dauer;
	private int nebenkosten;
	private int wohnungID;
	private int mieterID;
	
	@Override
	public void add()
	{
        try
        {
            super.add();
   		 	// Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            
            String insertSQL = "INSERT INTO Mietvertrag(id, mietbeginn, dauer, nebenkosten, wohnung, mieter) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(insertSQL);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            // pstmt.setInt(1, getId());
            pstmt.setInt(1, getId());
            pstmt.setDate(2, new java.sql.Date(getMietbeginn().getTime()));
            pstmt.setInt(3, getDauer());
            pstmt.setInt(4, getNebenkosten());
            pstmt.setInt(5, getWohnungID());
            pstmt.setInt(6, getMieterID());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
	}

	public static void listAll() {

        try
        {
   		 	// Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            
            String query = "SELECT mv.id, mv.mietbeginn, mv.dauer, mv.nebenkosten, mv.wohnung, mv.mieter, v.vertragsnr, v.datum, v.ort " +
            				"FROM Mietvertrag AS mv " +
            				"INNER JOIN Vertrag AS v ON mv.id = v.id;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("Mietvertraege:");
            System.out.println("ID | Mietbeginn | Dauer | Nebenkosten | Wohnung | MieterID | Vertragsnummer | Datum | Ort");
            while(rs.next())
            {
            	StringBuilder sb = new StringBuilder();
            	sb.append(rs.getInt(1)).append(" | ")
            	  .append(rs.getDate(2)).append(" | ")
            	  .append(rs.getInt(3)).append(" | ")
            	  .append(rs.getInt(4)).append(" | ")
            	  .append(rs.getInt(5)).append(" | ")
            	  .append(rs.getInt(6)).append(" | ")
            	  .append(rs.getInt(7)).append(" | ")
            	  .append(rs.getDate(8)).append(" | ")
            	  .append(rs.getString(9));
            	System.out.println(sb.toString());
            }
            System.out.println();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
	}
	
	public Date getMietbeginn() {
		return mietbeginn;
	}
	public void setMietbeginn(Date mietbeginn) {
		this.mietbeginn = mietbeginn;
	}
	public int getDauer() {
		return dauer;
	}
	public void setDauer(int dauer) {
		this.dauer = dauer;
	}
	public int getNebenkosten() {
		return nebenkosten;
	}
	public void setNebenkosten(int nebenkosten) {
		this.nebenkosten = nebenkosten;
	}
	public int getWohnungID() {
		return wohnungID;
	}
	public void setWohnungID(int wohnungID) {
		this.wohnungID = wohnungID;
	}
	public int getMieterID() {
		return mieterID;
	}
	public void setMieterID(int mieterID) {
		this.mieterID = mieterID;
	}

}
