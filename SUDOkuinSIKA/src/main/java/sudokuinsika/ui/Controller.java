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

    public void setApp(MainApp app) {
        this.app = app;
    }

    public void setUsersMgmt(UsersManagement usersMgmt) {
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
    public Game getGame() {
        return app.getGame();
    }

    @FXML
    public void newRiddle() {
        getGame().createRiddle();
    }

    public MainApp getApp() {
        return app;
    }

    public void setGame(Game game) {
        app.setGame(game);
    }

    public UsersManagement getUsersMgmt() {
        return usersMgmt;
    }

    public void setCursor(Cursor cursor) {
        app.getStage().getScene().setCursor(cursor);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
