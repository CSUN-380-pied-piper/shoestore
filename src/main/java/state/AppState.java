package state;

import backend.Database;
import gui.SceneLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.Stack;

public class AppState {

    private Stage stage;
    private Database db;
    private SceneLoader loader;
    private Stack<Parent> viewStack;

    public AppState(Stage stage,
                    Database db,
                    Stack<Parent> viewStack) {
        this.stage = stage;
        this.db = db;
        this.viewStack = viewStack;
    }

    public Stage getStage() {
        return stage;
    }

    public Database getDb() {
        return db;
    }

    public SceneLoader getLoader() {
        return loader;
    }

    public void setLoader(SceneLoader loader) {
        if (this.loader == null) {
            this.loader = loader;
        }
    }

    public Stack<Parent> getViewStack() {
        return viewStack;
    }
}
