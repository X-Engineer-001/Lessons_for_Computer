package lessons_for_computer;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class Controller51 {
	public void onRetry(ActionEvent e) throws IOException {
		Main.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("5.fxml"))));
	}
}
