package gui;

import backend.Customer;
import backend.Database;
import backend.Product;
import backend.ShoppingCart;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.When;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import state.AppState;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Optional;
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
    private SimpleBooleanProperty loggedIn;

    @FXML
    Button placeOrderButton, backToStoreButton, backToCartButton, loginButton;

    @FXML
    private TextField firstNameTF, lastNameTF, phoneNumTF, eTF, stTF, unitTF, cityTF,
            stateTF, zipTF, cardNameTF, cardNumTF, expDateTF, cvcTF;

    @FXML
    private Label subLabel, taxLabel, totalLabel, cartSummaryLbl;

    /**
     * Constructor for the checkout controller.
     * @param state
     */
    public CheckoutController(AppState state) {
        this.state = state;
        this.reqFields = new ArrayList<>();
        this.loggedIn = new SimpleBooleanProperty(false);
    }

    @FXML
    public void userLogin(ActionEvent event) {
        if (loggedIn.get()) {
            Alert alert = new SkinnedAlert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("Are you sure you want to logout?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                this.loggedIn.set(false);
                resetCustomerInfo();
            }
        } else {
            Dialog<Customer> login = new LoginPopup(state);
            Optional<Customer> result = login.showAndWait();
            if (result.isPresent()) {
                this.loggedIn.set(true);
                populateCustomerInfo(result.get());
            }
        }
    }

    @FXML
    public void backToShoppingCart(ActionEvent event){
        Parent prevScene = viewStack.pop();
        stage.getScene().setRoot(prevScene);
    }

    @FXML
    public void backToStore(ActionEvent event) {
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

    private void resetCustomerInfo() {
        for (TextField tf : reqFields) {
            tf.setText("");
        }
        // remember to empty the unit field too since that's non-required
        unitTF.setText("");
        // and reset our AppState customer object
        state.setCustomer(null);
    }

    private void populateCustomerInfo(Customer c) {
        firstNameTF.setText(c.getFirstName());
        lastNameTF.setText(c.getLastName());
        phoneNumTF.setText(c.getPhoneNum());
        eTF.setText(c.getEmail());
        stTF.setText(c.getStreet());
        unitTF.setText(c.getUnit());
        cityTF.setText(c.getCity());
        stateTF.setText(c.getState());
        zipTF.setText(String.valueOf(c.getZip()));
        state.setCustomer(c);
    }

    @FXML
    public void placeOrder(ActionEvent event) throws IOException {
        if (state.getCustomer() == null) {
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
        }
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
            Parent childRoot = loader.load(getClass().getResource("/gui/orderConfirm.fxml"));
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
            if (! tf.getStyleClass().contains("error")) {
                tf.getStyleClass().add("error");
            }
        } else {
            if (tf.getStyleClass().contains("error")) {
                tf.getStyleClass().remove("error");
            }
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

    private void initLoginButton() {
        StringBinding loginText = new When(loggedIn)
                .then("Logout").otherwise("Login");
        loginButton.textProperty().bind(loginText);
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
        this.initLoginButton();
        this.displayOrderSummary();
    }
}
