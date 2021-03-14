package JDBC;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSum {
	private int numLivraison;
	private BigDecimal bg;

	public TestSum(int liv) {
		setNumLivraison(liv);
	}

	public int getNumLivraison() {
		return numLivraison;
	}

	public void setNumLivraison(int numLivraison) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}catch (ClassNotFoundException e) {
			System.out.println("Driver non présent dans le CLASSPATH  -  " + e.getMessage());
			System.exit(1);
		}

		//Ouvrir une connexion 

		try( Connection cnx = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xepdb1", "livraison", "livraison")) {
			
			try(Statement stmt = cnx.createStatement()) {
				//On souhaite ne pas valider une transaction
				cnx.setAutoCommit(false);
				
							
				ResultSet rs = stmt.executeQuery("select sum(quantitelivree*prixunitaire)\"Total\" from livraison,detaillivraison,article where livraison.nolivraison=detaillivraison.nolivraison and detaillivraison.noarticle=article.noarticle and livraison.nolivraison="+numLivraison);
				
				rs.next();
				this.setBg(rs.getBigDecimal("Total"));
				System.out.println("Montant total de la livraison : "+getBg());
				cnx.rollback();
				
			} catch (SQLException e) {
				System.out.println("Pb JDBC  -  " + e.getMessage());
			}
			
			
		} catch (SQLException e1) {
			System.out.println("Pb pour atteindre la BD  -  " + e1.getMessage());
			System.exit(2);
		}
	}

	public BigDecimal getBg() {
		return bg;
	}

	public void setBg(BigDecimal bg) {
		this.bg = bg;
	}

}
