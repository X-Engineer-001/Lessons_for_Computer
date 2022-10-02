package lessons_for_computer;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import java.awt.AWTException;
import java.awt.Robot;

public class MouseCage implements NativeMouseInputListener {
	public static Robot robot;
	public static final double bound = 2;
	public static int phase=0;
	static double nativeScreenWidth=-1,nativeScreenHeight=-1;
	public MouseCage() {
		try {
			GlobalScreen.registerNativeHook();
		}catch (NativeHookException ex) {
			System.err.println(ex.getMessage());
		}
		try {
			MouseCage.robot=new Robot();
		}catch (AWTException ex){
			System.err.println(ex.getMessage());
		}
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
		calibrate();
	}
	public static void calibrate() {
		while(nativeScreenWidth<0||nativeScreenHeight<0) {
			double x=Main.robot.getMouseX(),y=Main.robot.getMouseY();
			Main.robot.mouseMove(Main.screenWidth,Main.screenHeight);
			Main.robot.mouseMove(x,y);
		}
		phase = 1;
	}
	public void check(NativeMouseEvent e) {
		double checkX=e.getX()*Main.screenWidth/nativeScreenWidth,checkY=e.getY()*Main.screenHeight/nativeScreenHeight;
		Main.mouseScreenX=checkX;
		Main.mouseScreenY=checkY;
		Main.mouseSceneX=checkX-Main.sceneX;
		Main.mouseSceneY=checkY-Main.sceneY;
		if(phase==0) {
			nativeScreenWidth=Math.max(nativeScreenWidth,e.getX());
			nativeScreenHeight=Math.max(nativeScreenHeight,e.getY());
		}else if(phase==1) {
			double x=checkX-Main.sceneX,y=checkY-Main.sceneY;
			double distanceFromCenter=Math.pow(Math.pow(x-250,2)+Math.pow(y-250,2),0.5);
			if(distanceFromCenter>100) {
				robot.mouseMove((int)(Main.sceneX+250+(x-250)*98/distanceFromCenter),(int)(Main.sceneY+250+(y-250)*98/distanceFromCenter));
			}
		}else if(phase==2){
			double x=checkX,y=checkY;
			if(x<=Main.sceneX) {
				x=Main.sceneX+bound;
			}else if(x>=Main.sceneX+Main.stage.getWidth()) {
				x=Main.sceneX+Main.stage.getWidth()-bound;
			}
			if(y<=Main.sceneY) {
				y=Main.sceneY+bound;
			}else if(y>=Main.sceneY+Main.stage.getHeight()) {
				y=Main.sceneY+Main.stage.getHeight()-bound;
			}
			if(x!=checkX||y!=checkY) {
				robot.mouseMove((int)x,(int)y);
			}
		}
	}
	public void nativeMouseMoved(NativeMouseEvent e) {
		check(e);
	}
	public void nativeMouseDragged(NativeMouseEvent e) {
		check(e);
	}
}