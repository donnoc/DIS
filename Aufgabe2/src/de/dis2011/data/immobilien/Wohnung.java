package de.dis2011.data.immobilien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.dis2011.data.DB2ConnectionManager;


public class Wohnung extends Immobilie{
	private int id = -1;
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
	 * L�d eine Liste aller Makler aus der Datenbank
	 * @return Makler-Array
	 */
	public static ArrayList<Wohnung> load_all_wohnungen() {
		try	{
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			
			// Erzeuge Anfrage
			String selectSQL = "SELECT id, id_immobilien, stockwerk, mietpreis, zimmer, balkon, ebk FROM wohnung";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			
			// F�hre Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			
			// Liste f�r alle Vertr�ge (mit id)
			ArrayList<Wohnung> all_wohnungen = new ArrayList<Wohnung>();
			
			while(rs.next()){
				Wohnung ts = new Wohnung();
				ts.setIdWohnung(rs.getInt("id"));
				ts.setId(rs.getInt("id_immobilien"));
				ts.setMietpreis(rs.getInt("mietpreis"));
				ts.setStockwerk(rs.getInt("stockwerk"));
				//ts.setPlz(rs.getString("mietpreis"));
				ts.setStrasse(rs.getString("zimmer"));
				ts.setHaus_nr(rs.getString("balkon"));
				ts.setFlaeche(rs.getInt("ebk"));
				
				all_wohnungen.add(ts);

			}
			
			rs.close();
			pstmt.close();
			return all_wohnungen;

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
	public static Wohnung load(int id) {
		try {
			Wohnung ts = loadImmobilie(id);

			
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM wohnung WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// F�hre Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				
				ts.setIdWohnung(rs.getInt("id"));
				ts.setId(rs.getInt("id_immobilien"));
				ts.setStockwerk(rs.getInt("stockwerk"));
				ts.setMietpreis(rs.getInt("mietpreis"));
				ts.setZimmer(rs.getInt("zimmer"));
				ts.setBalkon(rs.getInt("balkon"));
				ts.setEbk(rs.getInt("ebk"));

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
			if (getIdWohnung() == -1) {
				
				/*
				 * Speichere den Vertrag
				 */			
					
				super.saveImmobilie();
				
				
				/*
				 * Speichere den KAufvertrag
				 */
				String insertSQL = "INSERT INTO wohnung(id_immobilien, stockwerk, mietpreis, zimmer, balkon, ebk) VALUES (?, ?, ?, ?, ?, ?)";
				
				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				pstmt.setInt(1, getId());
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
				/*
				 * Speichere den Vertrag
				 */			
					
				saveImmobilie();
				
				
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE wohnung SET id_immobilien = ?, stockwerk = ?, mietpreis = ?, zimmer = ?, balkon = ?, ebk = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setInt(1, getId());
				pstmt.setInt(2, getStockwerk());
				pstmt.setInt(3, getMietpreis());
				pstmt.setInt(4, getZimmer());
				pstmt.setInt(5, getBalkon());
				pstmt.setInt(6, getEbk());
				pstmt.setInt(7, getIdWohnung());
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
