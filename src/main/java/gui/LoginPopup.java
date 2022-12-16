package gui;

import backend.Customer;
import backend.Database;
import backend.HashedPass;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import state.AppState;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.HexFormat;

public class LoginPopup extends Dialog<Customer> {

    @FXML
    private DialogPane loginPane;
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    private final Database db;

    public LoginPopup(AppState state) {
        this.db = state.getDb();

        String title = "User Login";
        this.setTitle(title);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/loginPopup.fxml"));
        loader.setController(this);
        try {
            loader.load();
            Button okBtn = (Button) loginPane.lookupButton(ButtonType.OK);
            this.setDialogPane(loginPane);
            // disable the ok/login button when username or password empty
            BooleanBinding disable = Bindings.or(userField.textProperty().isEmpty(),
                    passField.textProperty().isEmpty());
            okBtn.disableProperty().bind(disable);
            this.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    String email = userField.getText();
                    Customer c = db.getCustomer(email);
                    HashedPass hp = db.getHash(email);
                    String hashed = encryptPass(passField.getText(), hp.getSalt());
                    if (hp.equals(hashed)) {
                        return c;
                    } else {
                        return null;
                    }
                }
                return null;
            });
            // request focus on our username/email field
            Platform.runLater(userField::requestFocus);
        } catch (Exception ex) {
        }
    }


    private String encryptPass(String input, String salt) {
        try {
            HexFormat hex = HexFormat.of();
            byte[] seed = hex.parseHex(salt);
            //SecureRandom random = new SecureRandom();
            //random.nextBytes(seed);
            KeySpec spec = new PBEKeySpec(input.toCharArray(), seed, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            String hashHex = HexFormat.of().formatHex(hash);
            String saltHex = HexFormat.of().formatHex(seed);
            //System.out.println("hash: " + hashHex);
            //System.out.println("salt: " + saltHex);
            return hashHex + ":" + saltHex;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
