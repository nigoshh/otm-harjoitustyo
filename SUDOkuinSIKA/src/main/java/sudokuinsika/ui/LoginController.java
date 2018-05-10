package sudokuinsika.ui;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import sudokuinsika.domain.Game;

/**
 * FXML Controller for the login scene.
 */
public class LoginController extends Controller {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button logIn;
    @FXML
    private Label error;
    @FXML
    private Hyperlink linkToNewUser;

    /**
     * Cleans up the login scene.
     */
    public void clear() {
        username.setText("");
        password.setText("");
        error.setText("");
        linkToNewUser.setVisited(false);
        username.requestFocus();
    }

    @FXML
    private void logIn(ActionEvent event) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        Game game = getUsersMgmt().logIn(username.getText(), password.getText().toCharArray());
        if (game == null) {
            error.setText("wrong username and/or password! don't mess around");
        } else {
            toGameFromLogin(event);
        }
        username.setText("");
        password.setText("");
    }

    @FXML
    private void logInPressingEnter(KeyEvent event) throws SQLException {
        switch (event.getCode()) {
            case ENTER:
                logIn.fire();
                break;
            default:
                break;
        }
    }
}
