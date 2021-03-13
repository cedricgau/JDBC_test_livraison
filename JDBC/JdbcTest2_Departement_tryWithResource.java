package JDBC;

import java.sql.Connection;import java.sql.DriverManager;
import java.sql.ResultSet;import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest2_Departement_tryWithResource {
public static void main(String[] args)  {
	
// Charger la première classe du driver
try {
	Class.forName("oracle.jdbc.driver.OracleDriver");
} catch (ClassNotFoundException e) {
	System.out.println("Driver non présent dans le CLASSPATH  -  " + e.getMessage());
	System.exit(1);
}

//Ouvrir une connexion dite "réseau"  ( vs DataSource)

try( Connection cnx = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xepdb1", "scott", "scott")) {
	
	try(Statement stmt = cnx.createStatement()) {
		//On souhaite faire une transaction
		cnx.setAutoCommit(false);
		
		int nbLignesImpactees = stmt.executeUpdate("INSERT INTO DEPT(DEPTNO, DNAME, LOC) VALUES (94, 'FORMATION', 'Montreuil')");
		
		
		System.out.println("Retour de l'executeUpdate :" + nbLignesImpactees);		
		System.out.println();
		
		//Affichage du contenu de la table DEPT
		///////////////////////////////////////
		
		
		ResultSet rs = stmt.executeQuery("SELECT DEPTNO, DNAME, LOC FROM DEPT");
		
		while(rs.next()){
			System.out.println("DEPTNO :"+ rs.getInt("DEPTNO")+" DNAME :" + rs.getString("DNAME")+" LOC :" +rs.getString(3));
		}

		System.out.println("\nOn rembobine car c'est un test");
		cnx.rollback();

	} catch (SQLException e) {
		System.out.println("Pb JDBC  -  " + e.getMessage());
	}
	
	
} catch (SQLException e1) {
	System.out.println("Pb pour atteindre la BD  -  " + e1.getMessage());
	System.exit(2);
}}}



//finally {
//	//libération des ressources prises
//	//////////////////////////////////
//	if(rs != null)
//		try {
//			rs.close();
//		} catch (SQLException e) {
//			System.out.println("Pb sur la fermeture du resultSet  : " + e.getMessage());
//		}
//	if(stmt != null)
//		try {
//			stmt.close();
//		} catch (SQLException e) {
//			System.out.println("Pb sur la fermeture du statement  : " + e.getMessage());
//		}
//	if(cnx != null)
//		try {
//			cnx.close();
//		} catch (SQLException e) {
//			System.out.println("Pb sur la fermeture de la connexion  : " + e.getMessage());
//		}	
//}
//
// ResultSet rs = null;

