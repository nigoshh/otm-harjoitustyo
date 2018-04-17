package sudokuinsika.ui;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import sudokuinsika.domain.User;

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
    private void create(ActionEvent event) throws SQLException {
        User user = new User(username.getText(), password.getText(),
                email.getText());
        if (getUserDao().save(user)) {
            toLogin(event);
        } else {
            error.setText("username already in use, choose another one mate");
        }
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
        logInLink.setVisited(false);
    }
}
