/** 
 * SeatBooking.java - the class that controls the graphical presentation of seat booking of selected film
 * @author Danqi He
 * @version 1.0 
 * @see BookingStatus_Controller.java & BookingConfirm.java
 */
package seatGraphical;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import dbUtil.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import login.Logininfo;
import javafx.scene.control.Alert.AlertType;

public class SeatBooking {
	
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
	public void initialize() throws SQLException{
		String statusTemp = null;
		String sql = "SELECT Book FROM filmlist where rowid = ?";
		try{	
			Connection conn = dbConnection.getConnection();
			PreparedStatement pr = null;
			ResultSet rs = null;
			pr = conn.prepareStatement(sql);
			pr.setString(1, Logininfo.filmID);
			rs = pr.executeQuery();		
			if(rs.next()){
				statusTemp = rs.getString(1);
			}
			
			conn.close();
		}
		catch(SQLException e){
			System.err.println("Error" + e);
		}
		String[] strTemp = statusTemp.split(", ");
    	for(int i = 0; i<strTemp.length;i++){
    		Logininfo.bookingStatus[i] = Boolean.parseBoolean(strTemp[i]);
    	}
		
				
		ToggleButton[] seats = {seat1,seat2,seat3,seat4,seat5,seat6,seat7,seat8,seat9,seat10,seat11,seat12,seat13,seat14,seat15,seat16,seat17,seat18,seat19,seat20,seat21,seat22,seat23,seat24,seat25,seat26,seat27,seat28,seat29,seat30,seat31,seat32,seat33,seat34,seat35,seat36,seat37,seat38};
		for (int i=0;i<38;i++){
			if (Logininfo.bookingStatus[i]){
				seats[i].setSelected(true);
				seats[i].setText("Booked");
				seats[i].setStyle("-fx-background-color: blue;-fx-text-fill: white;");
			} else{
				seats[i].setSelected(false);
			}
		}
	}
	/** Change the boolean array representing the overall booking status based on selections*/
	public void seatSelection(ActionEvent selecting){
		ToggleButton[] seats = {seat1,seat2,seat3,seat4,seat5,seat6,seat7,seat8,seat9,seat10,seat11,seat12,seat13,seat14,seat15,seat16,seat17,seat18,seat19,seat20,seat21,seat22,seat23,seat24,seat25,seat26,seat27,seat28,seat29,seat30,seat31,seat32,seat33,seat34,seat35,seat36,seat37,seat38};
		for (int i=0;i<38;i++){
			if (selecting.getSource() == seats[i]){
				if (Logininfo.bookingStatus[i]){
					seats[i].setSelected(true);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Warning");
					alert.setHeaderText("Seat unavailable");
					alert.setContentText("This seat has been booked");
					alert.showAndWait();
				} else if (seats[i].isSelected()){
					seats[i].setText("Selected");
					Logininfo.currentBooking[i]=true;
				} 
				else{
					int column = i%6+1;	int row = i/6+1;
					if (row>6){
						row -= 1; column += 6;
					}
					int c = 64+row;
					String s = Character.toString((char)c)+column;
					seats[i].setText(s);
					Logininfo.currentBooking[i]=false;
				}
			}
	            
		}
		
	}
/** Check if all the elements within a boolean array are false
 * Modified from https://stackoverflow.com/questions/8260881/what-is-the-most-elegant-way-to-check-if-all-values-in-a-boolean-array-are-true 
 * @param boolean[]
 * @return boolean
 **/
	public static boolean areAllFalse(boolean[] array)
	{
	    for(boolean b : array) if(b) return false;
	    return true;
	}
	/** Validation of selections and store selection information into database*/
	public void bookingConfirm(ActionEvent confirming) throws IOException{
		
		ToggleButton[] seats = {seat1,seat2,seat3,seat4,seat5,seat6,seat7,seat8,seat9,seat10,seat11,seat12,seat13,seat14,seat15,seat16,seat17,seat18,seat19,seat20,seat21,seat22,seat23,seat24,seat25,seat26,seat27,seat28,seat29,seat30,seat31,seat32,seat33,seat34,seat35,seat36,seat37,seat38};
		
		
		for (int i=0;i<38;i++){
			if (seats[i].isSelected()){
				Logininfo.bookingStatus[i] = true;
			}
		}
				
		for (int i=0;i<38;i++){
			if (Logininfo.currentBooking[i]){
				seats[i].setText("Confirmed");
				seats[i].setStyle("-fx-background-color: orange;-fx-text-fill: white;-fx-font-size:7pt;");
				
			}
		}
		if (areAllFalse(Logininfo.currentBooking)){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Please select at least one seat before confirmation");
			alert.showAndWait();
		} else{
			//insert data to database
				String seatID = "";
				for(int i = 0; i<38;i++){
					if(Logininfo.currentBooking[i]){
						int column = i%6+1;	int row = i/6+1;
						if (row>6){
							row -= 1; column += 6;
						}
						int c = 64+row;
						String s = Character.toString((char)c)+column;
						if(seatID!=""){
							seatID = seatID.concat(", "+s);
						}
						else{
							seatID = seatID.concat(s);
						}
															
					}
				}
								
				Logininfo.bookTemp = Arrays.toString(Logininfo.currentBooking);
				Logininfo.bookTemp = Logininfo.bookTemp.replace("[", "");
				Logininfo.bookTemp = Logininfo.bookTemp.replace("]", "");
				
				String statusUpdate = Arrays.toString(Logininfo.bookingStatus);
				statusUpdate = statusUpdate.replace("[", "");
				statusUpdate = statusUpdate.replace("]", "");
				
				String bookingHistory = "INSERT INTO Ticket(username,title,date,time,book,rowid,seatNum) VALUES (?,?,?,?,?,?,?)";
				try{
					Connection conn =dbConnection.getConnection();
					PreparedStatement stmt = conn.prepareStatement(bookingHistory);
					
					stmt.setString(1, Logininfo.currentUsername);
					stmt.setString(2, Logininfo.titleTemp);
					stmt.setString(3, Logininfo.dateTemp);
					stmt.setString(4, Logininfo.timeTemp);
					stmt.setString(5, Logininfo.bookTemp);
					stmt.setString(6, Logininfo.filmID);
					stmt.setString(7, seatID);
					stmt.execute();
					conn.close();
					
					}
				catch (SQLException e){
					e.printStackTrace();
				}
				String overallUpdate = "UPDATE filmlist SET Book = ? WHERE rowid = ?;";
				try{
					Connection conn =dbConnection.getConnection();
					
					PreparedStatement overall = conn.prepareStatement(overallUpdate);
					
					overall.setString(1, statusUpdate);
					overall.setString(2, Logininfo.filmID);
					overall.execute();
					conn.close();
					
					}
				catch (SQLException e){
					e.printStackTrace();
				}
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Booking confirmation");
			alert.setHeaderText("Your booking is successful");
			alert.setContentText("Please click OK to view details");
			alert.showAndWait();
			
			//Scene switch
			Parent bookingDetail = FXMLLoader.load(getClass().getResource("/seatGraphical/BookingConfirm.fxml"));
			Scene bookingConfirm = new Scene(bookingDetail);
			bookingConfirm.getStylesheets().add(getClass().getResource("/seatGraphical/application.css").toExternalForm());
			Stage window = (Stage)((Node)confirming.getSource()).getScene().getWindow();
			window.setScene(bookingConfirm);
			window.show();
		}
	}
	/** Return to customer view*/
	public void returnHome(ActionEvent e) throws IOException{
		
		//Scene switch
		Parent bookingDetail = FXMLLoader.load(getClass().getResource("/customer/SeatBooking.fxml"));
		Scene bookingConfirm = new Scene(bookingDetail);
		bookingConfirm.getStylesheets().add(getClass().getResource("/customer/customer.css").toExternalForm());
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(bookingConfirm);
		window.show();
		}
		
		
	
}