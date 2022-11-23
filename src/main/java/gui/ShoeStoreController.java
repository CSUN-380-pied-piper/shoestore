package gui;

import backend.Database;
import backend.Product;
import backend.ShoppingCart;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import state.AppState;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Stack;

public class ShoeStoreController {

    private AppState state;
    private Stage stage;
    private Scene scene;
    private Database db;
    private SceneLoader loader;
    private Stack<Parent> viewStack;
    private ShoppingCart sc;
    private DecimalFormat df = new DecimalFormat("####,###,###.00");
    private HashMap<String, URL> sceneMap;
    private ObservableList<Product> products;

    // import fxml ui elements that we need to interact with
    @FXML
    Button CartButton;
    @FXML
    private TableView<Product> productList;
    @FXML
    private TableColumn<Product, Product> addBtnCol;
    @FXML
    private TableColumn<Product, String> nameCol;
    @FXML
    private TableColumn<Product, Number> priceCol;
    @FXML
    private TableColumn<Product, Number> qtyCol;
    @FXML
    private TableColumn<Product, Product> sizeCol;

    public ShoeStoreController(AppState state) {
        this.state = state;
    }

    @FXML void addToCart(ActionEvent event) {
        Object source = event.getSource();
        if (source instanceof Button) {
            String btnLabel = ((Button) source).getText();
            Product item = db.getProducts(btnLabel).get(0);
            sc.addItem(item);
        }

    }

    void addToCart(Product prod, Integer qty) {
        for (int i = 0; i < qty; i++) {
            sc.addItem(prod);
        }
    }

    /*
        Note, this is a placeholder/wip method to eventually remove the
        individual methods for each 'scene' button in our fxml files.

        Instead, this method will take the source text/label from the button
        and handle loading the appropriate fxml file, and pushing/popping
        the view stack before switching the scene.
     */
    @FXML
    public void switchScene(ActionEvent event) throws IOException {
        Object source = event.getSource();
        if (source instanceof Button) {
            System.out.println(((Button) source).getText());
            System.out.println(((Button) source).getId());
            Parent childRoot = loader.load(sceneMap.get(((Button) source).getText()));
            viewStack.push(stage.getScene().getRoot());
            stage.getScene().setRoot(childRoot);
        }
    }

    private void populateSceneMap() {
        this.sceneMap.put(CartButton.getText(), getClass().getResource("/shoppingCart.fxml"));
    }

    private void initProductList() {
        // get all the products currently available from the database
        this.products = FXCollections.observableArrayList(db.getProducts("%"));
        productList.setItems(products);
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProp());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProp());
        sizeCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        qtyCol.setCellValueFactory(cellData -> new SimpleIntegerProperty());
        addBtnCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        sizeCol.setCellFactory(cell -> new TableCell<>() {
            private final ChoiceBox box = new ChoiceBox();
            @Override
            protected void updateItem(Product prod, boolean empty) {
                super.updateItem(prod, empty);
                if (empty || prod == null) {
                    setGraphic(null);
                    return;
                }
                box.setItems(prod.getSizes());
                setGraphic(box);
            }
        });
        qtyCol.setCellFactory(cell -> new TableCell<>() {
            private final ChoiceBox box = new ChoiceBox();
            @Override
            protected void updateItem(Number qty, boolean empty) {
                super.updateItem(qty, empty);
                if (empty || qty == null) {
                    setGraphic(null);
                    return;
                }

                box.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9));
                setGraphic(box);
            }
        });
        addBtnCol.setCellFactory(cell -> new TableCell<>() {
            private final Button addBtn = new Button("+");
            @Override
            protected void updateItem(Product prod, boolean empty) {
                super.updateItem(prod, empty);
                if (empty || prod == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(addBtn);
                addBtn.setOnAction(event -> addToCart(prod, 1));
            }
        });
    }

    @FXML
    public void initialize() {
        this.db = state.getDb();
        this.loader = state.getLoader();
        this.stage = state.getStage();
        this.viewStack = state.getViewStack();
        this.sceneMap = state.getSceneMap();
        this.sc = state.getCart();
        this.populateSceneMap();
        initProductList();
        //db.getCustomer("johndoe@nowhere.fake");
    }

}
