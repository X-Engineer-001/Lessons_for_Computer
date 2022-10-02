package lessons_for_computer;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Controller7 implements Initializable{
	@FXML
	Pane _field;
	@FXML
	ImageView _trash;
	@FXML
	ImageView _select;
	@FXML
	ContextMenu _context;
	@FXML
	Label _title;
	@FXML
	ImageView _pass;
	int trashCount=500;
	LinkedList<ImageView> trashes=new LinkedList<ImageView>();
	public void initialize(URL arg0, ResourceBundle arg1) {
		for(int i=0;i<trashCount;i++) {
			Trash();
		}
	}
	private void Trash() {
		double[] prePos={0,0};
		ImageView newTrash=new ImageView(_trash.getImage());
		newTrash.setLayoutX(Math.random()*350);
		newTrash.setLayoutY(50+Math.random()*300);
		newTrash.setOnMouseClicked(e->{
			if(e.getButton()==MouseButton.SECONDARY) {
				_context.show(newTrash,e.getScreenX(),e.getScreenY());
			}
		});
		newTrash.setOnMousePressed(e->{
			if(e.getButton()==MouseButton.PRIMARY) {
				prePos[0]=e.getSceneX();
				prePos[1]=e.getSceneY();
			}
			if(newTrash.getImage().equals(_trash.getImage())&&!(e.isControlDown()||e.isShiftDown())) {
				for(var t:trashes) {
					t.setImage(_trash.getImage());
				}
			}
			newTrash.setImage(_select.getImage());
		});
		newTrash.setOnMouseDragged(e->{
			if(e.getButton()==MouseButton.PRIMARY) {
				double x=e.getSceneX()-prePos[0],y=e.getSceneY()-prePos[1];
				for(var t:trashes) {
					if(t.getImage().equals(_select.getImage())) {
						t.setLayoutX(Math.max(0,Math.min(350,t.getLayoutX()+x)));
						t.setLayoutY(Math.max(0,Math.min(350,t.getLayoutY()+y)));
					}
				}
				prePos[0]=e.getSceneX();
				prePos[1]=e.getSceneY();
			}
		});
		trashes.add(newTrash);
		_field.getChildren().add(newTrash);
	}
	public void onDelete(ActionEvent e) {
		Delete();
	}
	public void onDuplicate(ActionEvent e) {
		Duplicate();
	}
	public void onPressField(MouseEvent e) {
		if(!(e.isControlDown()||e.isShiftDown())) {
			for(var t:trashes) {
				t.setImage(_trash.getImage());
			}
		}
	}
	public EventHandler<KeyEvent> onKeyPressed = (e)->{
		_context.hide();
		if(e.getCode()==KeyCode.A&&e.isControlDown()) {
			for(var t:trashes) {
				t.setImage(_select.getImage());
			}
		}else if(e.getCode()==KeyCode.DELETE) {
			Delete();
		}
	};
	private void Delete() {
		for(var t:trashes) {
			if(t.getImage().equals(_select.getImage())) {
				_field.getChildren().remove(t);
				trashCount--;
			}
		}
		if(trashCount<=0) {
			End();
		}
	}
	private void Duplicate() {
		int duplicateCount=0;
		for(var t:trashes) {
			if(t.getImage().equals(_select.getImage())) {
				duplicateCount++;
			}
		}
		for(int i=0;i<duplicateCount;i++) {
			Trash();
			trashCount++;
		}
	}
	private void End() {
		MouseCage.phase=3;
		_title.setText("Congratulation!!!");
		_pass.setVisible(true);
		Timeline end=new Timeline(new KeyFrame(Duration.millis(10),(f)->{
			_pass.setFitWidth(_pass.getFitWidth()-47);
			_pass.setFitHeight(_pass.getFitHeight()-23);
		}));
		end.setCycleCount(90);
		end.play();
		_field.setOnMouseExited(e->{
			Main.stage.close();
			System.exit(0);
		});
	}
}