package gui;

import backend.Database;
import backend.ShoppingCart;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ShoppingCartController {

    private Database db;
    private ShoppingCart cart;

    // fxml ui elements that we need to interact with
    @FXML
    Button HomeButton;

    @FXML
    Button CheckoutButton;

    public ShoppingCartController() {

    }

    public ShoppingCartController(Database db) {
        this.db = db;
    }

    @FXML
    public void initialize() {
        this.cart = new ShoppingCart();
    }
}
