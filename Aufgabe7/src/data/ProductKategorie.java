package data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductKategorie {

	private int _productcategorieid;
	private String _name;

	
	public int get_productcategorieid() {
		return _productcategorieid;
	}

	public void set_productcategorieid(int _productcategorieid) {
		this._productcategorieid = _productcategorieid;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}


	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit spaeter generierte IDs zurC<ckgeliefert werden!
			String insertSQL = "INSERT INTO VSISP72.PRODUCTCATEGORY(NAME) VALUES (?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und fC<hre Anfrage aus
			pstmt.setString(1, get_name());
			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
