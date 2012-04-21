package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import de.dis2011.data.DB2ConnectionManager;

/**
 * Makler-Bean
 * 
 * Beispiel-Tabelle: CREATE TABLE makler(id INTEGER NOT NULL GENERATED ALWAYS AS
 * IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY, name
 * varchar(255), address varchar(255), login varchar(40) UNIQUE, password
 * varchar(40));
 */
public class Wohnung
{
    private int id = -1;
    private int stockwerk;
    private int mietpreis;
    private int zimmer;
    private int balkon;
    private int ebk;

    /**
     * Speichert den Makler in der Datenbank. Ist noch keine ID vergeben worden,
     * wird die generierte Id von DB2 geholt und dem Model übergeben.
     */
    public void add()
    {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try
        {
            // Achtung, hier wird noch ein Parameter mitgegeben,
            // damit spaeter generierte IDs zurC<ckgeliefert werden!
            String insertSQL = "INSERT INTO Wohnung(id, stockwerk, mietpreis, zimmer, balkon, ebk) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            pstmt.setInt(1, this.getId());
            pstmt.setInt(2, this.getStockwerk());
            pstmt.setInt(3, this.getMietpreis());
            pstmt.setInt(4, this.getZimmer());
            pstmt.setInt(5, this.getBalkon());
            pstmt.setInt(6, this.getEbk());
            // pstmt.setString(1, getOrt());

            pstmt.executeUpdate();

            // Hole die Id des engefC<gten Datensatzes
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
            {
                setId(rs.getInt(1));
            }

            rs.close();
            pstmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void edit()
    {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try
        {
            // Achtung, hier wird noch ein Parameter mitgegeben,
            // damit spaeter generierte IDs zurC<ckgeliefert werden!
            String insertSQL = "UPDATE wohnung SET stockwerk = ?, mietpreis = ?, zimmer = ?, balkon = ?, ebk = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            // pstmt.setInt(1, getId());
            pstmt.setInt(1, this.getStockwerk());
            pstmt.setInt(2, this.getMietpreis());
            pstmt.setInt(3, this.getZimmer());
            pstmt.setInt(4, this.getBalkon());
            pstmt.setInt(5, this.getEbk());
            pstmt.setInt(6, this.getId());
            pstmt.executeUpdate();

            pstmt.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static Wohnung load(int id)
    {
        try
        {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM Wohnung WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                Wohnung ts = new Wohnung();
                ts.setId(rs.getInt("id"));
                ts.setStockwerk(rs.getInt("stockwerk"));
                ts.setMietpreis(rs.getInt("mietpreis"));
                ts.setZimmer(rs.getInt("zimmer"));
                ts.setBalkon(rs.getInt("balkon"));
                ts.setEbk(rs.getInt("ebk"));
                rs.close();
                pstmt.close();
                return ts;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setStockwerk(int stockwerk)
    {
        this.stockwerk = stockwerk;
    }

    public int getStockwerk()
    {
        return stockwerk;
    }

    public void setMietpreis(int mietpreis)
    {
        this.mietpreis = mietpreis;
    }

    public int getMietpreis()
    {
        return mietpreis;
    }

    public void setZimmer(int zimmer)
    {
        this.zimmer = zimmer;
    }

    public int getZimmer()
    {
        return zimmer;
    }

    public void setBalkon(int balkon)
    {
        this.balkon = balkon;
    }

    public int getBalkon()
    {
        return balkon;
    }

    public void setEbk(int ebk)
    {
        this.ebk = ebk;
    }

    public int getEbk()
    {
        return ebk;
    }

}