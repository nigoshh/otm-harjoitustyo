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
    private Label errorUsername;

    @FXML
    private Label errorPW;

    @FXML
    private Label errorEmail;

    @FXML
    private Button create;

    @FXML
    private Hyperlink logInLink;

    @FXML
    private void create(ActionEvent event)
            throws SQLException, NoSuchAlgorithmException {
        clearErrors();
        boolean usernameLengthOK = getUsersMgmt().checkUsernameLength(username.getText());
        boolean passwordLengthOK = getUsersMgmt().checkPasswordLength(password.getText());
        boolean emailLengthOK = getUsersMgmt().checkEmailLength(email.getText());

        if (!usernameLengthOK || !passwordLengthOK || !emailLengthOK) {
            if (!usernameLengthOK) {
                errorUsername.setText(errorUsernameLength);
            }
            if (!passwordLengthOK) {
                errorPW.setText(errorPWLength);
            }
            if (!emailLengthOK) {
                errorEmail.setText(errorEmailLength);
            }
        } else {
            if (password.getText().equals(repeatPW.getText())) {
                setCursor(Cursor.WAIT);
                if (getUsersMgmt().createUser(username.getText(),
                        password.getText().toCharArray(), email.getText())) {
                    toLogin(event);
                } else {
                    errorUsername.setText(errorUsernameTaken);
                }
                setCursor(Cursor.DEFAULT);
            } else {
                errorPW.setText(errorPWFieldsMatch);
            }
        }

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
        clearErrors();
        logInLink.setVisited(false);
        errorUsername.requestFocus();
    }

    private void clearErrors() {
        errorUsername.setText("");
        errorPW.setText("");
        errorEmail.setText("");
    }
}
