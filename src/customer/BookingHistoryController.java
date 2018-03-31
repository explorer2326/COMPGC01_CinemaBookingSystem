/**
 * BookingHistoryController.java - the class controls the booking history pop-up
 * @author Hongyi Gongyang & Danqi He
 * @version 2.0
 * @see UpdateProfileController.java
 */
package customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import login.Logininfo;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;

public class BookingHistoryController
{
	
	@FXML
	private TableView <BookingHistory> userhistorytable;
	@FXML
	private TableColumn <BookingHistory, String> filmname;
	@FXML
	private TableColumn <BookingHistory, String> datename;
	@FXML
	private TableColumn <BookingHistory, String> bookedseat;
	@FXML
	private TableColumn <BookingHistory, String> filmtime;
	@FXML
	private TableColumn <BookingHistory, String> rowId;
	@FXML
	private TableColumn <BookingHistory, String> seatId;
	
	private dbConnection dc;
	private ObservableList<BookingHistory> historydata;
	String sqlhistory ="SELECT title, date, time, book, rowid, seatNum FROM Ticket where username = ?" ;
	/** Load the latest booking history from database*/
	public void initialize() {
		
		this.dc = new dbConnection();
		
		try{
			Connection conn = dbConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlhistory);
			stmt.setString(1, Logininfo.currentUsername);
			this.historydata =  FXCollections.observableArrayList();
			ResultSet rs  = stmt.executeQuery();
			while (rs.next()){
				this.historydata.add(new BookingHistory(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
		    }
			conn.close();
		}
		catch(SQLException e){
			System.out.println("error"+e);
		}

	this.filmname.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("filmName"));
	this.datename.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("filmDate"));
	this.bookedseat.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("seatNumber"));
	this.filmtime.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("filmTime"));
	this.rowId.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("rowid"));
	this.seatId.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("seating"));
	
    this.userhistorytable.setItems(null);
    this.userhistorytable.setItems(this.historydata);

	}
	
	/** Refresh functionality*/
	@FXML
	private void loadCustomerdata(ActionEvent event){

		try{
			Connection conn = dbConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlhistory);
			stmt.setString(1, Logininfo.currentUsername);
			this.historydata =  FXCollections.observableArrayList();
			ResultSet rs  = stmt.executeQuery();
			while (rs.next()){
				this.historydata.add(new BookingHistory(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
		    }
			conn.close();
		}
		catch(SQLException e){
			System.out.println("error"+e);
		}

	this.filmname.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("filmName"));
	this.datename.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("filmDate"));
	this.bookedseat.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("seatNumber"));
	this.filmtime.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("filmTime"));
	this.rowId.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("rowid"));
	this.seatId.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("seating"));
	
    this.userhistorytable.setItems(null);
    this.userhistorytable.setItems(this.historydata);
}
	/**
	 * 1. Compare current time with the film screening time
	 * 2. Update the booking history database
	 * 3. Update the film information database
	 */
	@FXML
	private void deleteBooking(ActionEvent event) throws ParseException{
		try{
		//Check date and time
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.UK);
		String dateSelected = this.userhistorytable.getSelectionModel().getSelectedItem().getFilmDate();
		String timeSelected = this.userhistorytable.getSelectionModel().getSelectedItem().getFilmTime();
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());
		Date now = format.parse(timeStamp);
		Date selected = format.parse(dateSelected+" "+timeSelected);
		if (selected.before(now)){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText("Your cannot delete this booking");
			alert.setContentText("You can only delete future bookings");
			alert.showAndWait();
		} else{
			//Delete from booking history
			String bookingHistory = "DELETE FROM Ticket WHERE rowid = ? and seatNum = ?";
			try{
				Connection conn =dbConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(bookingHistory);
				
				String rowid = this.userhistorytable.getSelectionModel().getSelectedItem().getRowid().getValue();
				String seatTemp = this.userhistorytable.getSelectionModel().getSelectedItem().getSeating().getValue();
				
				stmt.setString(1, rowid);
				stmt.setString(2, seatTemp);
				stmt.execute();
				conn.close();				
				}
			catch (SQLException e){
				e.printStackTrace();
			}
			//Update overall booking status
			String extract = "SELECT Book FROM filmlist WHERE rowid = ?";
			String updateOverall = "UPDATE filmlist SET Book = ? WHERE rowid = ?;";
			
			try{
				String updated;
				String rowid = this.userhistorytable.getSelectionModel().getSelectedItem().getRowid().getValue();
				String originalBook="";
				boolean[] original = new boolean[38];
				boolean[] user = new boolean[38];
				//Retrieve original booking
				Connection conn =dbConnection.getConnection();
				PreparedStatement retrieve = conn.prepareStatement(extract);
				retrieve.setString(1, rowid);
				ResultSet rs  = retrieve.executeQuery();
				if (rs.next()){
					originalBook = rs.getString(1);
			    }
				String userBook = this.userhistorytable.getSelectionModel().getSelectedItem().getSeatNumber();
				
				//String to Boolean
				String[] strTemp = originalBook.split(", ");
		    	for(int i = 0; i<strTemp.length;i++){
		    		original[i] = Boolean.parseBoolean(strTemp[i]);
		    	}
		    	
		    	String[] strTempo = userBook.split(", ");
		    	for(int i = 0; i<strTempo.length;i++){
		    		user[i] = Boolean.parseBoolean(strTempo[i]);
		    	}
		    	
		    	//Update boolean array
		    	for(int i = 0; i<38;i++){
		    		if(user[i] == original[i] & user[i] == true){
		    			original[i] = false;
		    		}
		    	}
		    	
		    	//boolean to String
		    	updated = Arrays.toString(original);
		    	updated = updated.replace("[", "");
		    	updated = updated.replace("]", "");
		    	
		    	PreparedStatement stmt = conn.prepareStatement(updateOverall);
				stmt.setString(1, updated);
				stmt.setString(2, rowid);
				stmt.executeUpdate();
				
				//Update seat info
				
				String updateBooked = "UPDATE filmlist SET Booked = (LENGTH(Book) - LENGTH(REPLACE(Book, 'true', '')))/4";
				String updateAvailable = "UPDATE filmlist SET Available = 38 - Booked";
				String updateTotal = "UPDATE filmlist SET Total = 38";
				conn.createStatement().executeUpdate(updateBooked);
				conn.createStatement().executeUpdate(updateAvailable);
				conn.createStatement().executeUpdate(updateTotal);				
				   			    	
		    	conn.close();		    					
				}
			catch (SQLException e){
				e.printStackTrace();
			}			
		}
		try{
			Connection conn = dbConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlhistory);
			stmt.setString(1, Logininfo.currentUsername);
			this.historydata =  FXCollections.observableArrayList();
			ResultSet rs  = stmt.executeQuery();
			while (rs.next()){
				this.historydata.add(new BookingHistory(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
		    }
			conn.close();
		}
		catch(SQLException e){
			System.out.println("error"+e);
		}

		this.filmname.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("filmName"));
		this.datename.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("filmDate"));
		this.bookedseat.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("seatNumber"));
		this.filmtime.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("filmTime"));
		this.rowId.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("rowid"));
		this.seatId.setCellValueFactory(new PropertyValueFactory<BookingHistory, String>("seating"));
		
	    this.userhistorytable.setItems(null);
	    this.userhistorytable.setItems(this.historydata);
		}
		catch(Exception e){
			
		}
	
	}
}
