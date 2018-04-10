package sudokuinsika.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sudokuinsika.dao.UserDao;
import sudokuinsika.domain.User;

public class Controller implements Initializable {

    MainApp app;
    UserDao userDao;

    public void setApp(MainApp app) {
        this.app = app;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @FXML
    void toLogin(ActionEvent event) {
        app.clearLoginScene();
        app.setloginScene();
    }

    @FXML
    private void toNewUser(ActionEvent event) {
        app.setNewUserScene();
    }

    @FXML
    void toGame(ActionEvent event) {
        app.setGameScene();
    }

    void setGame(User user) {
        app.setGame(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
