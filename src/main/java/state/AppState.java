package state;

import backend.Database;
import gui.SceneLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type App state.
 */
public class AppState {

    private Stage stage;
    private Database db;
    private SceneLoader loader;
    private Stack<Parent> viewStack;

    /**
     * Instantiates a new App state.
     *
     * @param stage     the main stage of our application
     * @param db        the db object that connects to our postgres db
     * @param viewStack the view stack that keeps track of scenes so
     *                      that users can navigate back & forward
     */
    public AppState(Stage stage,
                    Database db,
                    Stack<Parent> viewStack) {
        this.stage = stage;
        this.db = db;
        this.viewStack = viewStack;
    }

    /**
     * Gets stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Gets db.
     *
     * @return the db
     */
    public Database getDb() {
        return db;
    }

    /**
     * Gets our custom fxml/scene loader object.
     *
     * @return the loader
     */
    public SceneLoader getLoader() {
        return loader;
    }

    /**
     * Sets our custom fxml/scene loader object.
     *
     * @param loader the loader
     */
    public void setLoader(SceneLoader loader) {
        if (this.loader == null) {
            this.loader = loader;
        }
    }

    /**
     * Gets view stack.
     *
     * @return the view stack
     */
    public Stack<Parent> getViewStack() {
        return viewStack;
    }
}
