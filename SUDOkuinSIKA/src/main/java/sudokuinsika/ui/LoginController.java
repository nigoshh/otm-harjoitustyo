package sudokuinsika.ui;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sudokuinsika.domain.User;

public class LoginController extends Controller {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Hyperlink linkToNewUser;

    @FXML
    private Label error;

    @FXML
    private void login(ActionEvent event) throws SQLException {
        User user = userDao.findOne(username.getText());
        if (user == null) {
            error.setText("wrong username and/or password! don't mess around");
        } else {
            setGame(user);
            toGame(event);
        }
    }

    public void clear() {
        username.setText("");
        password.setText("");
        error.setText("");
    }
}
