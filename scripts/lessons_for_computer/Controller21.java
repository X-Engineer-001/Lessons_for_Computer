package lessons_for_computer;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class Controller21 {
	public void onRetry(ActionEvent e) throws IOException {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("2.fxml"));
		Scene scene=new Scene(loader.load());
	    scene.setOnKeyPressed(((Controller2)loader.getController()).onKeyPressed);
	    Main.stage.setScene(scene);
	}
}
