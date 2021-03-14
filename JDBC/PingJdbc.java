package JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PingJdbc {
	 
	   public static void load(String filename) throws IOException, FileNotFoundException{
	      Properties properties = new Properties();
	      FileInputStream input = new FileInputStream(filename);
	      try{
	         properties.load(input);
	        
	         try {
	 			Class.forName(properties.getProperty("driver"));
	 			
	 		}catch (ClassNotFoundException e) {
	 			System.out.println("Driver non présent dans le CLASSPATH  -  " + e.getMessage());
	 			System.exit(1);
	 		}

	 		//Ouvrir une connexion 

	 		try( Connection cnx = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("pwd"))) {
	 			
	 			try(Statement stmt = cnx.createStatement()) {
	 				//On souhaite ne pas valider une transaction
	 				cnx.setAutoCommit(false);
	 				
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
	         
	      }finally{
	         input.close();
	      }}

	public static void main(String[] args) throws FileNotFoundException, IOException {			  
		PingJdbc.load("F:\\Projets_Git\\JDBC_test_livraison\\JDBC\\jdbc.properties");
	}
}


