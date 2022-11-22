package gui;

import backend.Database;
import backend.Product;
import backend.ShoppingCart;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import state.AppState;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

public class ShoppingCartController {

    private AppState state;
    private Stage stage;

    private Scene scene;
    private Database db;
    private SceneLoader loader;
    private Stack<Parent> viewStack;
    private ShoppingCart cart;
    private ObservableList<Product> cartItemsList;
    private DecimalFormat df = new DecimalFormat("####,###,###.00");

    // fxml ui elements that we need to interact with
    @FXML
    Button HomeButton;

    @FXML
    Button CheckoutButton;

    @FXML
    Label taxLabel, totalLabel;

    @FXML
    private TableView<Product> cartList;
    @FXML
    private TableColumn<Product, String> prodNameCol;
    @FXML
    private TableColumn<Product, Number> prodPriceCol;

    public ShoppingCartController(AppState state) {
        this.state = state;
    }

    @FXML public void backToStore(ActionEvent event) {
        Parent prevScene = viewStack.pop();
        stage.getScene().setRoot(prevScene);
    }

    @FXML
    public void checkout(ActionEvent event) throws IOException {
        Parent root = loader.load(getClass().getResource("/checkout.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the cell value factory for our shopping cart
     * table view columns.
     */
    private void initCartList() {
        prodNameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        prodPriceCol.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPrice()));
    }

    /**
     * Initializes the observable properties for our computed
     * tax and total display labels.
     */
    private void initTaxAndTotal() {
        SimpleStringProperty taxProp = new SimpleStringProperty(df.format(cart.getTax()));
        SimpleStringProperty totalProp = new SimpleStringProperty(df.format(cart.getFinalTotal()));
        taxLabel.textProperty().bind(taxProp);
        totalLabel.textProperty().bind(totalProp);
    }

    @FXML
    public void initialize() {
        if (this.cart == null) {
            this.cart = state.getCart();
        }
        this.db = state.getDb();
        this.loader = state.getLoader();
        this.stage = state.getStage();
        this.viewStack = state.getViewStack();
        this.cartItemsList = FXCollections.observableArrayList(cart.getContents());
        this.cartList.setItems(cartItemsList);
        this.initCartList();
        this.initTaxAndTotal();
    }
}
