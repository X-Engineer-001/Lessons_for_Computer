package lessons_for_computer;
import java.io.IOException;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.robot.Robot;
import javafx.stage.*;

public class Main extends Application {
	public static Robot robot;
	public static Stage stage,newStage;
	public static double screenWidth,screenHeight,mouseSceneX,mouseSceneY,mouseScreenX,mouseScreenY,sceneX,sceneY;
	public static Timeline cursor,check;
	public static void main(String[] args) {
		screenWidth=java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		screenHeight=java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		launch(args);
	}
	public void start(Stage mainStage) throws IOException{
		robot=new Robot();
		mainStage.setTitle("Lessons for Computer");
		mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("0.fxml"))));
		mainStage.initStyle(StageStyle.UNDECORATED);
		mainStage.setResizable(false);
		mainStage.setAlwaysOnTop(true);
		stage=mainStage;
		mainStage.show();
	}
	static public void newWindow(Scene scene,String title,double x,double y) {
		newStage=new Stage();
		newStage.setTitle(title);
		newStage.setScene(scene);
		newStage.setX(x);
		newStage.setY(y);
		newStage.initStyle(StageStyle.UNDECORATED);
		newStage.setResizable(false);
		newStage.setAlwaysOnTop(true);
		newStage.show();
	}
}