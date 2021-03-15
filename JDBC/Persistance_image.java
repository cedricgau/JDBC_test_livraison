package JDBC;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Persistance_image {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//PingJdbc.load(2,"INSERT INTO ARTICLE VALUES(100,NULL,45,5)");
		PingJdbc.load(3,"CREATE TABLE tableImage ( idImage number, leBlob blob)");
		PingJdbc.load(3, "INSERT INTO TABLEIMAGE VALUES(1,coq1.gif)");

	}

}
