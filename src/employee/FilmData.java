/**
 * FilmData.java - the class that declares setters and getters for the data related to film list
 * @author Danqi He
 * @version 1.0
 * @see BookingHistory.java & UpdateProfile.java
 */
package employee;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilmData {
	private StringProperty title;
	private StringProperty description;
	private StringProperty date;
	private StringProperty time;
	private StringProperty book;
	private IntegerProperty available;
	private IntegerProperty booked;
	private IntegerProperty total;
	
	public FilmData(String title, String description, String date, String time, String book, int available,int booked, int total){
		
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
		this.date = new SimpleStringProperty(date);
		this.time = new SimpleStringProperty(time);
		this.book = new SimpleStringProperty(book);
		this.available = new SimpleIntegerProperty(available);
		this.booked = new SimpleIntegerProperty(booked);
		this.total = new SimpleIntegerProperty(total);
		
	}
	
	public StringProperty getTitle() {
		return title;
	}

	public void setTitle(StringProperty title) {
		this.title = title;
	}

	public StringProperty getDate() {
		return date;
	}

	public void setDate(StringProperty date) {
		this.date = date;
	}

	public StringProperty getTime() {
		return time;
	}

	public void setTime(StringProperty time) {
		this.time = time;
	}
		
	public StringProperty titleProperty(){
		return this.title;
	}
	
	public StringProperty dateProperty(){
		return this.date;
	}
	
	public StringProperty timeProperty(){
		return this.time;
	}

	public StringProperty getDescription() {
		return description;
	}

	public void setDescription(StringProperty description) {
		this.description = description;
	}
	public StringProperty descriptionProperty(){
		return this.description;
	}

	public StringProperty getBook() {
		return book;
	}

	public void setBook(StringProperty book) {
		this.book = book;
	}
	public StringProperty bookProperty(){
		return this.book;
	}

	public IntegerProperty getAvailable() {
		return available;
	}

	public void setAvailable(IntegerProperty available) {
		this.available = available;
	}
	public IntegerProperty availableProperty(){
		return this.available;
	}

	public IntegerProperty getTotal() {
		return total;
	}

	public void setTotal(IntegerProperty total) {
		this.total = total;
	}

	public IntegerProperty getBooked() {
		return booked;
	}

	public void setBooked(IntegerProperty booked) {
		this.booked = booked;
	}
	public IntegerProperty bookedProperty(){
		return this.booked;
	}
	public IntegerProperty totalProperty(){
		return this.total;
	}

}
