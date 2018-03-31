/**
 * LoginModel.java - the class loads the user profile information from database
 * @author Danqi He (modified from https://www.youtube.com/watch?v=h1rYlMrvNyE)
 * @version 1.0 
 */
package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbUtil.dbConnection;

public class LoginModel {
	
	Connection connection;
	/** Get connection to database*/
	public LoginModel(){
		try{
			this.connection = dbConnection.getConnection();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		if (this.connection == null){
			System.exit(1);
		}
	}
	/** Check connection status*/
	public boolean isDatabaseConnected(){
		return this.connection !=null;
	}
	/** Retrieve the user profile information from database*/
	public boolean isLogin(String user, String pass, String opt) throws Exception{
		PreparedStatement pr = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Customer where username = ? and password = ? and division = ?";
		try{
			pr = this.connection.prepareStatement(sql);
			pr.setString(1, user);
			pr.setString(2, pass);
			pr.setString(3, opt);
			
			rs = pr.executeQuery();			
			
			if(rs.next()){
				return true;
			}
			return false;
		}
		catch (SQLException ex){
			return false;
		}
		
		finally{
			{
			pr.close();
			rs.close();	
			}
		}
	}

}
