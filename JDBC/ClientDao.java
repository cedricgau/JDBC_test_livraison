package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
	private String requete;
	public List<Client> clients = new ArrayList<>();

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
	
	
	public ClientDao(String nom) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}catch (ClassNotFoundException e) {
			System.out.println("Driver non présent dans le CLASSPATH  -  " + e.getMessage());
			System.exit(1);
		}
		
		findClientByNom(nom);
	}

	private List<Client> findClientByNom(String nom) {
		try( Connection cnx = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xepdb1", "livraison", "livraison")) {
			
			try(Statement stmt = cnx.createStatement()) {
				//On souhaite ne pas valider une transaction
				cnx.setAutoCommit(false);
				
								
				ResultSet rs = stmt.executeQuery("SELECT NOCLIENT, NOMCLIENT, NOTELEPHONE FROM CLIENT WHERE NOMCLIENT LIKE '%"+nom+"%'");
				
				System.out.println("Clients portant le même nom : \n");
				while(rs.next()) {
					System.out.println(rs.getInt("NOCLIENT")+" "+rs.getString("NOMCLIENT")+" "+rs.getString("NOTELEPHONE"));
					clients.add(new Client(rs.getInt("NOCLIENT"),rs.getString("NOMCLIENT"),rs.getString("NOTELEPHONE")));
				
				}
	
				return clients;
				
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

	private Client findClientByKey(int id) {
		

		try( Connection cnx = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xepdb1", "livraison", "livraison")) {
			
			try(Statement stmt = cnx.createStatement()) {
				//On souhaite ne pas valider une transaction
				cnx.setAutoCommit(false);
				
				ResultSet rs = stmt.executeQuery("SELECT NOCLIENT, NOMCLIENT, NOTELEPHONE FROM CLIENT WHERE NOCLIENT = "+id);
				rs.next();
				int a = rs.getInt("NOCLIENT");
				String b = rs.getString("NOMCLIENT");
				String c = rs.getString("NOTELEPHONE");
	
				System.out.println("NOCLIENT :"+ a+" DNAME :" + b+" NOTELEPHONE :" + c);
						
				Client ask = new Client(a,b,c);
				
				return ask;
				
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

public List<Client> getClients() {
	return clients;
}

public void setClients(List<Client> clients) {
	this.clients = clients;
}
	
	
}
