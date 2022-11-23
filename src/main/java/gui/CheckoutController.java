package gui;

import backend.Customer;
import backend.Database;
import backend.ShoppingCart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import state.AppState;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Stack;

public class CheckoutController {
    private AppState state;
    private ShoppingCart cart;
    private Stage stage;
    private Scene scene;
    private Database db;
    private SceneLoader loader;
    private Stack<Parent> viewStack;
    private String lastName, firstName, phoneNum, email, street, unit, city, s, zip;
    private NumberFormat df;


    @FXML
    Button placeOrderButton, backToStoreButton, backToCartButton;

    @FXML
    private TextField firstNameTF, lastNameTF, contactNumTF, eTF, stTF, unitTF, cityTF, stateTF, zipTF;

    @FXML
    private Label subLabel, taxLabel, totalLabel;

    public CheckoutController(AppState state) {
        this.state = state;
    }

    @FXML
    public void backToShoppingCart(ActionEvent event) throws IOException {
        Parent root = loader.load(getClass().getResource("/shoppingCart.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void backToStore(ActionEvent event) throws IOException {
        Parent root = loader.load(getClass().getResource("/shoeStore.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void displayOrderSummary() {
        subLabel.setText(df.format(cart.getSubTotal()));
        taxLabel.setText(df.format(cart.getTax()));
        totalLabel.setText(df.format(cart.getFinalTotal()));
    }

    @FXML
    public void placeOrder(ActionEvent event) throws IOException {
        Parent root = loader.load(getClass().getResource("/orderConfirm.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        firstName = firstNameTF.getText();
        lastName = lastNameTF.getText();
        phoneNum = contactNumTF.getText();
        email = eTF.getText();
        street = stTF.getText();
        unit = unitTF.getText();
        city = cityTF.getText();
        s = stateTF.getText();
        zip = zipTF.getText();
        Customer c = new Customer(firstName, lastName, phoneNum, email, street, unit, city, s,
                Integer.parseInt(zip));
        state.setCustomer(c);
    }

    @FXML
    public void initialize() {
        this.db = state.getDb();
        this.loader = state.getLoader();
        this.stage = state.getStage();
        this.cart = state.getCart();
        this.df = state.getFormatter();
        this.displayOrderSummary();
    }
}
