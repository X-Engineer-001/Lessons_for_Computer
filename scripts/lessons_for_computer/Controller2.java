package lessons_for_computer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Controller2 implements Initializable{
	@FXML
	Line _edge;
	@FXML
	Polygon _side1;
	@FXML
	Polygon _side2;
	@FXML
	Label _save;
	@FXML
	Label _load;
	int slice=0;
	static boolean saveFlag=false;
	boolean[] sides=new boolean[4];
	static boolean[] saveSides=new boolean[4];
	static Timeline saved,loaded,fail,success,slicer;
	public void initialize(URL arg0,ResourceBundle arg1) {
		if(saveFlag) {
			sides=saveSides;
			loaded=new Timeline(new KeyFrame(Duration.millis(0),(e)->{
				_load.setVisible(true);
			}),new KeyFrame(Duration.millis(500),(e)->{
				_load.setVisible(false);
			}));
			loaded.setCycleCount(1);
			loaded.play();
		}else {
			for(int i=0;i<4;i++) {
				sides[i]=Math.random()<0.5;
			}
			saved=new Timeline(new KeyFrame(Duration.millis(0),(e)->{
				_save.setVisible(true);
			}),new KeyFrame(Duration.millis(500),(e)->{
				_save.setVisible(false);
			}));
			saved.setCycleCount(1);
		}
		fail=new Timeline(new KeyFrame(Duration.millis(0),(d)->{
			slicer.stop();
		}),new KeyFrame(Duration.millis(300),(d)->{
			try {
				Main.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("21.fxml"))));
			} catch (IOException ex) {}
		}));
		fail.setCycleCount(1);
		success=new Timeline(new KeyFrame(Duration.millis(0),(d)->{
			slicer.stop();
		}),new KeyFrame(Duration.millis(300),(d)->{
			if(slice<3) {
				slice++;
				Arrange();
				slicer.play();
			}else {
				try {
					FXMLLoader loader=new FXMLLoader(getClass().getResource("3.fxml"));
					Scene scene=new Scene(loader.load());
				    scene.setOnKeyPressed(((Controller3)loader.getController()).onKeyPressed);
				    Main.stage.setScene(scene);
				} catch (IOException e1) {}
			}
		}));
		success.setCycleCount(1);
		Arrange();
		slicer=new Timeline(new KeyFrame(Duration.millis(1),(e)->{
			if(slice==0||slice==2) {
				_edge.setEndX(_edge.getEndX()+0.8);
			}else if(slice==1) {
				_edge.setEndX(_edge.getEndX()-0.8);
			}
			if(slice==0||slice==1||slice==3) {
				_edge.setEndY(_edge.getEndY()+0.8);
			}
			if(_edge.getEndY()>500||_edge.getEndX()>500) {
				boolean isMouseInSide1=mouseInSide1();
				if(slice>=3) {
					if(saveFlag) {
						if(sides[3]) {
							_side1.setFill(Color.rgb(255,0,0,0.7));
							_side2.setFill(Color.rgb(0,255,0,0.7));
						}else {
							_side2.setFill(Color.rgb(255,0,0,0.7));
							_side1.setFill(Color.rgb(0,255,0,0.7));
						}
						if(isMouseInSide1^sides[slice]) {
							success.play();
						}else {
							fail.play();
						}
					}else {
						if(isMouseInSide1) {
							_side1.setFill(Color.rgb(255,0,0,0.7));
							_side2.setFill(Color.rgb(0,255,0,0.7));
						}else {
							_side2.setFill(Color.rgb(255,0,0,0.7));
							_side1.setFill(Color.rgb(0,255,0,0.7));
						}
						fail.play();
					}
				}else {
					if(sides[slice]) {
						_side1.setFill(Color.rgb(255,0,0,0.7));
					}else {
						_side2.setFill(Color.rgb(255,0,0,0.7));
					}
					if(isMouseInSide1^sides[slice]) {
						success.play();
					}else {
						fail.play();
					}
				}
			}
		}));
		slicer.setCycleCount(Timeline.INDEFINITE);
		slicer.play();
	}
	public EventHandler<KeyEvent> onKeyPressed = (e)->{
		if(e.getCode()==KeyCode.S&&e.isControlDown()) {
			saveFlag=true;
			saveSides=sides;
			saved.play();
		}
	};
	private void Arrange() {
		switch (slice){
		case 0:
			_edge.setStartX(0);
			_edge.setStartY(0);
			_edge.setEndX(0);
			_edge.setEndY(0);
			_side1.setFill(Color.rgb(0,0,0,0));
			_side2.setFill(Color.rgb(0,0,0,0));
			_side1.getPoints().setAll(0d,0d,500d,0d,500d,500d);
			_side2.getPoints().setAll(0d,0d,0d,500d,500d,500d);
			break;
		case 1:
			_edge.setStartX(500);
			_edge.setStartY(0);
			_edge.setEndX(500);
			_edge.setEndY(0);
			_side1.setFill(Color.rgb(0,0,0,0));
			_side2.setFill(Color.rgb(0,0,0,0));
			_side1.getPoints().setAll(500d,0d,0d,0d,0d,500d);
			_side2.getPoints().setAll(500d,0d,500d,500d,0d,500d);
			break;
		case 2:
			_edge.setStartX(0);
			_edge.setStartY(250);
			_edge.setEndX(0);
			_edge.setEndY(250);
			_side1.setFill(Color.rgb(0,0,0,0));
			_side2.setFill(Color.rgb(0,0,0,0));
			_side1.getPoints().setAll(0d,250d,0d,0d,500d,0d,500d,250d);
			_side2.getPoints().setAll(0d,250d,0d,500d,500d,500d,500d,250d);
			break;
		case 3:
			_edge.setStartX(250);
			_edge.setStartY(0);
			_edge.setEndX(250);
			_edge.setEndY(0);
			_side1.setFill(Color.rgb(0,0,0,0));
			_side2.setFill(Color.rgb(0,0,0,0));
			_side1.getPoints().setAll(250d,0d,0d,0d,0d,500d,250d,500d);
			_side2.getPoints().setAll(250d,0d,500d,0d,500d,500d,250d,500d);
			break;
		}
	}
	private boolean mouseInSide1() {
		switch (slice) {
			case 0:
				return Main.mouseSceneX-Main.mouseSceneY>0;
			case 1:
				return Main.mouseSceneX+Main.mouseSceneY<500;
			case 2:
				return Main.mouseSceneY<250;
			case 3:
				return Main.mouseSceneX<250;
			default:
				return true;
		}
	}
}