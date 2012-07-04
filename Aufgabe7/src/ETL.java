import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import data.Artikel;
import data.DB2ConnectionManager;
import data.Land;
import data.ProductKategorie;
import data.ProduktFamilie;
import data.ProduktGruppe;
import data.Region;
import data.Shop;
import data.Stadt;
import data.Verkaufstabelle;

public class ETL {

	public static void readDB() {

		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
				
			//Ausfuehren der Landanfrage
			String selectSQLLand = "SELECT LandID, name FROM DB2INST1.LANDID";
			PreparedStatement pstmt_land = con.prepareStatement(selectSQLLand);
			ResultSet rs_land = pstmt_land.executeQuery();
			
			while (rs_land.next()) {
				Land land     = new Land();
				
				land.set_landid(rs_land.getInt("landid"));
				land.set_name(rs_land.getString("name"));
				System.out.println("Land gesetzt");
				System.out.println("	landid:" + land.get_landid());
				System.out.println("	landname:" + land.get_name());
				System.out.println("Text1");
				land.save();
			};
			rs_land.close();
			pstmt_land.close();
				
			//Ausfuehren der Regionanfrage
			String selectSQLRegion = "SELECT LandID, name FROM DB2INST1.REGIONID";
			PreparedStatement pstmt_region = con.prepareStatement(selectSQLRegion);
			ResultSet rs_region = pstmt_region.executeQuery();
			
			while (rs_region.next()) {
				Region region     = new Region();
				
				region.set_landid(rs_region.getInt("landid"));
				region.set_name(rs_region.getString("name"));
				System.out.println("Region gesetzt");
				System.out.println("	landid:" + region.get_landid());
				System.out.println("	regionname:" + region.get_name());
				System.out.println("Text2");
				region.save();
			};
			rs_region.close();
			pstmt_region.close();
			
			//Ausfuehren der Regionanfrage
			String selectSQLStadt = "SELECT RegionID, name FROM DB2INST1.STADTID";
			PreparedStatement pstmt_stadt = con.prepareStatement(selectSQLStadt);
			ResultSet rs_stadt = pstmt_stadt.executeQuery();
			
			while (rs_stadt.next()) {
				Stadt stadt    = new Stadt();
				
				stadt.set_regionid(rs_stadt.getInt("RegionID"));
				stadt.set_name(rs_stadt.getString("name"));
				System.out.println("Stadt gesetzt");
				System.out.println("	regionId:" + stadt.get_regionid());
				System.out.println("	stadtname:" + stadt.get_name());
				System.out.println("Text3");
				stadt.save();
				System.out.println("Text5");
				
			};
			rs_stadt.close();
			pstmt_stadt.close();
			
			//Ausfuehren der Shopanfrage
			String selectSQLShop = "SELECT ShopID, StadtID, name FROM DB2INST1.SHOPID";
			PreparedStatement pstmt_shop = con.prepareStatement(selectSQLShop);
			ResultSet rs_shop = pstmt_shop.executeQuery();
			
			while (rs_shop.next()) {
				Shop shop    = new Shop();
				
				shop.set_shopid(rs_shop.getInt("shopid"));
				shop.set_stadtid(rs_shop.getInt("stadtid"));
				shop.set_name(rs_shop.getString("name"));
				System.out.println("Shop gesetzt");
				System.out.println("	shopid:" + shop.get_shopid());
				System.out.println("	stadtid:" + shop.get_stadtid());
				System.out.println("	shopname:" + shop.get_name());
				shop.save();
			};
			rs_shop.close();
			pstmt_shop.close();
			
			
			//Ausfuehren der Productcategoryanfrage
			String selectSQLProducategory = "SELECT name FROM DB2INST1.PRODUCTCATEGORYID";
			PreparedStatement pstmt_productcategory = con.prepareStatement(selectSQLProducategory);
			ResultSet rs_productcategory = pstmt_productcategory.executeQuery();
			
			while (rs_productcategory.next()) {
				ProductKategorie productcategory= new ProductKategorie();
				
				productcategory.set_name(rs_productcategory.getString("name"));
				System.out.println("Productcategory gesetzt");
				System.out.println("	productcategory:" + productcategory.get_name());
				productcategory.save();
			};
			rs_productcategory.close();
			pstmt_productcategory.close();
			
			//Ausfuehren der Productfamilyanfrage
			String selectSQLProductfamily = "SELECT Productcategoryid,name FROM DB2INST1.PRODUCTFAMILYID";
			PreparedStatement pstmt_productfamily = con.prepareStatement(selectSQLProductfamily);
			ResultSet rs_productfamily = pstmt_productfamily.executeQuery();

			while (rs_productfamily.next()) {
				ProduktFamilie productfamily= new ProduktFamilie();
				
				productfamily.set_productcategorieid(rs_productfamily.getInt("productcategoryid"));
				productfamily.set_name(rs_productfamily.getString("name"));
				System.out.println("productfamily gesetzt");
				System.out.println("	productfamily:" + productfamily.get_name());
				productfamily.save();
			};
			rs_productfamily.close();
			pstmt_productfamily.close();
			
			//Ausfuehren der productgroupanfrage
			String selectSQLproductgroup = "SELECT Productfamilyid,name FROM DB2INST1.productgroupID";
			PreparedStatement pstmt_productgroup = con.prepareStatement(selectSQLproductgroup);
			ResultSet rs_productgroup = pstmt_productgroup.executeQuery();

			while (rs_productgroup.next()) {
				ProduktGruppe productgroup= new ProduktGruppe();
				
				productgroup.set_productfamilyid(rs_productgroup.getInt("productfamilyid"));
				productgroup.set_name(rs_productgroup.getString("name"));
				System.out.println("productgroup gesetzt");
				System.out.println("	productgroup:" + productgroup.get_name());
				productgroup.save();
			};
			rs_productgroup.close();
			pstmt_productgroup.close();
			
			
			
			//Ausfuehren der Articleanfrage
			String selectSQLArticle = "SELECT ProductGroupID, preis, name FROM DB2INST1.ARTICLEID";
			PreparedStatement pstmt_article = con.prepareStatement(selectSQLArticle);
			ResultSet rs_article = pstmt_article.executeQuery();
			
			while (rs_article.next()) {
				   Artikel article = new Artikel();
				
				article.set_productgroupid(rs_article.getInt("productgroupid"));
				article.set_name(rs_article.getString("name"));
				article.set_preis(rs_article.getInt("preis"));
				System.out.println("Shop gesetzt");
				System.out.println("	shopid:" + article.get_productgroupid());
				System.out.println("	stadtid:" + article.get_preis());
				System.out.println("	shopname:" + article.get_name());
				article.save();
			};
			rs_article.close();
			pstmt_article.close();
			
			
				// return shop;
//			}
//			rs.close();
//			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "" +
					"select " +
					"A.ARTICLEID         as article_id, " +
					"A.name              as article_name," +
					"A.preis             as article_preis," +
					"G.productgroupid    as group_id," +
					"G.name              as group_name, " +
					"F.productfamilyid   as family_id," +
					"F.name              as family_name, " +
					"C.productcategoryid as category_id," +
					"C.name              as category_name " +
					
					"from " +
					"DB2INST1.ARTICLEID A, " +
					"DB2INST1.PRODUCTGROUPID G, " +
					"DB2INST1.PRODUCTFAMILYID F, " +
					"DB2INST1.PRODUCTCATEGORYID C " +
					
					"WHERE A.PRODUCTGROUPID = G.PRODUCTGROUPID " +
					"AND G.PRODUCTFAMILYID = F.PRODUCTFAMILYID " +
					"AND F.PRODUCTCATEGORYID = C.PRODUCTCATEGORYID";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			//System.out.println("Query geholt");

			// FŸhre Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Artikel artikel = new Artikel();
				ProduktGruppe productgroup = new ProduktGruppe();
				ProduktFamilie produktfamilie = new ProduktFamilie();
				ProductKategorie produktkategorie = new ProductKategorie();
				
				artikel.set_articleid(rs.getInt("article_id"));
				artikel.set_name(rs.getString("article_name"));
				artikel.set_preis(rs.getInt("article_preis"));
				artikel.set_productgroupid(rs.getInt("group_id"));
				//artikel.save();
				System.out.println("Artikel gesetzt");
				
				productgroup.set_name(rs.getString("group_name"));
				productgroup.set_productfamilyid(rs.getInt("family_id"));
				productgroup.set_productgroupid(rs.getInt("group_id"));
				//productgroup.save();
				System.out.println("Produktgruppe gesetzt");
				
				produktfamilie.set_name(rs.getString("family_name"));
				produktfamilie.set_productcategorieid(rs.getInt("category_id"));
				produktfamilie.set_productfamilyid(rs.getInt("family_id"));
				//produktfamilie.save();
				System.out.println("Produktfamilie gesetzt");
				
				produktkategorie.set_name(rs.getString("category_name"));
				produktkategorie.set_productcategorieid(rs.getInt("category_id"));
				//produktkategorie.save();
				System.out.println("Produktkategorie gesetzt");
				// return shop;
			}
			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void readFile() {
		try {
			// BufferedReader in = new BufferedReader(new
			// FileReader("sales.csv"));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream("sales.csv"), "UTF8"));
			int counter = 0;
			Hashtable<String, Integer> artikeltable = new Hashtable<String, Integer>();
			Hashtable<String, Integer> shoptable = new Hashtable<String, Integer>();
			while (in.ready()) {
				String[] line = in.readLine().split(";");
				// Datum;Shop;Artikel;Verkauft;Umsatz
				Verkaufstabelle vt = new Verkaufstabelle();

				vt.set_zeit(string2date(line[0]));
				if (!artikeltable.containsKey(line[2])) {
					vt.set_artikelid(line[2]);
					artikeltable.put(line[2], vt.get_artikelid());
				} else {
					vt.set_artikelid(artikeltable.get(line[2]));
				}
				if (!shoptable.containsKey(line[1])) {
					vt.set_shopid(line[1]);
					shoptable.put(line[1], vt.get_shopid());
				} else {
					vt.set_shopid(shoptable.get(line[1]));
				}
				
					System.out.println("Data: " + line[0] + line[1] + line[2]);
					System.out.println("Data1:" + vt.get_artikelid() + "--" + vt.get_shopid() + "--" + vt.get_zeit() + "--" + vt.get_umsatz());

				vt.set_anzahl(Integer.parseInt(line[3]));
				vt.set_umsatz(string2float(line[4]));
				System.out.println(counter);
				vt.save();
				vt = null;
				counter++;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static float string2float(String s) {
		float wert;
		String umsatz = s.replace(',', '.');
		wert = Float.valueOf(umsatz).floatValue();
		return wert;
	}

	private static java.sql.Date string2date(String string) {
		java.sql.Date datum = null;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			datum = new java.sql.Date(df.parse(string).getTime());
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return datum;
	}
}
