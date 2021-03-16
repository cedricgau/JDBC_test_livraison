package JDBC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ExecuteProcedure {
	
	public static void execute_procedure() throws IOException, FileNotFoundException{
    try {
    	Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}catch (ClassNotFoundException e) {
			System.out.println("Driver non pr�sent dans le CLASSPATH  -  " + e.getMessage());
			System.exit(1);
		}

		//Ouvrir une connexion 

		try( Connection cnx = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xepdb1", "livraison", "livraison")) {
			
			// Pr�paration � l'appel de la proc�dure stock�e
			// on value les param�tres en entr�e (IN)
			try (CallableStatement cstmt = cnx.prepareCall("{call p_modifier_quantiteEnStock (?,?)}")){
				cstmt.setInt(1, 10);
				
				//Ins�rer somme inf�rieur � 100 !
				cstmt.setInt(2, 101);
				cstmt.execute();
				cstmt.close();
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "ERREUR SUR LE MONTANT", JOptionPane.WARNING_MESSAGE);
			}
			
			// d�clenchement de l'execution de la proc�dure
			
			
			// affichage de la modification
			try (CallableStatement cstmt = cnx.prepareCall("{? = call f_quantiteEnStock (?)}")){
			
				cstmt.setInt(2, 10);
			
			// on enregistre la sortie
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			
			// d�clenchement de l'execution de la proc�dure
			cstmt.execute();
						
			System.out.println("Quantit� en stock : " + cstmt.getInt(1));
			
			cstmt.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "ERREUR SUR LE MONTANT", JOptionPane.WARNING_MESSAGE);
			}
			
			
			
			cnx.close();
			
			int i = JOptionPane.showConfirmDialog(null, "Drop the table tableimage ?", "Drop the table", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION);
	
		
			
					 				
			


		} catch (SQLException e1) {
			System.out.println("Pb pour atteindre la BD  -  " + e1.getMessage());
			System.exit(2);
		}


}

public static void main(String[] args) throws FileNotFoundException, IOException {
execute_procedure();
}


}
