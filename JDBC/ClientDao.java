package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientDao {
	private String requete;
	

	public ClientDao(Client c) {
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
				
				insertClient(c);
				
				int nbLignesImpactees = stmt.executeUpdate(requete);
				
				ResultSet rs = stmt.executeQuery("SELECT NOCLIENT, NOMCLIENT, NOTELEPHONE FROM CLIENT ORDER BY NOCLIENT");
				
				while(rs.next()){
					System.out.println("NOCLIENT :"+ rs.getInt("NOCLIENT")+" DNAME :" + rs.getString("NOMCLIENT")+" NOTELEPHONE :" + rs.getString("NOTELEPHONE"));
				}
				
				cnx.rollback();
				
			} catch (SQLException e) {
				System.out.println("Pb JDBC  -  " + e.getMessage());
			}
			
			
		} catch (SQLException e1) {
			System.out.println("Pb pour atteindre la BD  -  " + e1.getMessage());
			System.exit(2);
		}
	}

	public ClientDao(int id) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}catch (ClassNotFoundException e) {
			System.out.println("Driver non présent dans le CLASSPATH  -  " + e.getMessage());
			System.exit(1);
		}
		
		findClientByKey(id);
	}
	
	
	private Client findClientByKey(int id) {
		

		try( Connection cnx = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xepdb1", "livraison", "livraison")) {
			
			try(Statement stmt = cnx.createStatement()) {
				//On souhaite ne pas valider une transaction
				cnx.setAutoCommit(false);
				
				ResultSet rs = stmt.executeQuery("SELECT NOCLIENT, NOMCLIENT, NOTELEPHONE FROM CLIENT WHERE NOCLIENT = "+id);
				
				while(rs.next()){
					System.out.println("NOCLIENT :"+ rs.getInt("NOCLIENT")+" DNAME :" + rs.getString("NOMCLIENT")+" NOTELEPHONE :" + rs.getString("NOTELEPHONE"));
				}
				
						
				cnx.rollback();
				
				return new Client(rs.getInt("NOCLIENT"),rs.getString("NOMCLIENT"),rs.getString("NOTELEPHONE"));
				
			} catch (SQLException e) {
				System.out.println("Pb JDBC  -  " + e.getMessage());
			}
			
			return null;
		} catch (SQLException e1) {
			System.out.println("Pb pour atteindre la BD  -  " + e1.getMessage());
			System.exit(2);
		}
		return null;
	}
	
	
	
public boolean insertClient(Client c) {
	requete = "INSERT INTO CLIENT(NOCLIENT, NOMCLIENT, NOTELEPHONE) VALUES ("+c.getNoclient()+",'"+c.getNomClient()+"','"+c.getNotelephone()+"')";
	setRequete(requete);
	return true;
	
}
public String getRequete() {
	return requete;
}

public void setRequete(String requete) {
	this.requete = requete;
}
	
	
}
