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

import javax.swing.JOptionPane;

public class PingJdbc {
	
		public static void load(int type , String requete) throws IOException, FileNotFoundException{
	      Properties properties = new Properties();
	      FileInputStream input = new FileInputStream("JDBC\\jdbc.properties");
	      try{
	         properties.load(input);
	      }finally{
		         input.close();
		  }
	        
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
	 				cnx.setAutoCommit(true);
	 				
	 				if ( type == 1 ) {
	 					ResultSet rs = stmt.executeQuery(requete);
	 				
	 				while(rs.next()){
	 					JOptionPane.showInternalMessageDialog(null,"CLIENT NOCLIENT :"+ rs.getInt("NOCLIENT")+" DNAME :" + rs.getString("NOMCLIENT")+" NOTELEPHONE :" + rs.getString("NOTELEPHONE"),"SELECT DE TOUS LES CLIENTS",JOptionPane.INFORMATION_MESSAGE);
	 				}
	 				}else if(type == 2) {
	 					int nLI=stmt.executeUpdate(requete);
	 				}else {
	 					stmt.execute(requete);
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
	     

	public static void main(String[] args) throws FileNotFoundException, IOException {			  
		PingJdbc.load(1,"SELECT NOCLIENT, NOMCLIENT, NOTELEPHONE FROM CLIENT ORDER BY NOCLIENT");
		
	}
}


