package lessons_for_computer;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.*;

public class Controller0 {
	public void onStart(MouseEvent e) throws IOException {
		if(e.getButton()==MouseButton.PRIMARY) {
	    	Main.sceneX=Main.stage.getX()+Main.stage.getScene().getX();
	    	Main.sceneY=Main.stage.getY()+Main.stage.getScene().getY();
			new MouseCage();
			Main.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("1.fxml"))));
		}
	}
}