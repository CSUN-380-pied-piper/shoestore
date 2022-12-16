package gui;

import backend.Customer;
import backend.Database;
import backend.ShoppingCart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import state.AppState;
import java.io.IOException;
import java.util.Stack;

public class OrderConfirmController {
    private AppState state;
    private Stage stage;
    private Scene scene;
    private Database db;
    private SceneLoader loader;
    private Stack<Parent> viewStack;

    @FXML
    private TextArea orderConfTA;

    public OrderConfirmController(AppState state) {
        this.state = state;
    }

    @FXML
    public void backToStore(ActionEvent event) throws IOException {
        // we know we've gone home > cart > checkout > conf
        // therefor we need to pop scenes 3 times
        viewStack.pop();
        viewStack.pop();
        Parent prevScene = viewStack.pop();
        stage.getScene().setRoot(prevScene);
    }

    public String OrderConfirmationText() {
    	String finalReceipt = "";
    	Customer c = state.getCustomer();
    	finalReceipt = c.orderConfirmationCustomerInfo();
        ShoppingCart sc = state.getCart();
        finalReceipt = finalReceipt + "\n\n" + sc.ReceiptInTextArea()
        				+ "\nTax: $" + sc.getTax()
        				+ "\nTotal: $" + sc.getFinalTotal();

		return finalReceipt;
    }

    private void displayEmail() {
        Customer c = state.getCustomer();
        orderConfTA.setText("Order Confirmation\n\n" +
                "Hi "
                + c.getFirstName() + " " + c.getLastName() + "!\n"
                + "Here are the details of your order: \n\n"
                + state.getCart().ReceiptInTextArea() + "\n\n"
                + "Shipping Address: \n"
                + c.getStreet() + " " + c.getUnit() + "\n"
                + c.getCity() + " " + c.getState() + " " + c.getZip() + "\n\n"
                +"Email: "
                + c.getEmail() + "\n" +
                "Phone Number: " + c.getPhoneNum());
    }

    @FXML
    public void initialize() {
        this.db = state.getDb();
        this.loader = state.getLoader();
        this.stage = state.getStage();
        this.viewStack = state.getViewStack();
        displayEmail();
    }
}
