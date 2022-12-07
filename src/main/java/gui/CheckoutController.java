package gui;

import backend.Customer;
import backend.Database;
import backend.Product;
import backend.ShoppingCart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import state.AppState;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
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
    private ArrayList<TextField> reqFields;

    @FXML
    Button placeOrderButton, backToStoreButton, backToCartButton;

    @FXML
    private TextField firstNameTF, lastNameTF, phoneNumTF, eTF, stTF, unitTF, cityTF,
            stateTF, zipTF, cardNameTF, cardNumTF, expDateTF, cvcTF;

    @FXML
    private Label subLabel, taxLabel, totalLabel, cartSummaryLbl;

    public CheckoutController(AppState state) {
        this.state = state;
        this.reqFields = new ArrayList<>();
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
        StringBuilder builder = new StringBuilder();
        for (Product p : cart.getContents()) {
            builder.append(p.toString());
            builder.append("\n");
        }
        // append one extra new line for nicer formatting
        builder.append("\n");
        cartSummaryLbl.setText(builder.toString());
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
        ccn = cardNumTF.getText();
        ed = expDateTF.getText();
        cc = cvcTF.getText();
        Customer c = new Customer(fn, ln, pn, e, st, u, this.c, s, parseZip(zip));
        state.setCustomer(c);
        // Validate user input
        if (!(userInputValid())) {
            Alert alert = new SkinnedAlert(Alert.AlertType.WARNING);
            alert.setTitle("Place order cancelled");
            alert.setHeaderText("User input is incomplete or empty.");
            alert.setContentText("Your did not enter required personal details, please " +
                    "fill in the necessary information needed.");
            alert.showAndWait();
        } else {
            // now switch our scene
            Parent childRoot = loader.load(getClass().getResource("/orderConfirm.fxml"));
            viewStack.push(stage.getScene().getRoot());
            stage.getScene().setRoot(childRoot);
        }
    }

    private int parseZip(String zip) {
        try {
            return Integer.parseInt(zip);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void flagTextField(TextField tf, Boolean red) {
        if (red) {
            tf.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        } else {
            tf.setStyle("-fx-text-box-border: green; -fx-focus-color: green;");
        }
    }

    private boolean userInputValid() {
        boolean validTextFields = true;
        for (TextField tf : reqFields) {
            if (tf.getText().isEmpty() || tf.getText().isBlank()) {
                flagTextField(tf, true);
                validTextFields = false;
            } else {
                flagTextField(tf,false);
            }
        }
        return validTextFields;
    }

    private void initReqFieldsList() {
        reqFields.add(firstNameTF);
        reqFields.add(lastNameTF);
        reqFields.add(phoneNumTF);
        reqFields.add(eTF);
        reqFields.add(stTF);
        reqFields.add(cityTF);
        reqFields.add(stateTF);
        reqFields.add(zipTF);
        reqFields.add(cardNameTF);
        reqFields.add(cardNumTF);
        reqFields.add(expDateTF);
        reqFields.add(cvcTF);
    }

    @FXML
    public void initialize() {
        this.db = state.getDb();
        this.loader = state.getLoader();
        this.stage = state.getStage();
        this.viewStack = state.getViewStack();
        this.cart = state.getCart();
        this.df = state.getFormatter();
        this.initReqFieldsList();
        this.displayOrderSummary();
    }
}
