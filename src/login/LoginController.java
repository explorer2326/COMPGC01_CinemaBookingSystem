/**
 * LoginController.java - the class controls the login page
 * @author Danqi He & Hongyi Gongyang (modified from https://www.youtube.com/watch?v=h1rYlMrvNyE)
 * @version 1.0 
 */
package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	LoginModel loginModel = new LoginModel();
	@FXML
	private Label dbstatus;
	@FXML
	private Label warning;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private ComboBox<option> comboBox;
	@FXML
	private Button loginButton;
	
	/** Check the database connection status and retrieve combo box information*/
	public void initialize(URL url, ResourceBundle rb){
		if (this.loginModel.isDatabaseConnected()){
			this.dbstatus.setText("Connected to database");
		}
		else{
			this.dbstatus.setText("Connection failed");
			this.dbstatus.setStyle("-fx-text-fill: red;");
		}
		this.comboBox.setItems(FXCollections.observableArrayList(option.values()));
	}
	
	/** Validate the user name & password pair as well as division, and link to different view based on the input*/
	@FXML
	public void Login(ActionEvent event) {
		try {
			if(this.loginModel.isLogin(this.username.getText(),this.password.getText(), ((option)this.comboBox.getValue()).toString())){
				Stage stage = (Stage)this.loginButton.getScene().getWindow();
				stage.close();
				switch(((option)this.comboBox.getValue()).toString()){
					case "Customer":
						customerLogin();
						break;
					case "Employee":
						employeeLogin();
						break;			
				}
			}
			else{
				this.warning.setText("Wrong Credential");
			}
		} catch (Exception e) {
			this.warning.setText("Wrong Credential");
		}
	}
	/** Change scene to customer view*/
	public void customerLogin(){
		Logininfo.currentUsername= username.getText().toString();
		try{
			Stage primaryStage =new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/customer/SeatBooking.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/customer/customer.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Seat Booking");
			primaryStage.show();
		}
		catch(IOException ex){
			ex.printStackTrace();			
		}
	}
	/** Change scene to employee view*/
	public void employeeLogin(){
		Logininfo.currentUsername= username.getText().toString();
		try{
			Stage employeeStage = new Stage();
			FXMLLoader employeeloader = new FXMLLoader();
			Pane employeeroot = (Pane)employeeloader.load(getClass().getResource("/employee/EmployeeView.fxml").openStream());
			Scene scene = new Scene(employeeroot);
			employeeStage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("/employee/employee.css").toExternalForm());
			employeeStage.setTitle("Employee View");
			employeeStage.setResizable(false);
			employeeStage.show();
		}
		catch(IOException ex){
			ex.printStackTrace();			
		}
	}
}
