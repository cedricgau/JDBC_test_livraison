package JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Information_Temporelle {
	
	
	public static void info_temp() throws FileNotFoundException, IOException {
		load();
	}
		public static void load() throws IOException, FileNotFoundException{
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
		 				cnx.setAutoCommit(true);
		 			
		 				stmt.execute("CREATE TABLE LETEMPS (id NUMBER PRIMARY KEY, laDate 	DATE, Heure 	DATE , laDateEtHeure	DATE, laDateEtHeure2	TIMESTAMP(3))"); 
		 				
		 				PreparedStatement pstmt = cnx.prepareStatement("INSERT INTO LETEMPS VALUES (?,?,?,?,?)");
		 				pstmt.setInt(1, 1);
		 				pstmt.setDate(2, Date.valueOf(LocalDate.now()));
		 				pstmt.setTime(3, Time.valueOf(LocalTime.now()));
		 	      		pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
		 	            pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
		 	            pstmt.executeUpdate();
		 			
		 				ResultSet rs = stmt.executeQuery("select * from letemps");
		 				while(rs.next()){
		 					System.out.println("La date :"+ rs.getDate("LADATE")+" L'heure :" + rs.getTime("HEURE")+" DATE ET TEMPS :" +rs.getTimestamp("ladateetheure"));
		 				}
		 				
		 				int i = JOptionPane.showConfirmDialog(null, "Drop the table letemps ?", "Drop the table", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION);
		 				
		 				if (i==0) stmt.execute("drop table letemps cascade constraints PURGE");
		 				
		 				
		 				pstmt=cnx.prepareStatement("INSERT INTO COMMANDE VALUES (?,?,?)");
		 				pstmt.setInt(1,500);
		 				pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
		 				pstmt.setInt(3,10);
		 				pstmt.executeUpdate();
		 				
		 				rs = stmt.executeQuery("select * from commande where NOCOMMANDE = 500");
		 				rs.next();
		 				i = JOptionPane.showConfirmDialog(null, "Résultat : nocommande = "+rs.getInt("NOCOMMANDE")+" datecommande = "+rs.getTimestamp("DATECOMMANDE")+" noclient = "+rs.getInt("NOCLIENT")+ "\n Erase this line  ?", "Drop the INSERT", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION);
		 				if (i==0) stmt.execute("delete from commande where nocommande = 500");
		 				
		 			} catch (SQLException e) {
		 				System.out.println("Pb JDBC  -  " + e.getMessage());
		 			}
	
	
		 		} catch (SQLException e1) {
		 			System.out.println("Pb pour atteindre la BD  -  " + e1.getMessage());
		 			System.exit(2);
		 		}
		
	
}
	public static void main(String[] args) throws FileNotFoundException, IOException {
	info_temp();

	}

}
