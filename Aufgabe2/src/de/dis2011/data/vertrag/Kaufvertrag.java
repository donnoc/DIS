package de.dis2011.data.vertrag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import de.dis2011.data.DB2ConnectionManager;

public class Kaufvertrag extends Vertrag{
	private int id = -1;
	private int id_person;
	private int id_haus;
	private int anzahl_raten;
	private int ratenzins;
	

	public int getIdKaufvertrag() {
		return id;
	}
	
	public void setIdKaufvertrag(int id) {
		this.id = id;
	}
		
	public int getId_person() {
		return id_person;
	}
	
	public void setId_person(int id_person) {
		this.id_person = id_person;
	}
	
	public int getId_haus() {
		return id_haus;
	}
	
	public void setId_haus(int id_haus) {
		this.id_haus = id_haus;
	}
	
	public int getAnzahl_raten() {
		return anzahl_raten;
	}
	
	public void setAnzahl_raten(int anzahl_raten) {
		this.anzahl_raten = anzahl_raten;
	}
	
	public int getRatenzins() {
		return ratenzins;
	}
	
	public void setRatenzins(int ratenzins) {
		this.ratenzins = ratenzins;
	}
	
	/**
	 * Speichert den Kaufvertrag
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getIdKaufvertrag() == -1) {
				
				/*
				 * Speichere den Vertrag
				 */			
				this.save_vertrag();
				
				
				/*
				 * Speichere den KAufvertrag
				 */
				String insertSQL_kaufvertrag = "INSERT INTO kaufvertrag(id_person, id_haus, id_vertrag, anzahl_raten, ratenzins) VALUES (?, ?, ?, ?, ?)";
				
				PreparedStatement pstmt_kaufvertrag = con.prepareStatement(insertSQL_kaufvertrag,
						Statement.RETURN_GENERATED_KEYS);

				pstmt_kaufvertrag.setInt(1, getId_person());
				pstmt_kaufvertrag.setInt(2, getId_haus());
				pstmt_kaufvertrag.setInt(3, getId());
				pstmt_kaufvertrag.setInt(4, getAnzahl_raten());
				pstmt_kaufvertrag.setInt(5, getRatenzins());
				pstmt_kaufvertrag.executeUpdate();

				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs_kaufvertrag = pstmt_kaufvertrag.getGeneratedKeys();
				if (rs_kaufvertrag.next()) {
					setIdKaufvertrag(rs_kaufvertrag.getInt(1));
				}
				
				rs_kaufvertrag.close();
				pstmt_kaufvertrag.close();
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				
				/*
				 * Update ist noch nicht vorhanden
				 */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
