package sudokuinsika.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sudokuinsika.dao.UserDao;

public class Controller implements Initializable {

    private MainApp app;
    private UserDao uDao;

    public void setApp(MainApp app) {
        this.app = app;
    }

    public void setUserDao(UserDao uDao) {
        this.uDao = uDao;
    }

    @FXML
    private void toLogin(ActionEvent event) {
        app.setloginScene();
    }

    @FXML
    private void toNewUser(ActionEvent event) {
        app.setNewUserScene();
    }

    @FXML
    private void toGame(ActionEvent event) {
        app.setGameScene();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
