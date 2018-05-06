package sudokuinsika.ui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import sudokuinsika.domain.Game;
import sudokuinsika.domain.UsersManagement;

public class Controller implements Initializable {

    private MainApp app;
    private UsersManagement usersMgmt;

    protected final String errorUsernameTaken = "username already taken, choose another one mate";
    protected final String errorUsernameLength = "username length must be between 1 and 230 characters";
    protected final String errorPWLength = "password length must be between 10 and 1000 characters";
    protected final String errorPWFieldsMatch = "\"password\" and \"repeat password\" didn't match";
    protected final String errorNewPWFieldsMatch = "\"new password\" and \"repeat new password\" didn't match";
    protected final String errorEmailLength = "email length must be between 0 and 230 characters";

    protected void setApp(MainApp app) {
        this.app = app;
    }

    protected void setUsersMgmt(UsersManagement usersMgmt) {
        this.usersMgmt = usersMgmt;
    }

    @FXML
    protected void toLogin(ActionEvent event) {
        app.clearLoginScene();
        app.stageLoginScene();
    }

    @FXML
    protected void toNewUser(ActionEvent event) {
        app.clearNewUserScene();
        app.stageNewUserScene();
    }

    @FXML
    protected void toGame(ActionEvent event) {
        app.clearGameScene();
        app.stageGameScene();
    }

    @FXML
    protected void toScores(ActionEvent event) throws SQLException {
        app.clearScoresScene();
        app.stageScoresScene();
    }

    @FXML
    protected void toSettings(ActionEvent event) {
        app.clearSettingsScene();
        app.stageSettingsScene();
    }

    @FXML
    protected Game getGame() {
        return app.getUsersMgmt().getGame();
    }

    protected MainApp getApp() {
        return app;
    }

    protected UsersManagement getUsersMgmt() {
        return usersMgmt;
    }

    protected void setCursor(Cursor cursor) {
        app.getStage().getScene().setCursor(cursor);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
