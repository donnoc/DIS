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
public class Haus
{
    private int id = -1;
    private int stockwerke;
    private int kaufpreis;
    private int garten;

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
            String insertSQL = "INSERT INTO Haus(id, stockwerke, kaufpreis, garten) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            pstmt.setInt(1, this.getId());
            pstmt.setInt(2, this.getStockwerke());
            pstmt.setInt(3, this.getKaufpreis());
            pstmt.setInt(4, this.getGarten());
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
            String insertSQL = "UPDATE haus SET stockwerke = ?, kaufpreis = ?, garten = ? WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            // pstmt.setInt(1, getId());
            pstmt.setInt(1, this.getStockwerke());
            pstmt.setInt(2, this.getKaufpreis());
            pstmt.setInt(3, this.getGarten());
            pstmt.setInt(4, this.getId());
            pstmt.executeUpdate();

            pstmt.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static Haus load(int id)
    {
        try
        {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM Haus WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                Haus ts = new Haus();
                ts.setId(rs.getInt("id"));
                ts.setStockwerke(rs.getInt("stockwerke"));
                ts.setKaufpreis(rs.getInt("kaufpreis"));
                ts.setGarten(rs.getInt("garten"));
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

    public void setStockwerke(int stockwerke)
    {
        this.stockwerke = stockwerke;
    }

    public int getStockwerke()
    {
        return stockwerke;
    }

    public void setKaufpreis(int kaufpreis)
    {
        this.kaufpreis = kaufpreis;
    }

    public int getKaufpreis()
    {
        return kaufpreis;
    }

    public void setGarten(int garten)
    {
        this.garten = garten;
    }

    public int getGarten()
    {
        return garten;
    }
}