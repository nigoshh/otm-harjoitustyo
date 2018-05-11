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

/**
 * FXML Controller for the new user scene.
 */
public class NewUserController extends Controller {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPw;
    @FXML
    private TextField email;
    @FXML
    private Button create;
    @FXML
    private Label errorUsername;
    @FXML
    private Label errorPw;
    @FXML
    private Label errorEmail;
    @FXML
    private Hyperlink logInLink;

    /**
     * Cleans up the new user scene.
     */
    public void clear() {
        username.setText("");
        password.setText("");
        repeatPw.setText("");
        email.setText("");
        clearErrors();
        logInLink.setVisited(false);
        errorUsername.requestFocus();
    }

    @FXML
    private void create(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {

        clearErrors();

        boolean usernameLengthOK = getUsersMgmt().checkUsernameLength(username.getText());
        boolean passwordLengthOK = getUsersMgmt().checkPasswordLength(password.getText());
        boolean emailLengthOK = getUsersMgmt().checkEmailLength(email.getText());

        if (!usernameLengthOK || !passwordLengthOK || !emailLengthOK) {
            if (!usernameLengthOK) {
                errorUsername.setText(errorUsernameLength);
            }
            if (!passwordLengthOK) {
                errorPw.setText(errorPwLength);
            }
            if (!emailLengthOK) {
                errorEmail.setText(errorEmailLength);
            }
        } else {
            if (password.getText().equals(repeatPw.getText())) {
                if (getUsersMgmt().createUser(username.getText(),
                        password.getText().toCharArray(), email.getText())) {
                    toLogin(event);
                    username.setText("");
                    email.setText("");
                } else {
                    errorUsername.setText(errorUsernameTaken);
                }
            } else {
                errorPw.setText(errorPwFieldsMatch);
            }
        }
        password.setText("");
        repeatPw.setText("");
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

    private void clearErrors() {
        errorUsername.setText("");
        errorPw.setText("");
        errorEmail.setText("");
    }
}
