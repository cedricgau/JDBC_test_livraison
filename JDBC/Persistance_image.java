package JDBC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Blob;
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

import javax.swing.JOptionPane;

import oracle.jdbc.OracleBlob;
import oracle.sql.RAW;

public class Persistance_image {
	
	public static void persist_image() throws IOException, FileNotFoundException{
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
	 			
 				stmt.execute("CREATE TABLE TABLEIMAGE (idImage NUMBER PRIMARY KEY, leBlob blob)"); 
				
 				PreparedStatement pstmt = cnx.prepareStatement("INSERT INTO TABLEIMAGE VALUES(?,?)");
				pstmt.setInt(1,1);
 				File coq = new File("D:\\TP_GIT\\JDBC_test_livraison\\JDBC\\coq1.gif");
				pstmt.setBinaryStream(2,new FileInputStream(coq), (int) coq.length());
				pstmt.executeUpdate();
	 			
	 			ResultSet rs = stmt.executeQuery("select * from tableimage");
	 			
				while(rs.next()){
					System.out.println("La taille de l'image :"+ (int) rs.getBlob("LEBLOB").length());
					byte[] octets = rs.getBlob("LEBLOB").getBytes(1, (int) rs.getBlob("LEBLOB").length());
					FileOutputStream coq2 = new FileOutputStream("D:\\TP_GIT\\JDBC_test_livraison\\JDBC\\coq2.gif");
					coq2.write(octets);
					coq2.close();
				}
				
						
	 				
				int i = JOptionPane.showConfirmDialog(null, "Drop the table tableimage ?", "Drop the table", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION);
			
				if (i==0) stmt.execute("drop table tableimage cascade constraints PURGE");
	 					 				
	 					 				
	 			} catch (SQLException e) {
	 				System.out.println("Pb JDBC  -  " + e.getMessage());
	 			}


	 		} catch (SQLException e1) {
	 			System.out.println("Pb pour atteindre la BD  -  " + e1.getMessage());
	 			System.exit(2);
	 		}
	

}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		persist_image();
	}

}
