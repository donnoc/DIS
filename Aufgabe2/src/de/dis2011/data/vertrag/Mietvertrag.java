package de.dis2011.data.vertrag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import de.dis2011.data.DB2ConnectionManager;

public class Mietvertrag extends Vertrag{
	private int id = -1;
	private int id_person;
	private int id_wohnung;
	private int mietbegin;
	private int dauer;
	private int nebenkosten;
	

	public int getIdMietvertrag() {
		return id;
	}
	
	public void setIdMietvertrag(int id) {
		this.id = id;
	}
		
	public int getId_person() {
		return id_person;
	}
	
	public void setId_person(int id_person) {
		this.id_person = id_person;
	}
	
	public int getId_wohnung() {
		return id_wohnung;
	}
	
	public void setId_wohnung(int id_wohnung) {
		this.id_wohnung = id_wohnung;
	}
	
	public int getMietbegin() {
		return mietbegin;
	}
	
	public void setMietbegin(int mietbegin) {
		this.mietbegin = mietbegin;
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
	
	/**
	 * Speichert den Mietvertrag
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getIdMietvertrag() == -1) {
				
				/*
				 * Speichere den Vertrag
				 */
				this.save_vertrag();
				
				/*
				 * Speichere den mietvertrag
				 */
				String insertSQL_mietvertrag = "INSERT INTO mietvertrag(id_person, id_wohnung, id_vertrag, mietbegin, dauer, nebenkosten) VALUES (?, ?, ?, ?, ?, ?)";
				
				PreparedStatement pstmt_mietvertrag = con.prepareStatement(insertSQL_mietvertrag,
						Statement.RETURN_GENERATED_KEYS);
				
				//System.out.println("5 - bis hier hin funktioniert es");
				//System.out.println(getId_person()+"-"+getId_wohnung()+"-"+getId()+"-"+getMietbegin()+"-"+getMietbegin()+"-"+getDauer()+"-"+getNebenkosten());
				
				pstmt_mietvertrag.setInt(1, getId_person());
				pstmt_mietvertrag.setInt(2, getId_wohnung());
				pstmt_mietvertrag.setInt(3, getId());
				pstmt_mietvertrag.setInt(4, getMietbegin());
				pstmt_mietvertrag.setInt(5, getDauer());
				pstmt_mietvertrag.setInt(6, getNebenkosten());
				pstmt_mietvertrag.executeUpdate();

				//System.out.println("6 - bis hier hin funktioniert es");
				
				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs_mietvertrag = pstmt_mietvertrag.getGeneratedKeys();
				if (rs_mietvertrag.next()) {
					setIdMietvertrag(rs_mietvertrag.getInt(1));
				}
				
				rs_mietvertrag.close();
				pstmt_mietvertrag.close();
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
