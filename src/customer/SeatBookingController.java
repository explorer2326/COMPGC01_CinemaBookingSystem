/**
 * SeatBookingController.java - the class that controls the functionalities of customer view
 * @author Danqi He
 * @Version 2.0
 * @see  EmployeeController.java
 */
package customer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import dbUtil.dbConnection;
import employee.FilmData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import login.Logininfo;

public class SeatBookingController implements Initializable{
	
	@FXML
	private Label title;
	@FXML
	private Label description;
	@FXML
	private DatePicker date;
	@FXML
	private ImageView display;
	@FXML
	private TableView<FilmData> filmTable;
	@FXML
	private TableColumn<FilmData,String>titleColumn;
	@FXML
	private TableColumn<FilmData,String>descriptionColumn;
	@FXML
	private TableColumn<FilmData,String>dateColumn;
	@FXML
	private TableColumn<FilmData,String>timeColumn;
	@FXML
	private TableColumn<FilmData,String>totalColumn;
	@FXML
	private TableColumn<FilmData,String>availableColumn;
	@FXML
	private TableColumn<FilmData,String>bookedColumn;
	@FXML
	private TableColumn<FilmData,String>bookColumn;
	/*@FXML
	private Label Title = new Label();
	@FXML
	private Label Description = new Label();*/
	
	private dbConnection dc;
	private ObservableList<FilmData> data;
	
	Connection connection;
	private String sql = "SELECT * FROM filmlist";
	private String updateBooked = "UPDATE filmlist SET Booked = (LENGTH(Book) - LENGTH(REPLACE(Book, 'true', '')))/4";
	private String updateAvailable = "UPDATE filmlist SET Available = 38 - Booked";
	private String updateTotal = "UPDATE filmlist SET Total = 38";
	
