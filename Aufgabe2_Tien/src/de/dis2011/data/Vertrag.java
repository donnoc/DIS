package de.dis2011.data;

import java.sql.*;
import java.util.Date;

public abstract class Vertrag 
{
	private int id = -1;
	private int vertragsnummer;
	private Date datum;
	private String ort;
	
	protected void add() throws SQLException
	{
		// Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();
        
        String insertSQL = "INSERT INTO Vertrag(vertragsnr, datum, ort) VALUES (?, ?, ?)";

        PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, getVertragsnummer());
        pstmt.setDate(2, new java.sql.Date(getDatum().getTime()));
        pstmt.setString(3, getOrt());
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
	
	protected void load(int id)
	{
		try
		{
			Connection conn = DB2ConnectionManager.getInstance().getConnection();
			
			// Anfrage
			String query = "SELECT * FROM vertrag WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next())
			{
				this.setId(rs.getInt("id"));
				this.setOrt(rs.getString("ort"));
				this.setVertragsnummer(rs.getInt("vertragsnr"));
				this.setDatum(rs.getDate("datum"));
				rs.close();
				stmt.close();
				return;
			}
			else
			{
				System.err.println("Vertrag nicht gefunden!");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setVertragsnummer(int vertragsnummer) {
		this.vertragsnummer = vertragsnummer;
	}
	public int getVertragsnummer() {
		return vertragsnummer;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public Date getDatum() {
		return datum;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	public String getOrt() {
		return ort;
	}	
}
