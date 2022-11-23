package gui;

import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import javafx.scene.Parent;
import state.AppState;

public class SceneLoader {

    private AppState state;
    private FXMLLoader loader;

    public SceneLoader(AppState state) {
        this.state = state;
        this.state.setLoader(this);
        this.loader = new FXMLLoader();
        try {
            loader.setControllerFactory((Class<?> type) -> {
                try {
                    for (Constructor<?> c : type.getConstructors()) {
                        if (c.getParameterCount() == 1) {
                            return c.newInstance(this.state);
                        }
                    }
                    return type.getConstructor().newInstance();
                } catch (Exception ex2) {
                    throw new RuntimeException(ex2);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Parent load(URL url) throws IOException {
        // clear the last used root & controller values before loading
        loader.setRoot(null);
        loader.setController(null);
        // now set location and load the file
        loader.setLocation(url);
        return loader.load();
    }
}
