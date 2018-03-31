/**
 * ValidationMethod.java - the class declares methods to validate user inputs
 * @author Hongyi Gongyang & Danqi He
 * @version 2.0
 */
package validation;

import javafx.scene.control.TextField;

public class ValidationMethod {
	/** Validation of email format
	 * @param TextField
	 * @return boolean
	 * */
	public static boolean emailFormat(TextField inputTextField){
		if (!inputTextField.getText().matches("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
			return false;
		}else{
			return true;
		}	  
	}
	/** Validation of text field
	 * @param TextField
	 * @return boolean
	 * */
	public static boolean textFieldNull(TextField inputTextField){
		if (inputTextField.getText().isEmpty()){
			return true;
		}
	  return false;
	}
	/** Validation of time format
	 * @param TextField
	 * @return boolean
	 * */
	public static boolean timeFormat(TextField inputTextField){
		if (!inputTextField.getText().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")){
			return false;
		}
	  return true;
	}
	
}
