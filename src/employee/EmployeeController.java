/**
 * EmployeeController.java - the class that controls the functionalities of employee view
 * @author Danqi He
 * @Version 2.0
 * @see  SeatBookingController.java
 */

package employee;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import login.Logininfo;
import seatGraphical.SeatBooking;
import validation.ValidationMethod;

public class EmployeeController implements Initializable{
	
	@FXML
	private TextField title;
	@FXML
	private TextArea description;
	@FXML
	private DatePicker date;
	@FXML
	private TextField time;
	@FXML
	private ImageView imageView;
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
	@FXML
	private Label textArea;
	@FXML
	private Label desc;
	@FXML
	private Label titl;
	
	private File selectedFile;
	private dbConnection dc;
	private ObservableList<FilmData> data;
	
	private String sql = "SELECT * FROM filmlist";
	private String updateBooked = "UPDATE filmlist SET Booked = (LENGTH(Book) - LENGTH(REPLACE(Book, 'true', '')))/4";
	private String updateAvailable = "UPDATE filmlist SET Available = 38 - Booked";
	private String updateTotal = "UPDATE filmlist SET Total = 38";
	//used to initialize the book information at SQLite when adding new film
	String bookInitial = "false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false";
	/** Load the latest data to the film list from SQLite database */
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
		
