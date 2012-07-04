package data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ProduktGruppe {

	private int _productgroupid;
	private int _productfamilyid;
	private String _name;

	public int get_productgroupid() {
		return _productgroupid;
	}

	public void set_productgroupid(int _productgroupid) {
		this._productgroupid = _productgroupid;
	}
	
	public int get_productfamilyid() {
		return _productfamilyid;
	}

	public void set_productfamilyid(int _productfamilyid) {
		this._productfamilyid = _productfamilyid;
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
			String insertSQL = "INSERT INTO VSISP72.PRODUCTGROUP(PRODUCTFAMILYID, NAME) VALUES (?, ?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und fC<hre Anfrage aus
			pstmt.setInt(1, get_productfamilyid());
			pstmt.setString(2, get_name());
			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
