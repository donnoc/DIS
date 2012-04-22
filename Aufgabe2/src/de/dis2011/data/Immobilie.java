package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.Statement;
import java.util.ArrayList;

import de.dis2011.data.DB2ConnectionManager;

public class Immobilie {
	private int id = -1;
	private int id_makler;
	private String ort;
	private String plz;
	private String strasse;
	private String haus_nr;
	private int flaeche;
	

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId_makler() {
		return id_makler;
	}
	
	public void setId_makler(int id_makler) {
		this.id_makler = id_makler;
	}
	
	public String getOrt() {
		return ort;
	}
	
	public void setOrt(String ort) {
		this.ort = ort;
	}
	
	public String getPlz() {
		return plz;
	}
	
	public void setPlz(String plz) {
		this.plz = plz;
	}
	
	public String getStrasse() {
		return strasse;
	}
	
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	
	public String getHaus_nr() {
		return haus_nr;
	}
	
	public void setHaus_nr(String haus_nr) {
		this.haus_nr = haus_nr;
	}
	
	public int getFlaeche() {
		return flaeche;
	}
	
	public void setFlaeche(int flaeche) {
		this.flaeche = flaeche;
	}
	
	/**
	 * L�d eine Liste aller Makler aus der Datenbank
	 * @return Makler-Array
	 */
	public static ArrayList<Immobilie> load_all_immobilien() {
		try	{
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			
			// Erzeuge Anfrage
			String selectSQL = "SELECT id, strasse, haus_nr,  FROM immobilien";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			
			// F�hre Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			
			// Liste f�r alle Vertr�ge (mit id)
			ArrayList<Immobilie> all_immobilien = new ArrayList<Immobilie>();
			
			while(rs.next()){
				Immobilie ts = new Immobilie();
				ts.setId(rs.getInt("id"));
				ts.setId_makler(rs.getInt("id_makler"));
				ts.setOrt(rs.getString("ort"));
				ts.setPlz(rs.getString("plz"));
				ts.setStrasse(rs.getString("strasse"));
				ts.setHaus_nr(rs.getString("haus_nr"));
				ts.setFlaeche(rs.getInt("flaeche"));
				
				all_immobilien.add(ts);

			}
			
			rs.close();
			pstmt.close();
			return all_immobilien;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Speichert eine immobilie
	 */
	public void save_immobilie(){
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getId() == -1) {
				
				/*
				 * Speichere den Vertrag
				 */
				String insertSQL = "INSERT INTO immobilien(id_makler, ort, plz, strasse, haus_nr, flaeche) VALUES (?, ?, ?, ?, ?, ?)";
				
				//System.out.println("1 - bis hier hin funktioniert es");
					PreparedStatement pstmt = con.prepareStatement(insertSQL,
							Statement.RETURN_GENERATED_KEYS);
						
				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt.setInt(1, getId_makler());
				pstmt.setString(2, getOrt());
				pstmt.setString(3, getPlz());
				pstmt.setString(3, getStrasse());
				pstmt.setString(3, getHaus_nr());
				pstmt.setInt(3, getFlaeche());
				pstmt.executeUpdate();
						
				//System.out.println("2 - bis hier hin funktioniert es");
						
				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs_vertrag = pstmt.getGeneratedKeys();
				if (rs_vertrag.next()) {
					setId(rs_vertrag.getInt(1));
				}
						
				//System.out.println("3 - bis hier hin funktioniert es");
						
				rs_vertrag.close();
				pstmt.close();
						
				//System.out.println("4 - bis hier hin funktioniert es");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
