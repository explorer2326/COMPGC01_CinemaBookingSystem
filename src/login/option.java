/**
 * option.java - the class declares the value inside the combo box shown in the login page
 * @author Danqi He (modified from https://www.youtube.com/watch?v=h1rYlMrvNyE)
 * @version 1.0 
 */
package login;

public enum option {
	Customer, Employee;
	
	private option(){};
	
	public String value(){
		return name();
	}
	
	public static option fromvalue(String v){
		return valueOf(v);
	}

}
