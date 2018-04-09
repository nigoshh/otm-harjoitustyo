package sudokuinsika.ui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sudokuinsika.dao.DBUserDao;
import sudokuinsika.dao.Database;
import sudokuinsika.dao.UserDao;

public class MainApp extends Application {

    private Stage stage;
    private Scene loginScene;
    private Scene newUserScene;
    private Scene gameScene;

    @Override
    public void init() throws Exception {
        // here we build all the dependencies we need, and we inject them
        // into the controllers
        Database db = new Database("jdbc:sqlite:userdata.db");
        UserDao uDao = new DBUserDao(db);

        FXMLLoader loginSceneLoader
                = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent loginPane = loginSceneLoader.load();
        LoginController loginController = loginSceneLoader.getController();
        loginController.setApp(this);
        loginController.setUserDao(uDao);
        loginScene = new Scene(loginPane);

        FXMLLoader newUserSceneLoader
                = new FXMLLoader(getClass().getResource("/fxml/NewUser.fxml"));
        Parent newUserPane = newUserSceneLoader.load();
        NewUserController newUserController = newUserSceneLoader.getController();
        newUserController.setApp(this);
        newUserController.setUserDao(uDao);
        newUserScene = new Scene(newUserPane);

        FXMLLoader gameSceneLoader
                = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
        Parent gamePane = gameSceneLoader.load();
        GameController gameController = gameSceneLoader.getController();
        gameController.setApp(this);
        gameController.setUserDao(uDao);
        gameScene = new Scene(gamePane);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        stage.setTitle("SUDOku");
        setloginScene();
        stage.show();
    }

    public void setloginScene() {
        stage.setScene(loginScene);
    }

    public void setNewUserScene() {
        stage.setScene(newUserScene);
    }

    public void setGameScene() {
        stage.setScene(gameScene);
    }

    @Override
    public void stop() {
        // here actions to be done before closing, like saving files
        // or displaying a badass goodbye gif
    }

    public static void main(String[] args) {
        launch(args);
    }

}
