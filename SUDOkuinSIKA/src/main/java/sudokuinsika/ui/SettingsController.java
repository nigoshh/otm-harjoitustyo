package sudokuinsika.ui;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller for the settings scene.
 */
public class SettingsController extends Controller {

    @FXML
    private TextField currentPw;
    @FXML
    private TextField newUsername;
    @FXML
    private TextField newPw;
    @FXML
    private TextField repeatNewPw;
    @FXML
    private TextField newEmail;
    @FXML
    private Label errorCurrentPw;
    @FXML
    private Label errorNewUsername;
    @FXML
    private Label errorNewPw;
    @FXML
    private Label errorNewEmail;
    @FXML
    private Label instructions;
    @FXML
    private Hyperlink gameLink;

    /**
     * Cleans up the settings scene.
     */
    public void clear() {
        currentPw.setText("");
        newUsername.setText("");
        newPw.setText("");
        repeatNewPw.setText("");
        newEmail.setText("");
        gameLink.setVisited(false);
        instructions.requestFocus();
        clearErrors();
    }

    @FXML
    private void changeUsername(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        clearErrors();
        clearNewPwFields();
        if (checkCurrentPw()) {
            if (getUsersMgmt().checkUsernameLength(newUsername.getText())) {
                if (getUsersMgmt().changeUsername(newUsername.getText())) {
                    displaySuccessMessage("username");
                } else {
                    errorNewUsername.setText(errorUsernameTaken);
                }
            } else {
                errorNewUsername.setText(errorUsernameLength);
            }
        } else {
            displayCurrentPasswordError();
        }
    }

    @FXML
    private void changePassword(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        clearErrors();
        if (checkCurrentPw()) {
            if (getUsersMgmt().checkPasswordLength(newPw.getText())) {
                if (newPw.getText().equals(repeatNewPw.getText())) {
                    char[] newPassword = newPw.getText().toCharArray();
                    clearNewPwFields();
                    getUsersMgmt().changePassword(newPassword);
                    displaySuccessMessage("password");
                } else {
                    errorNewPw.setText(errorNewPwFieldsMatch);
                }
            } else {
                errorNewPw.setText(errorPwLength);
            }
        } else {
            displayCurrentPasswordError();
        }
        clearNewPwFields();
    }

    @FXML
    private void changeEmail(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        clearErrors();
        clearNewPwFields();
        if (checkCurrentPw()) {
            if (getUsersMgmt().checkEmailLength(newEmail.getText())) {
                getUsersMgmt().changeEmail(newEmail.getText());
                displaySuccessMessage("email");
            } else {
                errorNewEmail.setText(errorEmailLength);
            }
        } else {
            displayCurrentPasswordError();
        }
    }

    @FXML
    private void deleteUser(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        clearErrors();
        clearNewPwFields();
        if (checkCurrentPw()) {
            displayWarning();
        } else {
            displayCurrentPasswordError();
        }
    }

    private boolean checkCurrentPw() throws NoSuchAlgorithmException, InvalidKeySpecException {
        boolean ret = getUsersMgmt().checkPassword(currentPw.getText().toCharArray());
        currentPw.setText("");
        return ret;
    }

    private void displayCurrentPasswordError() {
        errorCurrentPw.setText("wrong password!");
    }

    private void clearNewPwFields() {
        newPw.setText("");
        repeatNewPw.setText("");
    }

    private void clearErrors() {
        errorCurrentPw.setText("");
        errorNewUsername.setText("");
        errorNewPw.setText("");
        errorNewEmail.setText("");
    }

    private void displaySuccessMessage(String updated) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("cheers mate");
        alert.setHeaderText(null);
        String message = updated + " updated successfully";
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayWarning() throws SQLException {
        String title= "destroy everything you've worked for";
        Alert alert = new Alert(AlertType.CONFIRMATION, title, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(null);
        String message = "you sure you wanna obliterate all your data forever mate?";
        alert.setContentText(message);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            getUsersMgmt().deleteUser();
            toLogin(new ActionEvent());
        }
    }
}
