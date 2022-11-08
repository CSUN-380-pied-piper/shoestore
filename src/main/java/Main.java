import backend.Database;
import gui.SceneLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import state.AppState;
import java.util.Stack;

public class Main extends Application {

    private AppState state;

    @Override
    public void start(Stage stage) throws Exception {
        state = new AppState(stage, new Database(), new Stack<>());
        SceneLoader loader = new SceneLoader(state);
        Parent root = loader.load(getClass().getResource("/shoeStore.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}