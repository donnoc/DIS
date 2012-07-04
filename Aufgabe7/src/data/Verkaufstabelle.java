package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Verkaufstabelle {
	private int _id;
	private int _shopid = 0;
	private int _artikelid = 0;
	private java.sql.Date _zeit;
	private int _anzahl;
	private float _umsatz;

	public void set_shopid(String name) {
		int id = 0;
		try {
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM Shop WHERE Name = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt("shopid");
			}
			rs.close();
            pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this._shopid = id;
	}

	public void set_artikelid(String name) {
		int id = 0;
		try {
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			String selectSQL = "SELECT * FROM Article WHERE Name = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt("articleid");
			}
			rs.close();
            pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this._artikelid = id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_id() {
		return _id;
	}

	public void set_shopid(int _shopid) {
		this._shopid = _shopid;
	}

	public int get_shopid() {
		return _shopid;
	}

	public void set_artikelid(int _artikelid) {
		this._artikelid = _artikelid;
	}

	public int get_artikelid() {
		return _artikelid;
	}

	public void set_zeit(java.sql.Date _zeit) {
		this._zeit = _zeit;
	}

	public java.sql.Date get_zeit() {
		return _zeit;
	}

	public void set_anzahl(int _anzahl) {
		this._anzahl = _anzahl;
	}

	public int get_anzahl() {
		return _anzahl;
	}

	public void set_umsatz(float _umsatz) {
		this._umsatz = _umsatz;
	}

	public float get_umsatz() {
		return _umsatz;
	}

	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit spaeter generierte IDs zurC<ckgeliefert werden!
			// ID SHOPID ARTIKELID ZEIT ANZAHL UMSATZ
			String insertSQL = "INSERT INTO VSISP72.verkaufstabelle(SHOPID, ARTIKELID, ZEIT, ANZAHL, UMSATZ) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und fuehre Anfrage aus
			pstmt.setInt(1, this.get_shopid());
			pstmt.setInt(2, this.get_artikelid());
			pstmt.setDate(3, this.get_zeit());
			pstmt.setInt(4, this.get_anzahl());
			pstmt.setFloat(5, this.get_umsatz());
			pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
