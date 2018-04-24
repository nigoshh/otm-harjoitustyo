package sudokuinsika.ui;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class NewUserController extends Controller {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField repeatPW;

    @FXML
    private TextField email;

    @FXML
    private Label error;

    @FXML
    private Button create;

    @FXML
    private Hyperlink logInLink;

    @FXML
    private void create(ActionEvent event)
            throws SQLException, NoSuchAlgorithmException {

        if (username.getLength() == 0
                || password.getLength() == 0
                || repeatPW.getLength() == 0) {

            error.setText("please fill in all required fields (all but email)");

        } else if (password.getLength() < 10
                || password.getLength() > 1000
                || username.getLength() > 230
                || email.getLength() > 230) {

            error.setText("username length must be between 1 and 230 characters"
                    + "\npassword length must be between 10 and 1000 characters"
                    + "\nemail length must be between 0 and 230 characters");

        } else {

            if (password.getText().equals(repeatPW.getText())) {

                setCursor(Cursor.WAIT);
                if (getUsersMgmt().createUser(username.getText(),
                        password.getText().toCharArray(), email.getText())) {
                    error.setText("");
                    toLogin(event);
                } else {
                    error.setText(
                            "username already in use, choose another one mate");
                }
                setCursor(Cursor.DEFAULT);

            } else {
                error.setText(
                        "\"password\" and \"repeat password\" don't match");
            }
        }

        username.setText("");
        password.setText("");
        repeatPW.setText("");
    }

    @FXML
    private void createUserPressingEnter(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                create.fire();
                break;
            default:
                break;
        }
    }

    public void clear() {
        username.setText("");
        password.setText("");
        repeatPW.setText("");
        email.setText("");
        error.setText("");
        logInLink.setVisited(false);
        create.requestFocus();
    }
}
