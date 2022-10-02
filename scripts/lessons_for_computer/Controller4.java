package lessons_for_computer;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.util.Duration;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller4 implements Initializable{
	@FXML
	Circle _end1;
	@FXML
	Circle _end2;
	@FXML
	Circle _end3;
	Point2D shadow1,shadow2,real;
	Timeline shadow,move,check;
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.robot.mouseMove(Main.sceneX+250,Main.sceneY+250);
		real = new Point2D(Main.sceneX+250,Main.sceneY+250);
		shadow1=new Point2D(Main.sceneX+350,Main.sceneY+250);
		shadow2=new Point2D(Main.sceneX+150,Main.sceneY+250);
		shadow=new Timeline(new KeyFrame(Duration.millis(70),(e)->{
			Main.robot.mouseMove(shadow1);
		}),new KeyFrame(Duration.millis(100),(e)->{
			Main.robot.mouseMove(real);
		}),new KeyFrame(Duration.millis(170),(e)->{
			Main.robot.mouseMove(shadow2);
		}),new KeyFrame(Duration.millis(200),(e)->{
			Main.robot.mouseMove(real);
		}));
		shadow.setCycleCount(Timeline.INDEFINITE);
		shadow.play();
		move=new Timeline(new KeyFrame(Duration.millis(1),(e)->{
			Point2D test=new Point2D(Main.mouseScreenX,Main.mouseScreenY);
			if(test.distance(shadow1)>50&&test.distance(shadow2)>50&&test.distance(real)<50) {
				real=test;
			}
		}));
		move.setCycleCount(Timeline.INDEFINITE);
		move.play();
		check=new Timeline(new KeyFrame(Duration.millis(100),(e)->{
			boolean got1=real.add(-Main.sceneX,-Main.sceneY).distance(_end1.getLayoutX(),_end1.getLayoutY())<=10||shadow1.add(-Main.sceneX,-Main.sceneY).distance(_end1.getLayoutX(),_end1.getLayoutY())<=10||shadow2.add(-Main.sceneX,-Main.sceneY).distance(_end1.getLayoutX(),_end1.getLayoutY())<=10;
			boolean got2=real.add(-Main.sceneX,-Main.sceneY).distance(_end2.getLayoutX(),_end2.getLayoutY())<=10||shadow1.add(-Main.sceneX,-Main.sceneY).distance(_end2.getLayoutX(),_end2.getLayoutY())<=10||shadow2.add(-Main.sceneX,-Main.sceneY).distance(_end2.getLayoutX(),_end2.getLayoutY())<=10;
			boolean got3=real.add(-Main.sceneX,-Main.sceneY).distance(_end3.getLayoutX(),_end3.getLayoutY())<=10||shadow1.add(-Main.sceneX,-Main.sceneY).distance(_end3.getLayoutX(),_end3.getLayoutY())<=10||shadow2.add(-Main.sceneX,-Main.sceneY).distance(_end3.getLayoutX(),_end3.getLayoutY())<=10;
			int gotCount=0;
			if(got1) {
				gotCount++;
			}
			if(got2) {
				gotCount++;
			}
			if(got3) {
				gotCount++;
			}
			Color got=Color.rgb(0,0,0,1),left=Color.rgb(0,0,0,1);
			switch (gotCount){
				case 0:
					left=Color.rgb(0,0,0,1);
					break;
				case 1:
					got=Color.rgb(0,0,0,1);
					left=Color.rgb(0,150,0,1);
					break;
				case 2:
					got=Color.rgb(0,150,0,1);
					left=Color.rgb(0,255,0,1);
					break;
				case 3:
					move.stop();
					shadow.stop();
					Main.robot.mouseMove(real);
					check.stop();
					try {
						Main.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("5.fxml"))));
					} catch (IOException ex) {}
			}
			_end1.setFill(got1?got:left);
			_end2.setFill(got2?got:left);
			_end3.setFill(got3?got:left);
		}));
		check.setCycleCount(Timeline.INDEFINITE);
		check.play();
	}
	public EventHandler<KeyEvent> onKeyReleased = (e)->{
		if(e.getCode()==KeyCode.TAB) {
			move.stop();
			shadow.stop();
			Point2D temp=real;
			real=shadow1;
			shadow1=shadow2;
			shadow2=temp;
			Main.robot.mouseMove(real);
			shadow.play();
			move.play();
		}
	};
}