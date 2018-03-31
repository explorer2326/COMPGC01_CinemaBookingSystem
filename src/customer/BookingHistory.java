/**
 * BookingHistory.java - the class that declares setters and getters for the data related to user booking history
 * @author Hongyi Gongyang
 * @version 1.0
 * @see FilmData.java & UpdateProfile.java
 */
package customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookingHistory {

	 private SimpleStringProperty filmName;
	 private SimpleStringProperty filmDate;
	 private SimpleStringProperty filmTime;
	 private SimpleStringProperty seatNumber;
	 private SimpleStringProperty rowid;
	 private SimpleStringProperty seating;

	 public  BookingHistory(String filmname, String filmdate, String filmtime, String seatnumber,String rowid,String seating){
		 
		 this.filmName = new SimpleStringProperty(filmname);
		 this.filmDate = new SimpleStringProperty(filmdate);
		 this.filmTime = new SimpleStringProperty(filmtime);
		 this.seatNumber = new SimpleStringProperty(seatnumber);
		 this.rowid = new SimpleStringProperty(rowid);
		 this.seating = new SimpleStringProperty(seating);
		 
	 }

	
	public String getFilmName() {
		return filmName.get();
	}

	public String getFilmDate() {
		return filmDate.get();
	}

	public String getFilmTime() {
		return filmTime.get();
	}

	public String getSeatNumber() {
		return seatNumber.get();
	}

	public void setFilmName(String filmName) {
		this.filmName.set(filmName);
	}

	public void setFilmDate(String filmDate) {
		this.filmDate.set(filmDate);
	}

	public void setFilmTime(String filmTime) {
		this.filmTime.set(filmTime);
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber.set(seatNumber);
	}

	public StringProperty FilmNameProperty(){
		return this.filmName;

	}
	
	public StringProperty FilmDateProperty(){
		return this.filmDate;

	}
	public StringProperty FilmTimeProperty(){
		return this.filmTime;

	}
	public StringProperty SeatNumberProperty(){
		return this.seatNumber;

	}


	public SimpleStringProperty getRowid() {
		return rowid;
	}


	public void setRowid(SimpleStringProperty rowid) {
		this.rowid = rowid;
	}
	
	public StringProperty rowidProperty(){
		return this.rowid;

	}


	public SimpleStringProperty getSeating() {
		return seating;
	}


	public void setSeating(SimpleStringProperty seating) {
		this.seating = seating;
	}
	public StringProperty seatingProperty(){
		return this.seating;

	}
	
}
