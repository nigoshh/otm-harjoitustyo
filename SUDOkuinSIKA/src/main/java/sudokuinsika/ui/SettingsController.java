package sudokuinsika.ui;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SettingsController extends Controller {

    @FXML
    private TextField currentPW;

    @FXML
    private TextField newUsername;

    @FXML
    private TextField newPW;

    @FXML
    private TextField repeatNewPW;

    @FXML
    private TextField newEmail;

    @FXML
    private Hyperlink gameLink;

    @FXML
    private Label instructions;

    @FXML
    private Label errorCurrentPW;

    @FXML
    private Label errorNewUsername;

    @FXML
    private Label errorNewPW;

    @FXML
    private Label errorNewEmail;

    @FXML
    private void changeUsername(ActionEvent event) throws SQLException {
        clearErrors();
        clearNewPWFields();
        if (checkCurrentPW()) {
            if (getUsersMgmt().checkUsernameLength(newUsername.getText())) {
                if (getUsersMgmt().changeUsername(newUsername.getText())) {
                    displaySuccessMessage("username");
                } else {
                    errorNewUsername.setText("this username is taken, choose another one");
                }
            } else {
                errorNewUsername.setText("username length must be between 1 and 230 characters");
            }
        } else {
            displayCurrentPasswordError();
        }
    }

    @FXML
    private void changePassword(ActionEvent event) throws NoSuchAlgorithmException, SQLException {
        clearErrors();
        if (checkCurrentPW()) {
            if (newPW.getText().equals(repeatNewPW.getText())) {
                if (getUsersMgmt().checkPasswordLength(newPW.getText())) {
                    char[] newPassword = newPW.getText().toCharArray();
                    clearNewPWFields();
                    getUsersMgmt().changePassword(newPassword);
                    displaySuccessMessage("password");
                } else {
                    errorNewPW.setText("password length must be between 10 and 1000 characters");
                }
            } else {
                errorNewPW.setText("\"new password\" and \"repeat new password\" didn't match");
            }
        } else {
            displayCurrentPasswordError();
        }
        clearNewPWFields();
    }

    @FXML
    private void changeEmail(ActionEvent event) throws SQLException {
        clearErrors();
        clearNewPWFields();
        if (checkCurrentPW()) {
            if (getUsersMgmt().checkEmailLength(newEmail.getText())) {
                getUsersMgmt().changeEmail(newEmail.getText());
                displaySuccessMessage("email");
            } else {
                errorNewEmail.setText("email length must be between 0 and 230 characters");
            }
        } else {
            displayCurrentPasswordError();
        }
    }

    @FXML
    private void deleteUser(ActionEvent event) throws SQLException {
        clearErrors();
        clearNewPWFields();
        if (checkCurrentPW()) {
            displayWarning();
        } else {
            displayCurrentPasswordError();
        }
    }

    public void clear() {
        currentPW.setText("");
        newUsername.setText("");
        newPW.setText("");
        repeatNewPW.setText("");
        newEmail.setText("");
        gameLink.setVisited(false);
        instructions.requestFocus();
        clearErrors();
    }

    private void clearErrors() {
        errorCurrentPW.setText("");
        errorNewUsername.setText("");
        errorNewPW.setText("");
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
        alert.setHeaderText(null);
        String message = "you sure you wanna obliterate all your data forever mate?";
        alert.setContentText(message);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            getUsersMgmt().deleteUser();
            toLogin(new ActionEvent());
        }

        if (alert.getResult() == ButtonType.NO) {
            alert.close();
        }
    }

    private void displayCurrentPasswordError() {
        errorCurrentPW.setText("wrong password!");
    }

    private boolean checkCurrentPW() {
        boolean ret = getUsersMgmt().checkPassword(currentPW.getText().toCharArray());
        currentPW.setText("");
        return ret;
    }

    private void clearNewPWFields() {
        newPW.setText("");
        repeatNewPW.setText("");
    }
}
