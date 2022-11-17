package gui;

import backend.Database;
import backend.ShoppingCart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import state.AppState;

import java.io.IOException;
import java.util.Stack;

public class placeOrderController {
    private AppState state;
    private Stage stage;
    private Scene scene;
    private Database db;
    private SceneLoader loader;
    private Stack<Parent> viewStack;

    public placeOrderController(AppState state) {
        this.state = state;
    }




}
