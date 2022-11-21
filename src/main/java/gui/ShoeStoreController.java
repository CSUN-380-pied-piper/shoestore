package gui;

import backend.Database;
import backend.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import state.AppState;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

public class ShoeStoreController {

    private AppState state;
    private Stage stage;
    private Scene scene;
    private Database db;
    private SceneLoader loader;
    private Stack<Parent> viewStack;
    private ShoppingCart sc;
    private DecimalFormat df = new DecimalFormat("####,###,###.00");

    // import fxml ui elements that we need to interact with
    @FXML
    Button CartButton, HeelsBtn, SneakersBtn, SandalsBtn, BootsBtn;

    public ShoeStoreController(AppState state) {
        this.state = state;
    }

    @FXML void addToCart(ActionEvent event) {
        Object source = event.getSource();
        if (source instanceof Button) {
            String btnLabel = ((Button) source).getText();
            System.out.println("Button text: " + btnLabel);
            Product item = db.getProducts(btnLabel).get(0);
            System.out.println("Shoe: " + btnLabel + ", Price: " + df.format(item.getPrice()));
            sc.addItem(item);
        }

    }

    /*
        Note, this is a placeholder/wip method to eventually remove the
        individual methods for each 'scene' button in our fxml files.

        Instead, this method will take the source text/label from the button
        and handle loading the appropriate fxml file, and pushing/popping
        the view stack before switching the scene.
     */
    @FXML
    public void switchScene(ActionEvent event) {
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
        Parent childRoot = loader.load(getClass().getResource("/shoppingCart.fxml"));
        viewStack.push(stage.getScene().getRoot());
        stage.getScene().setRoot(childRoot);
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
        this.db = state.getDb();
        this.loader = state.getLoader();
        this.stage = state.getStage();
        this.viewStack = state.getViewStack();
    }

}
