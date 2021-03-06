package de.dis2011.data.vertrag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.Statement;
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
			String selectSQL = "SELECT id, vertragsnr, datum, ort FROM vertrag";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			
			// F�hre Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			
			// Liste f�r alle Vertr�ge (mit id)
			ArrayList<Vertrag> all_vertraege = new ArrayList<Vertrag>();
			
			while(rs.next()){
				Vertrag ts = new Vertrag();
				ts.setId(rs.getInt("id"));
				ts.setVertragsnummer(rs.getString("vertragsnr"));
				ts.setDatum(rs.getString("datum"));
				ts.setOrt(rs.getString("ort"));
				
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

	public void save_vertrag(){
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getId() == -1) {
				
				/*
				 * Speichere den Vertrag
				 */
				String insertSQL_vertrag = "INSERT INTO vertrag(vertragsnr, datum, ort) VALUES (?, ?, ?)";
				
				//System.out.println("1 - bis hier hin funktioniert es");
					PreparedStatement pstmt_vertrag = con.prepareStatement(insertSQL_vertrag,
							Statement.RETURN_GENERATED_KEYS);
						
				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt_vertrag.setString(1, getVertragsNr());
				pstmt_vertrag.setString(2, getDatum());
				pstmt_vertrag.setString(3, getOrt());
				pstmt_vertrag.executeUpdate();
						
				//System.out.println("2 - bis hier hin funktioniert es");
						
				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs_vertrag = pstmt_vertrag.getGeneratedKeys();
				if (rs_vertrag.next()) {
					setId(rs_vertrag.getInt(1));
				}
						
				//System.out.println("3 - bis hier hin funktioniert es");
						
				rs_vertrag.close();
				pstmt_vertrag.close();
						
				//System.out.println("4 - bis hier hin funktioniert es");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
