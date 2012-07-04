package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.DB2ConnectionManager;

public class Stadt {


		private int _stadtid;
		private int _regionid;
		private String _name;

		public int get_stadtid() {
			return _stadtid;
		}

		public void set_stadtid(int _stadtid) {
			this._stadtid = _stadtid;
		}

		public int get_regionid() {
			return _regionid;
		}

		public void set_regionid(int _regionid) {
			this._regionid = _regionid;
		}
		
		public void set_name(String _name) {
			this._name = _name;
		}

		public String get_name() {
			return _name;
		}


	    public void save()
	    {
	        // Hole Verbindung
	        Connection con = DB2ConnectionManager.getInstance().getConnection();

	        try
	        {
	            // Achtung, hier wird noch ein Parameter mitgegeben,
	            // damit spaeter generierte IDs zurC<ckgeliefert werden!
	            String insertSQL = "INSERT INTO VSISP72.STADT(REGIONID, NAME) VALUES ( ? , ?)";
	            System.out.println("Fail1" + get_regionid() + get_name());
	            PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

	            // Setze Anfrageparameter und fC<hre Anfrage aus
	            pstmt.setInt(1, this.get_regionid());
	            pstmt.setString(2, this.get_name());
	            pstmt.executeUpdate();
	            System.out.println("Fail2");
	            ResultSet rs = pstmt.getGeneratedKeys();
	          /**  if (rs.next())
	            {
	                set_id(rs.getInt(1));
	            }**/

	            rs.close();
	            pstmt.close();

	        }
	        catch (SQLException e)
	        {
	            e.printStackTrace();
	        }
	    }
}
