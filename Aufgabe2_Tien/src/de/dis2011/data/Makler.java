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
public class Makler
{
    private int    id = -1;
    private String name;
    private String address;
    private String login;
    private String password;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Lädt einen Makler aus der Datenbank
     * 
     * @param id
     *            ID des zu ladenden Maklers
     * @return Makler-Instanz
     */
    // public static Makler load(int id)
    public static Makler load(String login)
    {
        try
        {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM makler WHERE login = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setString(1, login);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                Makler ts = new Makler();
                ts.setId(rs.getInt("id"));
                ts.setName(rs.getString("name"));
                ts.setAddress(rs.getString("adresse"));
                ts.setLogin(rs.getString("login"));
                ts.setPassword(rs.getString("passwort"));

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
            String insertSQL = "INSERT INTO makler(name, adresse, login, passwort) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            pstmt.setString(1, getName());
            pstmt.setString(2, getAddress());
            pstmt.setString(3, getLogin());
            pstmt.setString(4, getPassword());
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
        Connection con = DB2ConnectionManager.getInstance().getConnection();
        try
        {
            // Falls schon eine ID vorhanden ist, mache ein Update...
            String updateSQL = "UPDATE makler SET name = ?, adresse = ?, login = ?, passwort = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setString(1, getName());
            pstmt.setString(2, getAddress());
            pstmt.setString(3, getLogin());
            pstmt.setString(4, getPassword());
            pstmt.setInt(5, getId());
            pstmt.executeUpdate();

            pstmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void delete()
    {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try
        {

            // Achtung, hier wird noch ein Parameter mitgegeben,
            // damit spaeter generierte IDs zurC<ckgeliefert werden!
            String deleteSQL = "DELETE FROM makler WHERE ID = ?";

            PreparedStatement pstmt = con.prepareStatement(deleteSQL, Statement.RETURN_GENERATED_KEYS);

            // Setze Anfrageparameter und fC<hre Anfrage aus
            pstmt.setInt(1, getId());
            pstmt.executeUpdate();

            pstmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}