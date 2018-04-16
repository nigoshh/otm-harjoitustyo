package sudokuinsika.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sudokuinsika.dao.UserDao;
import sudokuinsika.domain.Game;
import sudokuinsika.domain.User;

public class Controller implements Initializable {

    private MainApp app;
    private UserDao userDao;

    public void setApp(MainApp app) {
        this.app = app;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @FXML
    void toLogin(ActionEvent event) {
        app.clearLoginScene();
        app.stageLoginScene();
    }

    @FXML
    private void toNewUser(ActionEvent event) {
        app.stageNewUserScene();
    }

    @FXML
    public void toGame(ActionEvent event) {
        app.stageGameScene();
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

    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
