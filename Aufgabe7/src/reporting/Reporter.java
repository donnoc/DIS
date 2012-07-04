package reporting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import data.DB2ConnectionManager;

public class Reporter {
	public void run()
	{
		Connection conn = DB2ConnectionManager.getInstance().getConnection();
		// Erzeuge Anfrage
		String selectSQL = "SELECT s.REGION, a.NAME, QUARTER(v.Zeit), SUM(v.Anzahl)" 
						+ " FROM Verkaufstabelle v"
						+ " LEFT JOIN Artikel a ON v.ARTIKELID = a.ID"
						+ " LEFT JOIN Shop s ON v.SHOPID = s.ID"
						+ " GROUP BY CUBE(a.NAME, s.REGION, QUARTER(Zeit))"
						+ " ORDER BY s.REGION, QUARTER(v.Zeit), a.NAME";
		try
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(selectSQL);
			
			boolean firstLinePrinted = false;
			String name, lastRegion = "", region, quarter;
			int number;
			
			StringBuilder sbNames = new StringBuilder();
			StringBuilder sbNumbers = new StringBuilder();
			while(rs.next())
			{
				region = rs.getString(1);
				name = rs.getString(2);
				quarter = (rs.getInt(3) > 0)? "Quartal " + rs.getInt(3) : "Gesamt";
				number = rs.getInt(4);
				if(!firstLinePrinted)
				{
					if(name != null)
						sbNames.append(name + " | ");
					else
						sbNames.append("Gesamt |");
				}
				sbNumbers.append(number).append(" | ");
				if(name == null)
				{
					if(!firstLinePrinted)
					{
						System.out.println("Verkaeufe             | " + sbNames.toString());
						firstLinePrinted = true;
					}
					if(region == null)
						region = "Gesamt";
					if(region.equals(lastRegion))
						region = "       ";
					else
					{
						System.out.println("-------------------------------------------------------------------------------");
						lastRegion = region;
					}
					System.out.println(String.format("%s | %s | %s", region, quarter, sbNumbers.toString()));
					sbNumbers = new StringBuilder();
				}
			}
			rs.close();
			st.close();
			conn.close();
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Reporter r = new Reporter();
		r.run();
	}
}