	/** Load the latest film list into the table*/
	public void initialize(URL url, ResourceBundle rb){
		this.dc = new dbConnection();
		
		try{
			Connection conn = dbConnection.getConnection();
			this.data = FXCollections.observableArrayList();
			conn.createStatement().executeUpdate(updateBooked);
			conn.createStatement().executeUpdate(updateAvailable);
			conn.createStatement().executeUpdate(updateTotal);
			ResultSet rs = conn.createStatement().executeQuery(sql);
			
			while (rs.next()){
				this.data.add(new FilmData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8)));
			}
			conn.close();
		}
		catch(SQLException e){
			System.err.println("Error" + e);
		}
		
		this.titleColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("title"));
		this.descriptionColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("description"));
		this.dateColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("date"));
		this.timeColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("time"));
		this.bookColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("book"));
		this.availableColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("available"));
		this.bookedColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("booked"));
		this.totalColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("total"));
		
		this.filmTable.setItems(null);
		this.filmTable.setItems(this.data);
	}
	
	/** Refresh functionality*/
	@FXML
	private void loadFilmData(ActionEvent event){
		try{
			Connection conn = dbConnection.getConnection();
			this.data = FXCollections.observableArrayList();
			
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				this.data.add(new FilmData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8)));
			}
			conn.close();
		}
		catch(SQLException e){
			System.err.println("Error" + e);
		}
				
		this.titleColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("title"));
		this.descriptionColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("description"));
		this.dateColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("date"));
		this.timeColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("time"));
		this.bookColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("book"));
		this.availableColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("available"));
		this.bookedColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("booked"));
		this.totalColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("total"));
						
		this.filmTable.setItems(null);
		this.filmTable.setItems(this.data);
	}
	/** Filter the film displayed according to the date selected */
	@FXML
	private void datePick(ActionEvent event){
		String pattern = "MM/dd/yyyy";

		this.date.setConverter(new StringConverter<LocalDate>() {
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 });
		try{
			Connection conn = dbConnection.getConnection();
			this.data = FXCollections.observableArrayList();
			
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				if(rs.getString(3).equals(this.date.getEditor().getText())){
				this.data.add(new FilmData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8)));
				}
			}
			conn.close();
		}
		catch(SQLException e){
			System.err.println("Error" + e);
		}
				
		this.titleColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("title"));
		this.descriptionColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("description"));
		this.dateColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("date"));
		this.timeColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("time"));
		this.bookColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("book"));
		this.availableColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("available"));
		this.bookedColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("booked"));
		this.totalColumn.setCellValueFactory(new PropertyValueFactory<FilmData,String>("total"));
						
		this.filmTable.setItems(null);
		this.filmTable.setItems(this.data);
	}
	
	/** Retrieve and display relevant information based on row selection*/
	@FXML
	public void handle(MouseEvent t) throws IOException {
		String descriptionTemp = this.filmTable.getSelectionModel().getSelectedItem().getDescription().getValue();
		String titleTemp = this.filmTable.getSelectionModel().getSelectedItem().getTitle().getValue();
		String dateTemp = this.filmTable.getSelectionModel().getSelectedItem().getDate().getValue();
		String timeTemp = this.filmTable.getSelectionModel().getSelectedItem().getTime().getValue();
		this.title.setText(titleTemp);
		this.description.setText(descriptionTemp);
		
		String selectImage = "SELECT Image FROM filmlist WHERE Title = ? and Date = ? and Time = ?";
		FileOutputStream fos = null;
		try{
			Connection conn = dbConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(selectImage);
			pstmt.setString(1, titleTemp);
			pstmt.setString(2, dateTemp);
			pstmt.setString(3, timeTemp);
			ResultSet rs = pstmt.executeQuery();
			
			// write binary stream into file
            File file = new File("imageTemp.png");
            fos = new FileOutputStream(file); 
           
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("Image");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    fos.write(buffer);
                }
            }
            Image image = new Image("file:imageTemp.png");
			display.setImage(image);
			conn.close();
		}
		catch(SQLException e){
			System.err.println("Error" + e);
		}	
		finally{
			if (fos != null) {
                fos.close();
            }
		}
    }
	
	/** Retrieve information based on row selection and pass it to graphical presentation of seating map*/
	public void seatBooking(ActionEvent event) throws IOException, SQLException{
		try{
		Logininfo.dateTemp = this.filmTable.getSelectionModel().getSelectedItem().getDate().getValue();
		Logininfo.timeTemp = this.filmTable.getSelectionModel().getSelectedItem().getTime().getValue();
		Logininfo.titleTemp = this.filmTable.getSelectionModel().getSelectedItem().getTitle().getValue();
	
		//filmID
		Connection conn = dbConnection.getConnection();
		PreparedStatement pr = null;
		ResultSet rs = null;
		String sql = "SELECT rowid FROM filmlist where Date = ? and Time = ?";
		try{
			pr = conn.prepareStatement(sql);
			pr.setString(1, Logininfo.dateTemp);
			pr.setString(2, Logininfo.timeTemp);
			rs = pr.executeQuery();			
			
			if(rs.next()){
				Logininfo.filmID = rs.getString(1);				
			}
			conn.close();
		}
		catch(SQLException e){
			System.err.println("Error" + e);
		}	
		
		//Scene switch
		Parent bookingDetail = FXMLLoader.load(getClass().getResource("/seatGraphical/SeatingPlan.fxml"));
		Scene bookingConfirm = new Scene(bookingDetail);
		bookingConfirm.getStylesheets().add(getClass().getResource("/seatGraphical/application.css").toExternalForm());
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(bookingConfirm);
		window.show();
		}
		catch(Exception e){
			
		}
	}
	/** Open the Booking History pop-up*/
	public void BookingHistory (ActionEvent event) throws Exception{

		Stage primaryStage =new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/customer/BookingHistory.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/customer/customer.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Booking History");
		primaryStage.show();
	}
	/** Open the Update Profile pop-up*/
	public void UpdateProfile (ActionEvent event) throws Exception{
		
		Stage primaryStage =new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/customer/UpdateProfile.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/customer/customer.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Update Profile");
		primaryStage.show();
			
	}
	/** Logout functionality*/
	public void Logout (ActionEvent event) throws Exception{
		Parent bookingDetail = FXMLLoader.load(getClass().getResource("/login/LoginApp.fxml"));
		Scene bookingConfirm = new Scene(bookingDetail);
		bookingConfirm.getStylesheets().add(getClass().getResource("/login/login.css").toExternalForm());
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(bookingConfirm);
		window.show();
	}
	
		
}