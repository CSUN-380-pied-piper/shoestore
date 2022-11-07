package gui;

import backend.Database;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;

public class SceneLoader extends FXMLLoader {

    public SceneLoader(Database db, Stage stage) {
        try {
            this.setControllerFactory((Class<?> type) -> {
                try
                {
                    for (Constructor<?> c : type.getConstructors()) {
                        if (c.getParameterCount() == 1) {
                            return c.newInstance(db);
                        } else if (c.getParameterCount() == 2) {
                            return c.newInstance(db, this);
                        } else if (c.getParameterCount() == 3) {
                            return c.newInstance(db, this, stage);
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

}
