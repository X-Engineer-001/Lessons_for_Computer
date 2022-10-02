package lessons_for_computer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Controller3 implements Initializable{
	@FXML
	ImageView _back;
	@FXML
	Label _capture;
	@FXML
	Rectangle _end;
	int count=0;
	WritableImage screen=new WritableImage((int)Main.screenWidth,(int)Main.screenHeight);
	Timeline captured;
	public void initialize(URL arg0, ResourceBundle arg1) {
		captured=new Timeline(new KeyFrame(Duration.millis(0),(e)->{
			Main.robot.getScreenCapture(screen,0,0,Main.screenWidth,Main.screenHeight);
			_capture.setVisible(true);
		}),new KeyFrame(Duration.millis(250),(e)->{
			_back.setImage(screen);
		}),new KeyFrame(Duration.millis(500),(e)->{
			_capture.setVisible(false);
			try {
				check(Main.mouseScreenX,Main.mouseScreenY);
			} catch (IOException ex) {}
		}));
		captured.setCycleCount(1);
		double halfWidth=Main.screenWidth/2,halfHeight=Main.screenHeight/2;
		Main.stage.setWidth(halfWidth);
		Main.stage.setHeight(halfHeight);
		_back.setFitWidth(halfWidth);
		_back.setFitHeight(halfHeight);
		Pane window31=new Pane(new Rectangle(50,50,Color.rgb(0,255,0,1)));
		Main.newWindow(new Scene(window31),"new",0,0);
		Platform.runLater(()->{
			_capture.setLayoutX((halfWidth-_capture.getWidth())/2);
			_capture.setLayoutY((halfHeight-_capture.getHeight())/2);
		});
	}
	public EventHandler<KeyEvent> onKeyPressed = (e)->{
		if(e.getCode()==KeyCode.PRINTSCREEN) {
			captured.play();
		}
	};
	public void check(double x,double y) throws IOException {
		if(Main.robot.getPixelColor(x,y).equals(Color.rgb(0,255,0,1))) {
			Main.newStage.close();
			Main.stage.setWidth(500);
			Main.stage.setHeight(500);
			FXMLLoader loader=new FXMLLoader(getClass().getResource("4.fxml"));
			Scene scene=new Scene(loader.load());
		    scene.setOnKeyReleased(((Controller4)loader.getController()).onKeyReleased);
		    Main.stage.setScene(scene);
		}
	}
	public void onMoved(MouseEvent e) throws IOException {
		check(e.getScreenX(),e.getScreenY());
	}
}