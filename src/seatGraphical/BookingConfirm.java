/** 
 * BookingConfirm.java - the class that controls the graphical presentation of customer's booking details
 * @author Danqi He
 * @version 1.0 
 * @see BookingStatus_Controller.java & SeatBooking.java
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

public class BookingConfirm {
	@FXML
	private Label seatNum; 
	@FXML
	private Label film; 
	@FXML
	private Label date; 
	@FXML
	private Label time; 
	
 
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
	/** Load the original booking status of selected film and add newly confirmed seats into the seating map*/
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
		
		for (int i=0;i<38;i++){
			if (Logininfo.currentBooking[i]){
				seats[i].setText("Confirmed");
				seats[i].setStyle("-fx-opacity: 1.0;-fx-background-color: orange;-fx-text-fill: white;-fx-font-size:7pt;");
			} 
		}
		
		int count = 0;int index = 0;
		for(int i=0;i<38;i++){
			if (Logininfo.currentBooking[i]){
				count++;
			}
		}
		
		String[] seatInfo = new String[count];
		
		for(int i=0;i<38;i++){
			if (Logininfo.currentBooking[i]){
				int column = i%6+1;	int row = i/6+1;
				if (row>6){
					row -= 1; column += 6;
				}
				int c = 64+row;
				String s = Character.toString((char)c)+column;
				seatInfo[index]=s;index++;
			}
		}
		String allBooking = " Seat(s): "+String.join(",", seatInfo);
		seatNum.setText(allBooking);
		film.setText(" Film: "+ Logininfo.titleTemp);
		date.setText(" Date: "+ Logininfo.dateTemp);
		time.setText(" Time: "+Logininfo.timeTemp);
		
		for(int i=0;i<Logininfo.currentBooking.length;i++){
			Logininfo.currentBooking[i]=false;
		}
	}
/** Return to customer view*/	
public void returnHome(ActionEvent event) throws IOException{
		
	//Scene switch
		Parent bookingDetail = FXMLLoader.load(getClass().getResource("/customer/SeatBooking.fxml"));
		Scene bookingConfirm = new Scene(bookingDetail);
		bookingConfirm.getStylesheets().add(getClass().getResource("/customer/customer.css").toExternalForm());
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(bookingConfirm);
		window.show();		
	}
}
