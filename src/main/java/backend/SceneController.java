package shoeStore;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	// Reference on how to use scenebuilder: https://github.com/jocelynmallon/GraphingCalculator/blob/master/src/main/java/dev/StylishNerds/GraphingCalculator/Calculator.java
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchToProductScene(ActionEvent event) throws IOException{
		/*
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        	Scene scene = new Scene(root);
		*/
	}
	
	public void switchToShoppingCartScene(ActionEvent event) throws IOException{
		
	}
	
	public void switchToCheckOutScene(ActionEvent event) throws IOException{
		
	}
	
	public void switchToConfirmationScene(ActionEvent event) throws IOException{
		
	}
	
}
