package gui;

import backend.Database;
import backend.Product;
import backend.ShoppingCart;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.SVGPath;
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
    private ObservableList<Integer> qtyList;

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
    private TableColumn<Product, Number> priceCol, qtyCol, sizeCol;

    public ShoeStoreController(AppState state) {
        this.state = state;
    }

    private Boolean sizeAvailable(Product p, Double size) {
        return p.getSizes().stream().anyMatch(s -> s.equals(size));
    }

    private void addToCart(Product prod) {
        int qty = prod.getLastQty();
        double size = prod.getLastSize();
        System.out.println("qty: " + qty);
        System.out.println("size: " + size);
        if (sizeAvailable(prod, size)) {
            for (int i = 0; i < qty; i++) {
                sc.addItem(prod);
            }
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
        this.qtyList = FXCollections.observableArrayList();
        for (int i = 1; i < 10; i++) {
            qtyList.add(i);
        }
        productList.setItems(products);
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProp());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProp());
        sizeCol.setCellValueFactory(cellData -> cellData.getValue().lastSizeProp());
        qtyCol.setCellValueFactory(cellData -> cellData.getValue().lastQtyProp());
        addBtnCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        sizeCol.setCellFactory(cell -> new TableCell<>() {
            private final ChoiceBox<Double> box = new ChoiceBox();
            @Override
            protected void updateItem(Number prod, boolean empty) {
                super.updateItem(prod, empty);
                if (empty || prod == null) {
                    setGraphic(null);
                    return;
                }
                Product p = this.getTableRow().getItem();
                box.setItems(p.getSizes());
                setGraphic(box);
                box.setOnAction(event -> {
                    p.lastSizeProp().set(box.getValue());
                });
            }
        });
        qtyCol.setCellFactory(cell -> new TableCell<>() {
            private final ChoiceBox<Integer> box = new ChoiceBox();
            @Override
            protected void updateItem(Number qty, boolean empty) {
                super.updateItem(qty, empty);
                if (empty || qty == null) {
                    setGraphic(null);
                    return;
                }
                Product p = this.getTableRow().getItem();
                box.setItems(qtyList);
                setGraphic(box);
                box.setOnAction(event -> {
                    p.lastQtyProp().set(box.getValue());
                });
            }
        });
        addBtnCol.setCellFactory(cell -> new TableCell<>() {
            private final Button addBtn = new Button("Add ");
            @Override
            protected void updateItem(Product prod, boolean empty) {
                super.updateItem(prod, empty);
                if (empty || prod == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(addBtn);
                SVGPath svg = new SVGPath();
                svg.setContent(Glyphs.ADD());
                addBtn.setContentDisplay(ContentDisplay.RIGHT);
                addBtn.setGraphic(svg);
                addBtn.setOnAction(event -> {
                    addToCart(prod);
                });
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
