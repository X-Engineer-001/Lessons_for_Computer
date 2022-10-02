package lessons_for_computer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Controller1 implements Initializable{
	@FXML
	ContextMenu _punch;
	@FXML
	Arc _shard;
	@FXML
	Arc _remain;
	Timeline fly1,fly2;
	boolean endFlag=false;
	public void initialize(URL arg0,ResourceBundle arg1) {
		fly1=new Timeline(new KeyFrame(Duration.millis(1),(f)->{
			_shard.setRotate(_shard.getRotate()+1);
			_shard.setLayoutX(_shard.getLayoutX()+0.5);
			_shard.setLayoutY(_shard.getLayoutY()-0.5);
		}));
		fly1.setCycleCount(Timeline.INDEFINITE);
		fly2=new Timeline(new KeyFrame(Duration.millis(1),(f)->{
			_remain.setRotate(_remain.getRotate()-0.2);
			_remain.setLayoutX(_remain.getLayoutX()-0.2);
			_remain.setLayoutY(_remain.getLayoutY()+0.3);
		}));
		fly2.setCycleCount(Timeline.INDEFINITE);
	}
	static boolean mouseInFlag=true;
	public void onPunch(MouseEvent e) throws InterruptedException {
		if(e.getButton()==MouseButton.SECONDARY) {
			if(!endFlag) {
				MouseCage.phase=2;
				endFlag=true;
				_remain.setMouseTransparent(true);
				double rightSplitAngle=Math.asin((250-e.getSceneY())/100)*180/Math.PI;
				double bottumSplitAngle=Math.acos((250-e.getSceneX())/100)*180/Math.PI+180;
				_shard.setStartAngle(bottumSplitAngle);
				_shard.setLength(rightSplitAngle+360-bottumSplitAngle);
				_remain.setStartAngle(rightSplitAngle);
				_remain.setLength(bottumSplitAngle-rightSplitAngle);
				fly1.play();
				fly2.play();
			}
			_punch.show(_remain,e.getScreenX(),e.getScreenY());
			Timeline delay=new Timeline(new KeyFrame(Duration.millis(500),(d)->{
				_punch.hide();
			}));
			delay.setCycleCount(1);
			delay.play();
		}
    }
	public void onExit(MouseEvent e) {
		double distanceFromCenter=Math.pow(Math.pow(e.getSceneX()-250,2)+Math.pow(e.getSceneY()-250,2),0.5);
		if(distanceFromCenter>100) {
			Main.robot.mouseMove(Main.sceneX+250+(e.getSceneX()-250)*98/distanceFromCenter,Main.sceneY+250+(e.getSceneY()-250)*98/distanceFromCenter);
		}
	}
	public void onEnd(MouseEvent e) throws IOException {
		if(endFlag) {
			fly1.stop();
			fly2.stop();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("2.fxml"));
			Scene scene=new Scene(loader.load());
			scene.setOnKeyPressed(((Controller2)loader.getController()).onKeyPressed);
			Main.stage.setScene(scene);
		}
	}
}
