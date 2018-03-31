/** 
 * BookingStatus_Controller.java - the class that controls the graphical presentation of latest booking status of selected film
 * @author Danqi He
 * @version 1.0 
 * @see BookingConfirm.java & SeatBooking.java
 */
package seatGraphical;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import login.Logininfo;

public class BookingStatus_Controller {
	@FXML
	private Label available; 
	@FXML
	private Label booked; 
	public ToggleButton seat1;	public ToggleButton seat2;	public ToggleButton seat3;
	public ToggleButton seat4;	public ToggleButton seat5;	public ToggleButton seat6;
	public ToggleButton seat7;	public ToggleButton seat8;	public ToggleButton seat9;
	public ToggleButton seat10;	public ToggleButton seat11;	public ToggleButton seat12;
	public ToggleButton seat13;	public ToggleButton seat14;	public ToggleButton seat15;
	public ToggleButton seat16;	public ToggleButton seat17;	public ToggleButton seat18;
	public ToggleButton seat19;	public ToggleButton seat20;	public ToggleButton seat21;
	public ToggleButton seat22;	public ToggleButton seat23;	public ToggleButton seat24;
	public ToggleButton seat25;	public ToggleButton seat26;	public ToggleButton seat27;
	public ToggleButton seat28;	public ToggleButton seat29;	public ToggleButton seat30;
	public ToggleButton seat31;	public ToggleButton seat32;	public ToggleButton seat33;
	public ToggleButton seat34;	public ToggleButton seat35;	public ToggleButton seat36;
	public ToggleButton seat37;	public ToggleButton seat38;
	/** Load the latest booking status of selected film*/
	public void initialize(){
		
		ToggleButton[] seats = {seat1,seat2,seat3,seat4,seat5,seat6,seat7,seat8,seat9,seat10,seat11,seat12,seat13,seat14,seat15,seat16,seat17,seat18,seat19,seat20,seat21,seat22,seat23,seat24,seat25,seat26,seat27,seat28,seat29,seat30,seat31,seat32,seat33,seat34,seat35,seat36,seat37,seat38};
		for (int i=0;i<38;i++){
			if (Logininfo.bookingStatus[i]){
				seats[i].setSelected(true);
				seats[i].setDisable(true);
				seats[i].setText("Booked");
				seats[i].setStyle("-fx-opacity: 1.0;-fx-background-color: blue;-fx-text-fill: white;");
			} else{
				seats[i].setSelected(false);
				seats[i].setDisable(true);
				seats[i].setStyle("-fx-opacity: 1.0;");
			}
		}
		int availableNum = 0;
		for(int i=0;i<38;i++){
			if (!Logininfo.bookingStatus[i]){
				availableNum+=1;
			}
		}
		available.setText(" Available seats: "+Integer.toString(availableNum));
		booked.setText(" Booked seats: "+Integer.toString(38-availableNum));
	}
	/** Return to employee view*/
	public void returnHome(ActionEvent returning) throws IOException{
		//Scene switch
		Parent bookingDetail = FXMLLoader.load(getClass().getResource("/employee/EmployeeView.fxml"));
		Scene bookingConfirm = new Scene(bookingDetail);
		bookingConfirm.getStylesheets().add(getClass().getResource("/employee/employee.css").toExternalForm());
		Stage window = (Stage)((Node)returning.getSource()).getScene().getWindow();
		window.setScene(bookingConfirm);
		window.show();
	}
}
