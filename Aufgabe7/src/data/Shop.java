package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.DB2ConnectionManager;

public class Shop {

	private int _shopid;
	private int _stadtid;
	private String _name;


	public int get_shopid() {
		return _shopid;
	}

	public void set_shopid(int _shopid) {
		this._shopid = _shopid;
	}

	public int get_stadtid() {
		return _stadtid;
	}

	public void set_stadtid(int _stadtid) {
		this._stadtid = _stadtid;
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
            String insertSQL = "INSERT INTO VSISP72.SHOP(STADTID, NAME) VALUES ( ? , ?)";

            PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            //pstmt.setInt(1, this.get_shopid());
            pstmt.setInt(1, this.get_stadtid());
            pstmt.setString(2, this.get_name());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
            {
                set_shopid(rs.getInt(1));
            }

            rs.close();
            pstmt.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
