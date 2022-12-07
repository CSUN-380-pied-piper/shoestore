package gui;

import backend.Database;
import backend.Product;
import backend.ShoppingCart;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import state.AppState;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

public class ShoppingCartController {

    private AppState state;
    private Stage stage;

    private Scene scene;
    private Database db;
    private SceneLoader loader;
    private Stack<Parent> viewStack;
    private ShoppingCart cart;
    private NumberFormat df;

    // fxml ui elements that we need to interact with
    @FXML
    Button HomeButton, CheckoutButton, EmptyCartBtn;
    @FXML
    Label subtotalLabel, taxLabel, totalLabel;
    @FXML
    private TableView<Product> cartList;
    @FXML
    private TableColumn<Product, String> prodNameCol, prodPriceCol, prodSizeCol;
    @FXML
    private TableColumn<Product, Product> delProdCol;

    public ShoppingCartController(AppState state) {
        this.state = state;
    }

    @FXML public void backToStore(ActionEvent event) {
        Parent prevScene = viewStack.pop();
        stage.getScene().setRoot(prevScene);
    }

    @FXML
    public void checkout(ActionEvent event) throws IOException {
        if (cart.getContents().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Checkout cancelled");
            alert.setHeaderText("Shopping cart is empty.");
            alert.setContentText("Your shopping cart is currently empty, please add" +
                    "some items to your cart before checking out.");
            alert.showAndWait();
        } else {
            Parent childRoot = loader.load(getClass().getResource("/checkout.fxml"));
            viewStack.push(stage.getScene().getRoot());
            stage.getScene().setRoot(childRoot);
        }
    }

    @FXML
    public void emptyCart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Empty Shopping Cart?");
        alert.setHeaderText("Confirm empty cart");
        alert.setContentText("Do you want to empty your shopping cart?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ArrayList<Product> temp = new ArrayList<>(cart.getContents());
            for (Product p : temp) {
                this.removeItem(p);
            }
        }
    }

    /**
     * Initializes the cell value factory for our shopping cart
     * table view columns.
     */
    private void initCartList() {
        cartList.setPlaceholder(new Label("Cart is empty"));
        prodNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProp());
        prodSizeCol.setCellValueFactory(cellData -> cellData.getValue().lastSizeProp().asString());
        prodPriceCol.setCellValueFactory(cellData -> cellData.getValue().priceProp().asString("$%,.2f"));
        delProdCol.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        delProdCol.setCellFactory(cell -> new TableCell<>() {
            private final Button delBtn = new Button("Remove  ");
            @Override
            protected void updateItem(Product prod, boolean empty) {
                super.updateItem(prod, empty);
                if (empty || prod == null) {
                    setGraphic(null);
                    return;
                }
                SVGPath svg = new SVGPath();
                svg.setContent(Glyphs.DEL());
                delBtn.setContentDisplay(ContentDisplay.RIGHT);
                delBtn.setGraphic(svg);
                setGraphic(delBtn);
                delBtn.prefWidthProperty().bind(cell.widthProperty());
                delBtn.setOnAction(event -> removeItem(prod));
            }
        });
    }

    private void removeItem(Product prod) {
        cart.removeItem(prod);
    }

    /**
     * Initializes the observable properties for our computed
     * tax and total display labels.
     */
    private void initBindings() {
        subtotalLabel.textProperty().bind(cart.subTotalProperty().asString("$%,.2f"));
        taxLabel.textProperty().bind(cart.taxProperty().asString("$%,.2f"));
        totalLabel.textProperty().bind(cart.finalTotalProperty().asString("$%,.2f"));
        EmptyCartBtn.disableProperty().bind(cart.isEmptyProperty().emptyProperty());
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
        this.df = state.getFormatter();
        this.cartList.setItems(cart.getContents());
        this.initCartList();
        this.initBindings();
    }
}
