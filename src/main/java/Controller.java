import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    int count = 0;

    @FXML
    private TextField cartCount;

    @FXML
    private TextField cartTotal;

    @FXML
    void addToCart(ActionEvent event) {
        String buttonSource = event.getSource().toString();
        int temp = count + 1;
        cartCount.setText(String.valueOf(temp));
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        cartCount.setText("0");
    }
}
