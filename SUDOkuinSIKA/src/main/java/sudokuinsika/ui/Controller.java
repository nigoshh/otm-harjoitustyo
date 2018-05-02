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

    @FXML protected void toScores(ActionEvent event) throws SQLException {
        app.clearScoresScene();
        app.stageScoresScene();
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
        // TODO
    }

}
