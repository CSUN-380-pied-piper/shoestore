package gui;

import backend.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ShoeStoreController {

    
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Database db;

    // import fxml ui elements that we need to interact with
    @FXML
    Button CartButton, HeelsBtn, SneakersBtn, SandalsBtn, BootsBtn;

    @FXML void addToCart(ActionEvent event) {
        Object source = event.getSource();
        if (source instanceof Button) {
            String btnLabel = ((Button) source).getText();
        }

    }

    @FXML
    public void switchToCheckout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/checkout.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToShoppingCart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/shoppingCart.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToOrderPlaced(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/orderPlaced.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToShoeStore(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/shoeStore.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        this.db = new Database();
    }

}
