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
	
	@FXML public void switchToProductScene(ActionEvent event) throws IOException{
		/*
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/shoestore.fxml"));
        	Scene scene = new Scene(root);
		        stage.setScene(scene);
        	stage.setMinWidth(350);
        	stage.setMinHeight(510);
        	stage.setResizable(true);
        	stage.initStyle(StageStyle.UNIFIED);
        	stage.setTitle("Product");
		*/
	}
	
	@FXML public void switchToShoppingCartScene(ActionEvent event) throws IOException{
		/*
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/shoppingCart.fxml"));
        	Scene scene = new Scene(root);
		        stage.setScene(scene);
        	stage.setMinWidth(350);
        	stage.setMinHeight(510);
        	stage.setResizable(true);
        	stage.initStyle(StageStyle.UNIFIED);
        	stage.setTitle("Product");
		*/
	}
	
	@FXML public void switchToCheckOutScene(ActionEvent event) throws IOException{
		
	}
	
	@FXML public void switchToConfirmationScene(ActionEvent event) throws IOException{
		
	}
	
}
