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
    private String ln, fn, pn, e, st, u, c, s, zip, ccn, cn, ed, cc;
    private NumberFormat df;

    @FXML
    Button placeOrderButton, backToStoreButton, backToCartButton;

    @FXML
    private TextField firstNameTF, lastNameTF, phoneNumTF, eTF, stTF, unitTF, cityTF,
            stateTF, zipTF, cardNameTF, cardNumTF, expDateTF, cvcTF;

    @FXML
    private Label subLabel, taxLabel, totalLabel;

    public CheckoutController(AppState state) {
        this.state = state;
    }

    @FXML
    public void backToShoppingCart(ActionEvent event) throws IOException {
        Parent prevScene = viewStack.pop();
        stage.getScene().setRoot(prevScene);
    }

    @FXML
    public void backToStore(ActionEvent event) throws IOException {
        // discard the cart scene, and go home.
        viewStack.pop();
        Parent prevScene = viewStack.pop();
        stage.getScene().setRoot(prevScene);
    }

    @FXML
    public void displayOrderSummary() {
        subLabel.setText(df.format(cart.getSubTotal()));
        taxLabel.setText(df.format(cart.getTax()));
        totalLabel.setText(df.format(cart.getFinalTotal()));
    }

    @FXML
    public void placeOrder(ActionEvent event) throws IOException {
        fn = firstNameTF.getText();
        ln = lastNameTF.getText();
        pn = phoneNumTF.getText();
        e = eTF.getText();
        st = stTF.getText();
        u = unitTF.getText();
        c = cityTF.getText();
        s = stateTF.getText();
        zip = zipTF.getText();
        cn = cardNameTF.getText();
        ccn = cardNameTF.getText();
        ed = expDateTF.getText();
        cc = cvcTF.getText();
        Customer c = new Customer(fn, ln, pn, e, st, u, this.c, s, parseZip(zip));
        state.setCustomer(c);
        // Validate user input
        if (!userInputValid()) {
            return;
        }
        // now switch our scene
        Parent childRoot = loader.load(getClass().getResource("/orderConfirm.fxml"));
        viewStack.push(stage.getScene().getRoot());
        stage.getScene().setRoot(childRoot);
    }

    private int parseZip(String zip) {
        try {
            return Integer.parseInt(zip);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private boolean userInputValid() {
        boolean validTextFields = true;
        if (fn.isEmpty()) {
            // set text field to red if empty
            validTextFields = false;
            firstNameTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (ln.isEmpty()) {
            validTextFields = false;
            lastNameTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (pn.isEmpty()) {
            validTextFields = false;
            phoneNumTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (e.isEmpty()) {
            validTextFields = false;
            eTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (st.isEmpty()) {
            validTextFields = false;
            stTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (c.isEmpty()) {
            validTextFields = false;
            cityTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (s.isEmpty()) {
            validTextFields = false;
            stateTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (zip.isEmpty()) {
            validTextFields = false;
            zipTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (ccn.isEmpty()) {
            validTextFields = false;
            cardNameTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (cn.isEmpty()) {
            validTextFields = false;
            cardNumTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (ed.isEmpty()) {
            validTextFields = false;
            expDateTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (cc.isEmpty()){
            validTextFields = false;
            cvcTF.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        return validTextFields;
    }

    @FXML
    public void initialize() {
        this.db = state.getDb();
        this.loader = state.getLoader();
        this.stage = state.getStage();
        this.viewStack = state.getViewStack();
        this.cart = state.getCart();
        this.df = state.getFormatter();
        this.displayOrderSummary();
    }
}
