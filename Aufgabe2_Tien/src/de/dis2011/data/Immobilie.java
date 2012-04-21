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
public class Immobilie
{
    private int    id = -1;
    private String ort;
    private int    plz;
    private String strasse;
    private int    hausnr;
    private int    flaeche;
    private int    makler;

    public boolean darfEdit(Makler m)
    {
        boolean zurueck = false;
        if (m.getId() == this.getMakler())
        {
            zurueck = true;
        }
        return zurueck;
    }

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
            String insertSQL = "INSERT INTO Immobilie(ort, plz, strasse, hausnr, flaeche, makler) VALUES (?, ?, ? , ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            // pstmt.setInt(1, getId());
            pstmt.setString(1, getOrt());
            pstmt.setInt(2, getPlz());
            pstmt.setString(3, getStrasse());
            pstmt.setInt(4, getHausnr());
            pstmt.setInt(5, getFlaeche());
            pstmt.setInt(6, getMakler());
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
            String insertSQL = "UPDATE immobilie SET ort = ?, plz = ?, strasse = ?, hausnr = ?, flaeche = ? WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            pstmt.setString(1, getOrt());
            pstmt.setInt(2, getPlz());
            pstmt.setString(3, getStrasse());
            pstmt.setInt(4, getHausnr());
            pstmt.setInt(5, getFlaeche());
            pstmt.setInt(6, getId());
            pstmt.executeUpdate();
            pstmt.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void delete(int Immo)
    {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try
        {

            // Achtung, hier wird noch ein Parameter mitgegeben,
            // damit spaeter generierte IDs zurC<ckgeliefert werden!
            String deleteSQL1 = "DELETE FROM Haus WHERE ID = ?";
            String deleteSQL2 = "DELETE FROM Wohnung WHERE ID = ?";
            String deleteSQL3 = "DELETE FROM Immobilie WHERE ID = ?";

            PreparedStatement pstmt1 = con.prepareStatement(deleteSQL1, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstmt2 = con.prepareStatement(deleteSQL2, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstmt3 = con.prepareStatement(deleteSQL3, Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            pstmt1.setInt(1, getId());
            pstmt2.setInt(1, getId());
            pstmt3.setInt(1, getId());
            pstmt1.executeUpdate();
            pstmt2.executeUpdate();
            pstmt3.executeUpdate();
            pstmt1.close();
            pstmt2.close();
            pstmt3.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static Immobilie load(int id)
    {
        try
        {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM immobilie WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                Immobilie ts = new Immobilie();
                ts.setId(rs.getInt("id"));
                ts.setOrt(rs.getString("ort"));
                ts.setPlz(rs.getInt("plz"));
                ts.setStrasse(rs.getString("strasse"));
                ts.setHausnr(rs.getInt("hausnr"));
                ts.setFlaeche(rs.getInt("flaeche"));
                ts.setMakler(rs.getInt("makler"));
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

    public void setOrt(String ort)
    {
        this.ort = ort;
    }

    public String getOrt()
    {
        return ort;
    }

    public void setPlz(int plz)
    {
        this.plz = plz;
    }

    public int getPlz()
    {
        return plz;
    }

    public void setStrasse(String strasse)
    {
        this.strasse = strasse;
    }

    public String getStrasse()
    {
        return strasse;
    }

    public void setHausnr(int hausnr)
    {
        this.hausnr = hausnr;
    }

    public int getHausnr()
    {
        return hausnr;
    }

    public void setFlaeche(int flaeche)
    {
        this.flaeche = flaeche;
    }

    public int getFlaeche()
    {
        return flaeche;
    }

    public void setMakler(int makler)
    {
        this.makler = makler;
    }

    public int getMakler()
    {
        return makler;
    }
}
