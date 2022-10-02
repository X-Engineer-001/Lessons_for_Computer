package lessons_for_computer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.util.Duration;

public class Controller6 implements Initializable{
	@FXML
	ImageView _pipes;
	@FXML
	ImageView _cloud;
	@FXML
	ImageView _ground;
	@FXML
	Label _title;
	@FXML
	ImageView _under;
	final double collideMouseX=100,x=Main.sceneX+collideMouseX,sceneY=Main.sceneY,pipeWidth=25;
	double y=sceneY+50,yv=0,speed=3,collideMouseY;
	Timeline run;
	boolean underFlag=false;
	public void initialize(URL arg0, ResourceBundle arg1) {
		run=new Timeline(new KeyFrame(Duration.millis(10),(r)->{
			_pipes.setLayoutX(_pipes.getLayoutX()-speed);
			_under.setLayoutX(_under.getLayoutX()-speed);
			if(speed>3) {
				speed-=0.5;
			}else if(speed<3) {
				speed+=0.5;
			}
			if(_pipes.getLayoutX()<-pipeWidth) {
				_pipes.setLayoutX(500);
				_under.setLayoutX(500);
				_pipes.setLayoutY(Math.random()*-300);
			}
			_cloud.setLayoutX(_cloud.getLayoutX()-2);
			if(_cloud.getLayoutX()<=-200) {
				_cloud.setLayoutX(0);
			}
			_ground.setLayoutX(_ground.getLayoutX()-5);
			if(_ground.getLayoutX()<=-50) {
				_ground.setLayoutX(0);
			}
			yv+=0.1;
			y+=yv;
			Main.robot.mouseMove(x,y-(underFlag?499:0));
			if(underFlag) {
				collideMouseY=y-500-sceneY;
				double collidePipesX=_under.getLayoutX();
				if(collideMouseX>=collidePipesX&&collideMouseX<=collidePipesX+pipeWidth) {
					run.stop();
					try {
						Main.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("61.fxml"))));
					} catch (IOException ex) {}
				}else if(collideMouseY<0) {
					_pipes.setVisible(true);
					_under.setVisible(false);
					_cloud.setVisible(true);
					_ground.setVisible(false);
					_title.setVisible(true);
					underFlag=false;
				}else if(collideMouseY>465) {
					run.stop();
					FXMLLoader loader=new FXMLLoader(getClass().getResource("7.fxml"));
					try {
						Scene scene=new Scene(loader.load());
						scene.setOnKeyPressed(((Controller7)loader.getController()).onKeyPressed);
					    Main.stage.setScene(scene);
					} catch (IOException ex) {}
				}
			}else {
				collideMouseY=y-sceneY;
				double collidePipesX1=_pipes.getLayoutX(),collidePipesX2=_pipes.getLayoutX()+pipeWidth;
				double collidePipesY1=_pipes.getLayoutY()+315,collidePipesY2=_pipes.getLayoutY()+485;
				if(collideMouseY<=20||(collideMouseX>=collidePipesX1&&collideMouseX<=collidePipesX2&&(collideMouseY<=collidePipesY1||collideMouseY>=collidePipesY2))) {
					run.stop();
					try {
						Main.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("61.fxml"))));
					} catch (IOException ex) {}
				}else if(collideMouseY>500) {
					_pipes.setVisible(false);
					_under.setVisible(true);
					_cloud.setVisible(false);
					_ground.setVisible(true);
					_title.setVisible(false);
					underFlag=true;
				}
			}
		}));
		run.setCycleCount(Timeline.INDEFINITE);
		run.play();
	}
	public void onMoved(MouseEvent e) {
		if(Main.mouseScreenX<x-2) {
			speed=1;
		}else if(Main.mouseScreenX>x+2) {
			speed=10;
		}
		Main.robot.mouseMove(x,y-(underFlag?499:0));
	}
	public void onFly(MouseEvent e) {
		if(e.getButton()==MouseButton.PRIMARY) {
			yv=-4.5;
		}
	}
}
