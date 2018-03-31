/**
 * UpdateProfile.java - the class that declares setters and getters for the data related to user booking history
 * @author Hongyi Gongyang
 * @version 1.0
 * @see FilmData.java & BookingHistory.java
 */
package customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UpdateProfile {
	private SimpleStringProperty ID;
	private SimpleStringProperty surName;
	private SimpleStringProperty firstName;
	private SimpleStringProperty email;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	private SimpleStringProperty division;


 public UpdateProfile(String id, String surname, String firstname, String email, String username, String password, String division){
	 this.ID = new SimpleStringProperty(id);
	 this.surName = new SimpleStringProperty(surname);
	 this.firstName = new SimpleStringProperty(firstname);
	 this.email = new SimpleStringProperty(email);

	 this.username = new SimpleStringProperty(username);
	 this.password = new SimpleStringProperty(password);

}

public String getID() {
	return ID.get();
}

public String getSurName() {
	return surName.get();
}

public String getFirstName() {
	return firstName.get();
}

public String getEmail() {
	return email.get();
}

public String getUsername() {
	return username.get();
}

public String getPassword() {
	return password.get();
}
public String getDivision() {
	return division.get();
}

public void setID(String iD) {
	this.ID.set(iD);
}

public void setSurName(String surName) {
	this.surName.set(surName);
}

public void setFirstName(String firstName) {
	this.firstName.set(firstName);
}

public void setEmail(String email) {
	this.email.set(email);
}

public void setUsername(String username) {
	this.username.set(username);
}

public void setPassword(String password) {
	this.password.set(password);
}
public void setDivision(String division) {
	this.division.set(division);
}

public StringProperty IdProperty(){
	return this.ID;

}
public StringProperty FirstNameProperty(){
	return this.firstName;

}
public StringProperty SurnameProperty(){
	return this.surName;

}
public StringProperty EmailProperty(){
	return this.email;

}
public StringProperty UsernameProperty(){
	return this.UsernameProperty();

}
public StringProperty PasswordProperty(){
	return this.PasswordProperty();

}
public StringProperty DivisionProperty(){
	return this.DivisionProperty();

}

}
