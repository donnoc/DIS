package data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Region {

	private int _regionid;
	private int _landid;
	private String _name;


	public int get_regionid() {
		return _regionid;
	}

	public void set_regionid(int _regionid) {
		this._regionid = _regionid;
	}

	public int get_landid() {
		return _landid;
	}

	public void set_landid(int _landid) {
		this._landid = _landid;
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
			String insertSQL = "INSERT INTO VSISP72.REGION(LANDID, NAME) VALUES ( ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und fC<hre Anfrage aus
			pstmt.setInt(1, get_landid());
			pstmt.setString(2, get_name());
			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
