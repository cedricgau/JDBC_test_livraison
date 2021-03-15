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
		 				
		 				PreparedStatement pstmt = cnx.prepareStatement("INSERT INTO LETEMPS (id,laDate,Heure,laDateEtHeure,laDateEtHeure2) VALUES (?,?,?,?,?)");
		 				pstmt.setInt(1, 1);
		 				pstmt.setDate(2, Date.valueOf(LocalDate.now()));
		 				pstmt.setTime(3, Time.valueOf(LocalTime.now()));
		 	      		pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
		 	            pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
		 	            pstmt.executeUpdate();
		 			
		 				ResultSet rs = stmt.executeQuery("select TO_CHAR(ladate,'DD-MON-YYYY HH24:MI:SS'),TO_CHAR(heure,'DD-MON-YYYY HH24:MI:SS'),TO_CHAR(ladateetheure,'DD-MON-YYYY HH24:MI:SS'),TO_CHAR(ladateetheure2,'DD-MON-YYYY HH24:MI:SS.FF') from letemps");
//		 				while(rs.next()){
//		 					System.out.println("La date :"+ rs.getString("LADATE")+" L'heure :" + rs.getTime("HEURE")+" DATE ET TEMPS :" +rs.getDate("ladateetheure"));
//		 				}
		 				
		 				
		 				//requete = "INSERT INTO COMMANDE VALUES ( 500,"+Timestamp.valueOf(LocalDateTime.now())+",10)";
		 				
		 				
		 				
		 				
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
