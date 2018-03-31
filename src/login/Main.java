/**
 * Main.java - this class defines the starting point of this app
 * @author Danqi He
 * @version 1.0
 */
package login;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	/** Set up the login stage*/
	public void start(Stage primaryStage) {
		try {
			//seatBooking
			Parent LoginPage = FXMLLoader.load(getClass().getResource("/login/LoginApp.fxml"));
			Scene login = new Scene(LoginPage);
			login.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
			primaryStage.setTitle("Cinema Booking Management - Group4: Danqi & Hongyi");
			primaryStage.setScene(login);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}