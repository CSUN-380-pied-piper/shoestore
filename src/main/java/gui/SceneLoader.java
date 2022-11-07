package gui;

import backend.Database;
import javafx.fxml.FXMLLoader;

import java.lang.reflect.Constructor;

public class SceneLoader extends FXMLLoader {


    public SceneLoader(Database db) {
        try {
            this.setControllerFactory((Class<?> type) -> {
                try
                {
                    for (Constructor<?> c : type.getConstructors()) {
                        if (c.getParameterCount() == 1) {
                            return c.newInstance(db);
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
