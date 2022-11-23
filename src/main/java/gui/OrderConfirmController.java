package gui;

import backend.Customer;
import backend.Database;
import backend.ShoppingCart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private String finalReceipt;

    @FXML
    TextArea orderConfTA;

    public OrderConfirmController(AppState state) {
        this.state = state;
    }

    @FXML
    public void backToStore(ActionEvent event) throws IOException {
        Parent root = loader.load(getClass().getResource("/shoeStore.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void displayEmailSim() {
    	finalReceipt = "";
    	Customer c = state.getCustomer();
    	finalReceipt = c.orderConfirmationCustomerInfo();
        ShoppingCart sc = state.getCart();
        finalReceipt = finalReceipt + "\n\n" + sc.ReceiptInTextArea()
        				+ "\nTax: $" + sc.getTax()
        				+ "\nTotal: $" + sc.getFinalTotal();

        orderConfTA.setText(finalReceipt);
    }

    private void displayEmail() {
        Customer c = state.getCustomer();
        orderConfTA.setText("Order Confirmation\n\n" +
                "Hi "
                + c.getFirstName() + " " + c.getLastName() + "!\n"
                + "Here are the details of your order: \n\n"
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
        displayEmail();
    }
}
