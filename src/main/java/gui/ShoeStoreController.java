package gui;

import backend.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class ShoeStoreController {

    
    private Stage stage;
    private Scene scene;
    private Database db;
    private SceneLoader loader;

    // import fxml ui elements that we need to interact with
    @FXML
    Button CartButton, HeelsBtn, SneakersBtn, SandalsBtn, BootsBtn;

    public ShoeStoreController() {
    }

    public ShoeStoreController(Database db, SceneLoader loader, Stage stage) {
        this.db = db;
        this.loader = loader;
        this.stage = stage;
    }

    @FXML void addToCart(ActionEvent event) {
        Object source = event.getSource();
        if (source instanceof Button) {
            String btnLabel = ((Button) source).getText();
        }

    }

    @FXML public void switchScene(ActionEvent event) {
        Object source = event.getSource();
        if (source instanceof Button) {
            String btnId = ((Button) source).getId();
            switch (btnId) {
                case "CartButton": ;break;
                default: break;
            }
        }
    }

    @FXML
    public void switchToCheckout(ActionEvent event) throws IOException {
        Parent root = loader.load(getClass().getResource("/checkout.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToShoppingCart(ActionEvent event) throws IOException {
        Parent root = loader.load(getClass().getResource("/shoppingCart.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToOrderPlaced(ActionEvent event) throws IOException {
        Parent root = loader.load(getClass().getResource("/orderPlaced.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToShoeStore(ActionEvent event) throws IOException {
        Parent root = loader.load(getClass().getResource("/shoeStore.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
    }

}
