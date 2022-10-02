module lessons_for_computer {
	requires java.desktop;
	requires transitive com.github.kwhat.jnativehook;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	opens lessons_for_computer to javafx.fxml;
	exports lessons_for_computer;
}