		selectedFile=new File("default.png");

	}
	
	/** Refresh the film list*/
	@FXML
	private void loadFilmData(ActionEvent event){
		try{
			Connection conn = dbConnection.getConnection();
			this.data = FXCollections.observableArrayList();
			
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				this.data.add(new FilmData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8)));
			}
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
	
	/** Convert time string to hour as double*/
	public static double timeToHourValue(String timeFormat){
		
			String[] timeParse = timeFormat.split(":");
			double h = Integer.parseInt(timeParse[0]);
			if (timeParse.length>1){
				double m = Integer.parseInt(timeParse[1]);
				return (h+m/60);
			}
			else{
				return h;
			}			
	}
	/** Check time conflict*/
	public static boolean isNoOverlap(String[] dateArray,String[] timeArray, String dateAdd, String timeAdd){
		boolean[] check = new boolean[dateArray.length];
		for(int i = 0; i < dateArray.length;i++){
			if (dateArray[i].equals(dateAdd)){
				if(Math.abs(timeToHourValue(timeArray[i])-timeToHourValue(timeAdd))<1){
					check[i]=true;
				}
				else{
					check[i]=false;
				}
			}
			else{
				check[i]=false;
			}
		
		}
		if (SeatBooking.areAllFalse(check)){
			return true;
		}
		else{
			return false;
		}
	}
	/** Select the image for the film to be added*/
	public void addImage(ActionEvent event){
		try{
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(
					new ExtensionFilter("Image Files","*.png","*.jpg"),
					new ExtensionFilter("All Files","*.*")					
			);
					
			fc.setInitialDirectory(new File ("C:\\"));
			fc.setTitle("Select Image File");
			selectedFile=fc.showOpenDialog(null);	
			 if (selectedFile != null) {
				 textArea.setText(selectedFile.getAbsolutePath());
				 String imagePath = selectedFile.toURI().toURL().toString();
				 Image image = new Image(imagePath);
				 imageView.setImage(image);
		     } else {		    	 
		 		 selectedFile=new File("default.png");
		    	 textArea.setText("Image file selection cancelled.");
		     }			
		}
		catch(Exception e){
	 		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText("Your import is unsuccessful");
			alert.setContentText("The file is invalid");
			alert.showAndWait();
			e.printStackTrace();
		}
	}
		
	/** Convert image to blob, from http://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/ */
	private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }
	
	/** Display relevant information for the row selected*/
	@FXML
	public void handle(MouseEvent t) throws IOException {
		String descriptionTemp = this.filmTable.getSelectionModel().getSelectedItem().getDescription().getValue();
		String titleTemp = this.filmTable.getSelectionModel().getSelectedItem().getTitle().getValue();
		String dateTemp = this.filmTable.getSelectionModel().getSelectedItem().getDate().getValue();
		String timeTemp = this.filmTable.getSelectionModel().getSelectedItem().getTime().getValue();
		this.titl.setText(titleTemp);
		this.desc.setText(descriptionTemp);
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
            // from http://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/       
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
	
	/** Validate the text fields and add films into database*/
	@FXML
	private void addFilm(ActionEvent event) throws FileNotFoundException{
		String sqlInsert = "INSERT INTO filmlist(Title,Description,Date,Time,Book,Image) VALUES (?,?,?,?,?,?)";
		String dateSelect = "SELECT Date From filmlist;";
		String timeSelect = "SELECT Time From filmlist;";
		
		try{
			Connection conn =dbConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlInsert);
			ResultSet rsdate = conn.createStatement().executeQuery(dateSelect);
			ResultSet rstime = conn.createStatement().executeQuery(timeSelect);
			ArrayList<String> dates = new ArrayList<String>();
			ArrayList<String> times = new ArrayList<String>();
			while (rsdate.next()) { 
			    dates.add(rsdate.getString(1));
			}
			while (rstime.next()) { 
			    times.add(rstime.getString(1));
			}

			String[] dateArr = new String[dates.size()];
			dateArr = dates.toArray(dateArr);
			
			String[] timeArr = new String[times.size()];
			timeArr = times.toArray(timeArr);
			
			if(this.title.getText().isEmpty()|this.description.getText().isEmpty()|this.date.getEditor().getText().isEmpty()|this.time.getText().isEmpty()){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText("Your update is unsuccessful");
				alert.setContentText("Please insert information into all the textfields");
				alert.showAndWait();
			}
			else if(!ValidationMethod.timeFormat(this.time)){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText("Your update is unsuccessful");
				alert.setContentText("Please insert time as HH:mm in 24-hour format");
				alert.showAndWait();
			}
			else if(!isNoOverlap(dateArr,timeArr,this.date.getEditor().getText(),this.time.getText())){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText("Your update is unsuccessful");
				alert.setContentText("There is a time conflict!");
				alert.showAndWait();
			}
			else{
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
								
				stmt.setString(1, this.title.getText());
				stmt.setString(2, this.description.getText());
				stmt.setString(3, this.date.getEditor().getText());
				stmt.setString(4, this.time.getText());
				stmt.setString(5, this.bookInitial);
				stmt.setBytes(6, readFile(selectedFile.getAbsolutePath()));
				
				stmt.execute();
				
				this.title.setText("");
				this.description.setText("");
				this.date.setValue(null);
				this.time.setText("");
				conn.createStatement().executeUpdate(updateBooked);
				conn.createStatement().executeUpdate(updateAvailable);
				conn.createStatement().executeUpdate(updateTotal);
				conn.close();
			}
			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		try{
			Connection conn = dbConnection.getConnection();
			this.data = FXCollections.observableArrayList();
			
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()){
				this.data.add(new FilmData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8)));
			}
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
	
	/** Clear all text fields*/
	@FXML
	private void clearForm(ActionEvent event){
		this.title.setText("");
		this.description.setText("");
		this.date.setValue(null);
		this.time.setText("");

	}
	/** Check the detailed booked and available seat numbers*/
	@FXML
	private void bookingCheck(ActionEvent event) throws IOException{
		String bookTemp = this.filmTable.getSelectionModel().getSelectedItem().getBook().getValue();
		
		//String to Boolean
		String[] strTemp = bookTemp.split(", ");
    	for(int i = 0; i<strTemp.length;i++){
    		Logininfo.bookingStatus[i] = Boolean.parseBoolean(strTemp[i]);
    	}

		//Scene switch
		Parent bookingDetail = FXMLLoader.load(getClass().getResource("/seatGraphical/BookingStatus_Employee.fxml"));
		Scene bookingConfirm = new Scene(bookingDetail);
		bookingConfirm.getStylesheets().add(getClass().getResource("/seatGraphical/application.css").toExternalForm());
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(bookingConfirm);
		window.show();
	}
	/** Export the film list as a .csv file*/
	public void exportCSV(ActionEvent event){
        String sql = "SELECT Title, Description, Date, Time, Available, Booked, Total FROM filmlist";
        
        try {
        	 Connection conn =dbConnection.getConnection();
    		 ResultSet list = conn.createStatement().executeQuery(sql);
    		 String s = "Title,Description,Date,Screening Time,Available Seats,Booked Seats,Total Seats";
            // loop through the result set
            while (list.next()) {
            	s = s.concat("\n");
                s = s.concat("\""+list.getString("Title") + "\""+ "," + "\"" + list.getString("Description") + "\"" + "," + list.getString("Date")+ "," + list.getString("Time")+ "," + list.getInt("Available")+ "," + list.getInt("Booked")+ "," + list.getString("Total"));
            }
            try{
    			File file = new File("filmList.csv");
    			if(!file.exists()){
    				file.createNewFile();
    			}
    			PrintWriter pw = new PrintWriter(file);
    			pw.println(s);pw.close();
    			Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Export Confirm");
				alert.setHeaderText("Your export is successful");
				alert.setContentText("The file is located the root directory");
				alert.showAndWait();
    			
    		}
    		catch(IOException e){
    			e.printStackTrace();
    			
    		}
        }
        catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
        
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
	
