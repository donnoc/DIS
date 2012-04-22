package de.dis2011.data.immobilien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2011.data.DB2ConnectionManager;

public class Wohnung extends Immobilie{
	private int id = -1;
	private int id_immobilien;
	private int stockwerk;
	private int mietpreis;
	private int zimmer;
	private int balkon;
	private int ebk;
	
	public int getIdWohnung(){
		return id;
	}
	
	public void setIdWohnung(int id){
		this.id = id;
	}

	public int getId_immobilien(){
		return id_immobilien;
	}
	
	public void setId_immobilien(int id_immobilien){
		this.id_immobilien = id_immobilien;
	}
	
	public int getStockwerk(){
		return stockwerk;
	}
	
	public void setStockwerk(int stockwerk){
		this.stockwerk = stockwerk;
	}
	
	public int getMietpreis(){
		return mietpreis;
	}
	
	public void setMietpreis(int mietpreis){
		this.mietpreis = mietpreis;
	}
	
	public int getZimmer(){
		return zimmer;
	}
	
	public void setZimmer(int zimmer){
		this.zimmer = zimmer;
	}
	
	public int getBalkon(){
		return balkon;
	}
	
	public void setBalkon(int balkon){
		this.balkon = balkon;
	}
	
	public int getEbk(){
		return ebk;
	}
	
	public void setEbk(int ebk){
		this.ebk = ebk;
	}
	
	/**
	 * Speichert die Wohnung
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getIdWohnung() == -1) {
				
				/*
				 * Speichere den Vertrag
				 */			
				this.saveImmobilie();
				
				
				/*
				 * Speichere den KAufvertrag
				 */
				String insertSQL = "INSERT INTO wohnung(id_immobilien, stockwerk, mietpreis, zimmer, balkon, ebk) VALUES (?, ?, ?, ?, ?, ?)";
				
				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmt.setInt(1, getId_immobilien());
				pstmt.setInt(2, getStockwerk());
				pstmt.setInt(3, getMietpreis());
				pstmt.setInt(4, getZimmer());
				pstmt.setInt(5, getBalkon());
				pstmt.setInt(6, getEbk());
				pstmt.executeUpdate();

				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs_kaufvertrag = pstmt.getGeneratedKeys();
				if (rs_kaufvertrag.next()) {
					setIdWohnung(rs_kaufvertrag.getInt(1));
				}
				
				rs_kaufvertrag.close();
				pstmt.close();
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
