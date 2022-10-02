package lessons_for_computer;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Controller5 implements Initializable{
	@FXML
	Pane _field;
	@FXML
	ImageView _galaxian;
	@FXML
	ImageView _powerup;
	@FXML
	ImageView _hpup;
	@FXML
	ImageView _bg;
	@FXML
	Circle _bullet;
	LinkedList<ImageView> galaxians=new LinkedList<ImageView>();
	LinkedList<Circle> ebullets=new LinkedList<Circle>();
	Timeline run;
	boolean runFlag=true,leftDownFlag=false,dropFlag=false,endFlag=false;
	int spawnTimer=0,fireTimer=0,bgMoveCount=0,killCount=0;
	final double sin15=Math.sin(Math.PI/12),cos15=Math.cos(Math.PI/12);
	public void initialize(URL arg0, ResourceBundle arg1) {
		run=new Timeline(new KeyFrame(Duration.millis(1),(r)->{
			if(_bullet.isVisible()) {
				_bullet.setLayoutX(_bullet.getLayoutX()-sin15*2);
				_bullet.setLayoutY(_bullet.getLayoutY()-cos15*2);
				if(_bullet.getLayoutY()<-5) {
					_bullet.setVisible(false);
				}
			}
			var tbullets=new LinkedList<Circle>(ebullets);
			for(var b:tbullets) {
				b.setLayoutX(b.getLayoutX()+sin15/2);
				b.setLayoutY(b.getLayoutY()+cos15/2);
				if(b.getBoundsInParent().contains(Main.mouseSceneX,Main.mouseSceneY)) {
					End(false);
				}else if(b.getCenterY()+b.getLayoutY()>505) {
					ebullets.remove(b);
					_field.getChildren().remove(b);
				}
			}
			spawnTimer++;
			if(spawnTimer>=500) {
				ImageView newGalaxian=new ImageView(_galaxian.getImage());
				newGalaxian.setLayoutX(-50);
				newGalaxian.setLayoutY(0);
				newGalaxian.setFitWidth(30);
				newGalaxian.setFitHeight(30);
				newGalaxian.setRotate(-15);
				galaxians.push(newGalaxian);
				_field.getChildren().add(newGalaxian);
				spawnTimer=0;
				Move(newGalaxian,0);
			}
			if(fireTimer<450) {
				fireTimer++;
			}else if(leftDownFlag&&!_bullet.isVisible()) {
				_bullet.setLayoutX(Main.mouseSceneX);
				_bullet.setLayoutY(Main.mouseSceneY);
				_bullet.setVisible(true);
				fireTimer=0;
			}
			if(_powerup.isVisible()) {
				_powerup.setLayoutX(_powerup.getLayoutX()+sin15/5);
				_powerup.setLayoutY(_powerup.getLayoutY()+cos15/5);
				if(_powerup.getBoundsInParent().contains(Main.mouseSceneX,Main.mouseSceneY)) {
					End(false);
				}else if(_powerup.getLayoutY()>505) {
					_powerup.setVisible(false);
				}
			}
			if(_hpup.isVisible()) {
				_hpup.setLayoutX(_hpup.getLayoutX()+sin15/5);
				_hpup.setLayoutY(_hpup.getLayoutY()+cos15/5);
				if(_hpup.getBoundsInParent().contains(Main.mouseSceneX,Main.mouseSceneY)) {
					End(true);
				}if(_hpup.getLayoutY()>505) {
					_hpup.setVisible(false);
				}
			}
			_bg.setLayoutX(_bg.getLayoutX()+sin15);
			_bg.setLayoutY(_bg.getLayoutY()+cos15);
			bgMoveCount++;
			if(bgMoveCount>=500) {
				_bg.setLayoutX(-100);
				_bg.setLayoutY(-450);
				bgMoveCount=0;
			}
		}));
		run.setCycleCount(Timeline.INDEFINITE);
		run.play();
	}
	public void onPressed(MouseEvent e) {
		if(e.getButton()==MouseButton.PRIMARY) {
			leftDownFlag=true;
		}
	}
	public void onReleased(MouseEvent e) {
		if(e.getButton()==MouseButton.PRIMARY) {
			leftDownFlag=false;
		}
	}
	private void Move(ImageView galaxian,int phase) {
		Timeline move=new Timeline(new KeyFrame(Duration.millis(4),(e)->{
			switch (phase) {
				case 0:
					galaxian.setLayoutX(galaxian.getLayoutX()+sin15);
					galaxian.setLayoutY(galaxian.getLayoutY()+cos15);
					break;
				case 1:
					galaxian.setLayoutX(galaxian.getLayoutX()+cos15);
					galaxian.setLayoutY(galaxian.getLayoutY()-sin15);
					break;
				case 2:
					galaxian.setLayoutX(galaxian.getLayoutX()+sin15);
					galaxian.setLayoutY(galaxian.getLayoutY()+cos15);
					break;
				case 3:
					galaxian.setLayoutX(galaxian.getLayoutX()-cos15);
					galaxian.setLayoutY(galaxian.getLayoutY()+sin15);
					break;
			}
			if(galaxian.getBoundsInParent().contains(Main.mouseSceneX,Main.mouseSceneY)||galaxian.getLayoutY()>=505) {
				End(false);
			}else if(_bullet.isVisible()&&galaxian.getBoundsInParent().intersects(_bullet.getBoundsInParent())){
				killCount++;
				if(killCount>=3) {
					killCount=0;
					if(!dropFlag&&!_powerup.isVisible()) {
						_powerup.setLayoutX(galaxian.getLayoutX());
						_powerup.setLayoutY(galaxian.getLayoutY());
						_powerup.setVisible(true);
						dropFlag=true;
					}else if(dropFlag&&!_hpup.isVisible()) {
						_hpup.setLayoutX(galaxian.getLayoutX());
						_hpup.setLayoutY(galaxian.getLayoutY());
						_hpup.setVisible(true);
						dropFlag=false;
					}
				}
				_bullet.setVisible(false);
				galaxians.remove(galaxian);
				_field.getChildren().remove(galaxian);
			}
			if(Math.random()<0.002) {
				Circle newBullet=new Circle(galaxian.getLayoutX()+galaxian.getFitWidth()/2,galaxian.getLayoutY()+galaxian.getFitHeight()/2,7,Color.rgb(255,0,0,1));
				ebullets.push(newBullet);
				_field.getChildren().add(newBullet);
			}
		}));
		switch (phase) {
			case 0:
			case 2:
				move.setCycleCount(50);
				break;
			case 1:
				move.setCycleCount((int)(Math.min(galaxian.getLayoutY()/sin15,460)));
				break;
			case 3:
				move.setCycleCount((int)((galaxian.getLayoutX())/cos15));
				break;
		}
		move.setOnFinished(e->{
			if(runFlag&&galaxians.contains(galaxian)) {
				Move(galaxian,(phase+1)%4);
			}
		});
		move.play();
	}
	private void End(boolean success) {
		if(!endFlag) {
			endFlag=true;
			runFlag=false;
			run.stop();
			try {
				if(success) {
					Main.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("6.fxml"))));
				}else {
					Main.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("51.fxml"))));
				}
			} catch (IOException e) {}
		}
	}
}