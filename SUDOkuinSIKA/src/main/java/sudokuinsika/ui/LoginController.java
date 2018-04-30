package sudokuinsika.ui;

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
import sudokuinsika.domain.Game;

public class LoginController extends Controller {

    @FXML
    private TextField username;

    public TextField getUsername() {
        return username;
    }

    @FXML
    private PasswordField password;

    @FXML
    private Button logIn;

    @FXML
    private Hyperlink linkToNewUser;

    @FXML
    private Label error;

    @FXML
    private void logIn(ActionEvent event) throws SQLException {

        setCursor(Cursor.WAIT);
        Game game = getUsersMgmt().logIn(
                username.getText(), password.getText().toCharArray());
        setCursor(Cursor.DEFAULT);

        if (game == null) {
            error.setText("wrong username and/or password! don't mess around");
        } else {
            game.createRiddle(29);
            setGame(game);
            getApp().getGameController().init();
            toGame(event);
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

    public void clear() {
        username.setText("");
        password.setText("");
        error.setText("");
        linkToNewUser.setVisited(false);
        username.requestFocus();
    }
}
