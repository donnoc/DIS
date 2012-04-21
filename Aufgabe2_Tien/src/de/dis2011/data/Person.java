package de.dis2011.data;

import java.sql.*;

public class Person 
{
	private int id = -1;
	private String vorname;
	private String nachname;
	private String adresse;
	
	public void add()
	{
		try
		{
			Connection conn = DB2ConnectionManager.getInstance().getConnection();
			
			String query = "INSERT INTO person(vorname, nachname, adresse) VALUES (?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, getVorname());
			stmt.setString(2, getNachname());
			stmt.setString(3, getAdresse());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if(rs.next())
			{
				setId(rs.getInt(1));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
}
