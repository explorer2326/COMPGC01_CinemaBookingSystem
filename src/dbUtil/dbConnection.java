/**
 * dbConnection.java - the class establishes SQLite database connection using JDBC driver
 * sqlite-jdbc-3.21.0.jar has been added to the Referenced Libraries
 */
package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
	private static final String SQCONN = "jdbc:sqlite:cinemaDB.sqlite";
	
	public static Connection getConnection() throws SQLException{
		try{
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(SQCONN);
		}
		catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}
		return null;
	}
} 
