package de.dis2011.data.immobilien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.dis2011.data.DB2ConnectionManager;


public class Haus extends Immobilie{
	private int id = -1;
	private int stockwerk;
	private int kaufpreis;
	private int garten;
	
	public int getIdHaus(){
		return id;
	}
	
	public void setIdHaus(int id){
		this.id = id;
	}

	public int getStockwerk(){
		return stockwerk;
	}
	
	public void setStockwerk(int stockwerk){
		this.stockwerk = stockwerk;
	}
	
	public int getKaufpreis(){
		return kaufpreis;
	}
	
	public void setKaufpreis(int kaufpreis){
		this.kaufpreis = kaufpreis;
	}
	
	public int getGarten(){
		return garten;
	}
	
	public void setGarten(int garten){
		this.garten = garten;
	}
	
	
	/**
	 * L�d eine Liste aller Makler aus der Datenbank
	 * @return Makler-Array
	 */
	public static ArrayList<Haus> load_all_haus() {
		try	{
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			
			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM immobilien_haus";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			
			// F�hre Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			
			// Liste f�r alle Vertr�ge (mit id)
			ArrayList<Haus> all_haus = new ArrayList<Haus>();
			
			while(rs.next()){
				Haus ts = new Haus();
				ts.setId(rs.getInt("id_immobilien"));
				ts.setId_makler(rs.getInt("id_makler"));
				ts.setOrt(rs.getString("ort"));
				ts.setPlz(rs.getString("plz"));
				ts.setStrasse(rs.getString("strasse"));
				ts.setHaus_nr(rs.getString("haus_nr"));
				ts.setFlaeche(rs.getInt("flaeche"));
				ts.setStockwerk(rs.getInt("stockwerk"));
				ts.setKaufpreis(rs.getInt("kaufpreis"));
				ts.setGarten(rs.getInt("garten"));
				
				all_haus.add(ts);

			}
			
			rs.close();
			pstmt.close();
			return all_haus;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * L�dt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static Haus load(int id) {
		try {
			//Haus ts = loadImmobilie(id);
			Haus ts = new Haus();
			
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM immobilien_haus WHERE id_immobilien = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// F�hre Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				
				ts.setId(rs.getInt("id_immobilien"));
				ts.setId_makler(rs.getInt("id_makler"));
				ts.setOrt(rs.getString("ort"));
				ts.setPlz(rs.getString("plz"));
				ts.setStrasse(rs.getString("strasse"));
				ts.setHaus_nr(rs.getString("haus_nr"));
				ts.setFlaeche(rs.getInt("flaeche"));
				ts.setStockwerk(rs.getInt("stockwerk"));
				ts.setKaufpreis(rs.getInt("kaufpreis"));
				ts.setGarten(rs.getInt("garten"));

				rs.close();
				pstmt.close();
				return ts;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * Speichert die Wohnung
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getIdHaus() == -1) {
				
				/*
				 * Speichere den Vertrag
				 */			
					
				super.saveImmobilie();
				
				
				/*
				 * Speichere den KAufvertrag
				 */
				String insertSQL = "INSERT INTO haus(id_immobilien, stockwerk, kaufpreis, garten) VALUES (?, ?, ?, ?)";
				
				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmt.setInt(1, getId());
				pstmt.setInt(2, getStockwerk());
				pstmt.setInt(3, getKaufpreis());
				pstmt.setInt(4, getGarten());
				pstmt.executeUpdate();

				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs_kaufvertrag = pstmt.getGeneratedKeys();
				if (rs_kaufvertrag.next()) {
					setIdHaus(rs_kaufvertrag.getInt(1));
				}
				
				rs_kaufvertrag.close();
				pstmt.close();
			} else {
				/*
				 * Speichere den Vertrag
				 */			
					
				saveImmobilie();
				
				
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE haus SET id_immobilien = ?, stockwerk = ?, kaufpreis = ?, garten = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setInt(1, getId());
				pstmt.setInt(2, getStockwerk());
				pstmt.setInt(3, getKaufpreis());
				pstmt.setInt(4, getGarten());
				pstmt.setInt(7, getIdHaus());
				pstmt.executeUpdate();
				
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * L�scht einen Makler
	 * @param int die ID einer Maklers
	 */
	public static void delete(int id) {
		
		Immobilie.deleteImmobilie(id);

	}
	
	
	
}
