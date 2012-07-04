package data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class Artikel {
	private int _articleid;
	private int _productgroupid;
	private String _name;
	private int _preis;


	public int get_articleid() {
		return _articleid;
	}


	public void set_articleid(int _articleid) {
		this._articleid = _articleid;
	}


	public int get_productgroupid() {
		return _productgroupid;
	}


	public void set_productgroupid(int _productgroupid) {
		this._productgroupid = _productgroupid;
	}


	public String get_name() {
		return _name;
	}


	public void set_name(String _name) {
		this._name = _name;
	}


	public int get_preis() {
		return _preis;
	}


	public void set_preis(int _preis) {
		this._preis = _preis;
	}


	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit spaeter generierte IDs zurC<ckgeliefert werden!
			String insertSQL = "INSERT INTO VSISP72.ARTICLE(PRODUCTGROUPID, NAME, PREIS) VALUES ( ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und fC<hre Anfrage aus
			pstmt.setInt(1, get_productgroupid());
			pstmt.setString(2, get_name());
			pstmt.setDouble(3, get_preis());
			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
