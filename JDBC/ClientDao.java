package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ClientDao {

	public static void main(String[] args) {
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
				
				int nbLignesImpactees = stmt.executeUpdate("INSERT INTO CLIENT(NOCLIENT, NOMCLIENT, NOTELEPHONE) VALUES (90, 'G.Lemoyne', '911')");
				
				
				System.out.println("Retour de l'executeUpdate :" + nbLignesImpactees);		
				System.out.println();
				
		}

	}

}
public boolean insertClient(Client c) {
	
}
	
	
}
