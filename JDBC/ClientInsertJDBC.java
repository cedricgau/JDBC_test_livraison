package JDBC;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import JDBC.Client;
import JDBC.ClientDao;

import javax.swing.JOptionPane;

public class ClientInsertJDBC {
	

	public static void main(String[] args)  {
		
	// Charger la première classe du driver
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
	} catch (ClassNotFoundException e) {
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
			
			//Affichage du contenu de la table DEPT
			///////////////////////////////////////
			
			
			ResultSet rs = stmt.executeQuery("SELECT NOCLIENT, NOMCLIENT, NOTELEPHONE FROM CLIENT");
			
			System.out.println("\n----------------QUESTION 1 : AFFICHAGE DE LA TABLE CLIENT APRES INSERTION DE G.Lemoyne-----------------\n");
			
			while(rs.next()){
				System.out.println("NOCLIENT :"+ rs.getInt("NOCLIENT")+" DNAME :" + rs.getString("NOMCLIENT")+" NOTELEPHONE :" +rs.getString("NOTELEPHONE"));
			}
			
					
			System.out.println("\n----------------QUESTION 2 : AFFICHAGE DE LA TABLE CLIENT DONT LES CLIENTS ONT UN NUMERO > 40-----------------\n");
			
			rs = stmt.executeQuery("SELECT NOCLIENT, NOMCLIENT FROM CLIENT WHERE NOCLIENT > 40");
			
			while(rs.next()){
				System.out.println("NOCLIENT :"+ rs.getInt("NOCLIENT")+" DNAME :" + rs.getString("NOMCLIENT"));
			}
			
			System.out.println("\n----------------QUESTION 3 : ASSEMBLAGE D'UNE REQUETE SQL DYNAMIQUE-----------------\n");
			
			String num = JOptionPane.showInputDialog(null, "Quel est le numéro du client désiré supérieur à 40 ? ", "Numéro du client", JOptionPane.INFORMATION_MESSAGE);
			
			rs = stmt.executeQuery("SELECT NOCLIENT, NOMCLIENT FROM CLIENT WHERE NOCLIENT = "+num+" AND NOCLIENT > 40");
			
			while(rs.next()){
				System.out.println("NOCLIENT :"+ rs.getInt("NOCLIENT")+" DNAME :" + rs.getString("NOMCLIENT"));
			}
			
			System.out.println("\n----------------QUESTION 4 : UTILISATION D'UN PREPARESTATEMENT-----------------\n");
			
			String num2 = JOptionPane.showInputDialog(null, "Quel est le numéro du client désiré ? ", "Numéro du client", JOptionPane.INFORMATION_MESSAGE);
			
			PreparedStatement pstmt = cnx.prepareStatement("SELECT NOCLIENT, NOMCLIENT FROM CLIENT WHERE NOCLIENT > ?");
			int intNoClient = Integer.parseInt(num2);
			pstmt.setInt(1, intNoClient);
			boolean nbLignesImpactees2 = pstmt.execute();
			System.out.println(nbLignesImpactees2);
			System.exit(0);
			
			
			System.out.println("\nOn rembobine car c'est un test");
			cnx.rollback();

		} catch (SQLException e) {
			System.out.println("Pb JDBC  -  " + e.getMessage());
		}
		
		
	} catch (SQLException e1) {
		System.out.println("Pb pour atteindre la BD  -  " + e1.getMessage());
		System.exit(2);
	}
	
			System.out.println("\n----------------QUESTION 5 : PERSISTENCE D'UN OBJET CLIENT : CLIENTDAO-----------------\n");
	
	
			Client ced = new Client(120, "Cédric Gautier", "06 99 99 99");
			new ClientDao(ced);
	
	
			System.out.println("\n----------------QUESTION 6 : FINDCLIENTBYKEY DANS CLIENTDAO-----------------\n");
	
			new ClientDao(70);
			
			System.out.println("\n----------------QUESTION 7 : MONTANT TOTAL DE LA LIVRAISON-----------------\n");
			
			new TestSum(100);
			
			System.out.println("\n----------------QUESTION 8 : FINDCLIENTBYNOM DANS CLIENTDAO-----------------\n");
			
			new ClientDao("Tremblay");
			
	
	}}


