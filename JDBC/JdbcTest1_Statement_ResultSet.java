package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest1_Statement_ResultSet {
	

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// Charger la première classe du driver
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		
		//Ouvrir une connexion dîtes réseaux ( vs DataSource )
		
		Connection cnx = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xepdb1", "scott","scott");
		
		//Insertion d'une ligne dans la table DEPT, à la connexion on demande un statement
		
		Statement stmt = cnx.createStatement();
		
		cnx.setAutoCommit(false);
		
		int nbLignesImpactees = stmt.executeUpdate("INSERT INTO DEPT(DEPTNO, DNAME, LOC) VALUES (93, 'FORMATION', 'Montreuil')");
		
		System.out.println("Retour de l'executeUpdate :" + nbLignesImpactees);
		
		//Affichage du contenu de la table DEPT
		
		ResultSet rs = stmt.executeQuery("SELECT DEPTNO, DNAME, LOC FROM DEPT");
		
		while(rs.next()) {
			System.out.println("DEPTNO :"+ rs.getInt("DEPTNO")+" DNAME : "+ rs.getString("DNAME")+ " LOC : "+ rs.getString(3));
		}
		
		//libération des ressources
		rs.close();
		stmt.close();
		cnx.rollback();
		cnx.close();
	}

}
