/**
 * UpdateProfileController.java - the class controls the user profile pop-up
 * @author Hongyi Gongyang & Danqi He
 * @version 1.0
 * @see BookingHistoryController.java
 */
package customer;

import validation.ValidationMethod;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import login.Logininfo;
import dbUtil.dbConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UpdateProfileController implements Initializable{
	@FXML
	private TextField surname;
	@FXML
	private TextField firstname;
	@FXML
	private TextField email;
	@FXML
	private TextField password;
	@FXML
	private TableColumn<UpdateProfile, String> columnid;
	@FXML
	private TableColumn<UpdateProfile, String> columnsurname;
	@FXML
	private TableColumn<UpdateProfile, String> columnfirstname;
	@FXML
	private TableColumn<UpdateProfile, String> columnemail;
	@FXML
	private TableColumn<UpdateProfile, String> columnusername;
	@FXML
	private TableColumn<UpdateProfile, String> columnpassword;
	@FXML
	private TableView<UpdateProfile> currentinfo;

	private dbConnection dc;
	private ObservableList<UpdateProfile> customerdata;
	String sqlcurrent ="SELECT * FROM Customer WHERE username == ? " ;

	/** Load the customer information based on the unique user name*/
	@Override
	public void initialize(URL url, ResourceBundle resource) {
		this.dc = new dbConnection();
		try{

			Connection conn = dbConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlcurrent);
			stmt.setString(1, Logininfo.currentUsername);
			this.customerdata =  FXCollections.observableArrayList();
			 ResultSet rs  = stmt.executeQuery();
			 while (rs.next()){
		   this.customerdata.add(new UpdateProfile(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
		    }
		}
		catch(SQLException e){
			System.out.println("error"+e);
		}
	this.columnid.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("ID"));
	this.columnsurname.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("surName"));
	this.columnfirstname.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("firstName"));
	this.columnemail.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("email"));
	this.columnusername.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("username"));
	this.columnpassword.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("password"));

    this.currentinfo.setItems(null);
    this.currentinfo.setItems(this.customerdata);
	}
	
	/** Refresh functionality*/
	@FXML
	private void loadCustomerdata(ActionEvent event){

		try{
			Connection conn = dbConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlcurrent);
			stmt.setString(1, Logininfo.currentUsername);
			this.customerdata =  FXCollections.observableArrayList();
			 ResultSet rs  = stmt.executeQuery();
			 while (rs.next()){
		   this.customerdata.add(new UpdateProfile(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
		    }
		}
		catch(SQLException e){
			System.out.println("error"+e);
		}

	this.columnid.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("ID"));
	this.columnsurname.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("surName"));
	this.columnfirstname.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("firstName"));
	this.columnemail.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("email"));
	this.columnusername.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("username"));
	this.columnpassword.setCellValueFactory(new PropertyValueFactory<UpdateProfile, String>("password"));

    this.currentinfo.setItems(null);
    this.currentinfo.setItems(this.customerdata);
}
	/** Validation of text fields, email and update the database*/
	@FXML
	private void updateCustomerInfo(ActionEvent event){
		 if(this.surname.getText().isEmpty()|this.firstname.getText().isEmpty()|this.email.getText().isEmpty()|this.password.getText().isEmpty()){
			 Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("Warning");
			 alert.setHeaderText("Your update is unsuccessful");
			 alert.setContentText("Please fill in information in all the text fields");
			 alert.showAndWait();	 
		 }
		 else if (!ValidationMethod.emailFormat(this.email)){
			 Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("Warning");
			 alert.setHeaderText("Your update is unsuccessful");
			 alert.setContentText("Please insert correct email address");
			 alert.showAndWait();	 
		 }else{
		 String sqlcurrent ="UPDATE Customer SET surname = ? , firstname = ?, email = ?, password = ? WHERE username = ?" ;
		 try  {
			 Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlcurrent);
	            pstmt.setString(1, this.surname.getText());
	            pstmt.setString(2, this.firstname.getText());
	            pstmt.setString(3, this.email.getText());
	            pstmt.setString(4,this.password.getText());
	            pstmt.setString(5, Logininfo.currentUsername);
	            pstmt.executeUpdate();
	            pstmt.execute();
	            conn.close();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		 
		 }
	}
}
