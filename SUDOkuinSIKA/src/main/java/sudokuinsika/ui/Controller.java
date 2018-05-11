package sudokuinsika.ui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sudokuinsika.domain.Game;
import sudokuinsika.domain.UsersManagement;

/**
 * Base FXML Controller.
 */
public class Controller implements Initializable {

    private MainApp app;

    protected final String errorUsernameTaken = "username already taken, choose another one mate";
    protected final String errorUsernameLength = "username length must be between 1 and 230 characters";
    protected final String errorPwLength = "password length must be between 10 and 1000 characters";
    protected final String errorPwFieldsMatch = "\"password\" and \"repeat password\" didn't match";
    protected final String errorNewPwFieldsMatch = "\"new password\" and \"repeat new password\" didn't match";
    protected final String errorEmailLength = "email length must be between 0 and 230 characters";

    @FXML
    protected void toLogin(ActionEvent event) {
        app.getLoginController().clear();
        app.stageLoginScene();
    }

    @FXML
    protected void toNewUser(ActionEvent event) {
        app.getNewUserController().clear();
        app.stageNewUserScene();
    }

    @FXML
    protected void toGame(ActionEvent event) {
        app.getGameController().clear();
        app.stageGameScene();
    }

    @FXML
    protected void toGameFromLogin(ActionEvent event) {
        app.getGameController().clearForLogin();
        toGame(event);
    }

    @FXML
    protected void toScores(ActionEvent event) throws SQLException {
        app.getScoresController().clear();
        app.stageScoresScene();
    }

    @FXML
    protected void toSettings(ActionEvent event) {
        app.getSettingsController().clear();
        app.stageSettingsScene();
    }

    protected void setCustomIcon(Alert alert) {
        app.setCustomIcon((Stage) alert.getDialogPane().getScene().getWindow());
    }

    protected Game getGame() {
        return getUsersMgmt().getGame();
    }

    protected UsersManagement getUsersMgmt() {
        return app.getUsersMgmt();
    }

    protected void setApp(MainApp app) {
        this.app = app;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
