package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.dis2011.data.DB2ConnectionManager;

public class Vertrag {
	private int id = -1;
	private String vertragsnr;
	private String datum;
	private String ort;
	

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getVertragsNr() {
		return vertragsnr;
	}
	
	public void setVertragsnummer(String vertragsnr) {
		this.vertragsnr = vertragsnr;
	}
	
	public String getDatum() {
		return datum;
	}
	
	public void setDatum(String datum) {
		this.datum = datum;
	}
	
	public String getOrt() {
		return ort;
	}
	
	public void setOrt(String ort) {
		this.ort = ort;
	}
	
	/**
	 * L�d eine Liste aller Makler aus der Datenbank
	 * @return Makler-Array
	 */
	public static ArrayList<Vertrag> load_all_vertraege() {
		try	{
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			
			// Erzeuge Anfrage
			String selectSQL = "SELECT id, vertragsnr FROM vertrag";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			
			// F�hre Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			
			// Liste f�r alle Vertr�ge (mit id)
			ArrayList<Vertrag> all_vertraege = new ArrayList<Vertrag>();
			
			while(rs.next()){
				Vertrag ts = new Vertrag();
				ts.setId(rs.getInt("id"));
				ts.setVertragsnummer(rs.getString("vertragsnr"));
				
				all_vertraege.add(ts);

			}
			
			rs.close();
			pstmt.close();
			return all_vertraege;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
