package gui;

import backend.ShoppingCart;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ShoppingCartController {

    private ShoppingCart cart;

    // fxml ui elements that we need to interact with
    @FXML
    Button HomeButton;

    @FXML
    Button CheckoutButton;

    @FXML
    public void initialize() {
        this.cart = new ShoppingCart();
    }
}
