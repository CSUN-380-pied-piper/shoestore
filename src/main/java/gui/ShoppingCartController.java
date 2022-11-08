package gui;

import backend.Database;
import backend.ShoppingCart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import state.AppState;
import java.util.Stack;

public class ShoppingCartController {

    private AppState state;
    private Stage stage;
    private Database db;
    private SceneLoader loader;
    private Stack<Parent> viewStack;
    private ShoppingCart cart;

    // fxml ui elements that we need to interact with
    @FXML
    Button HomeButton;

    @FXML
    Button CheckoutButton;

    public ShoppingCartController(AppState state) {
        this.state = state;
    }

    @FXML public void backToStore(ActionEvent event) {
        Parent prevScene = viewStack.pop();
        stage.getScene().setRoot(prevScene);
    }

    @FXML
    public void initialize() {
        if (this.cart == null) {
            this.cart = new ShoppingCart();
        }
        this.db = state.getDb();
        this.loader = state.getLoader();
        this.stage = state.getStage();
        this.viewStack = state.getViewStack();
    }
}
