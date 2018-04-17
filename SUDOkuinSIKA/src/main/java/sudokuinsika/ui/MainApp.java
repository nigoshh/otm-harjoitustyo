package sudokuinsika.ui;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sudokuinsika.dao.DBUserDao;
import sudokuinsika.dao.Database;
import sudokuinsika.dao.UserDao;
import sudokuinsika.domain.Game;
import sudokuinsika.domain.User;

public class MainApp extends Application {

    private Stage stage;
    private Scene loginScene;
    private Scene newUserScene;
    private Scene gameScene;
    private UserDao userDao;

    private LoginController loginController;
    private NewUserController newUserController;
    private GameController gameController;

    private Game game;

    @Override
    public void init() throws Exception {
        // here we build all the dependencies we need, and we inject them
        // into the controllers

        boolean dbExists = (new File("userdata.db")).isFile();
        Database db = new Database("jdbc:sqlite:userdata.db");
        if (!dbExists) {
            db.init();
        }

        userDao = new DBUserDao(db);

//        initScene("/fxml/Login.fxml", loginController, loginScene);

        FXMLLoader loginSceneLoader
                = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent loginPane = loginSceneLoader.load();
        loginController = loginSceneLoader.getController();
        loginController.setApp(this);
        loginController.setUserDao(userDao);
        loginScene = new Scene(loginPane);

        FXMLLoader newUserSceneLoader
                = new FXMLLoader(getClass().getResource("/fxml/NewUser.fxml"));
        Parent newUserPane = newUserSceneLoader.load();
        newUserController = newUserSceneLoader.getController();
        newUserController.setApp(this);
        newUserController.setUserDao(userDao);
        newUserScene = new Scene(newUserPane);

        FXMLLoader gameSceneLoader
                = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
        Parent gamePane = gameSceneLoader.load();
        gameController = gameSceneLoader.getController();
        gameController.setApp(this);
        gameController.setUserDao(userDao);
        gameScene = new Scene(gamePane);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        stage.setTitle("SUDOku");
        stageLoginScene();
        stage.show();
    }

    public void stageLoginScene() {
        stage.setScene(loginScene);
    }

    public void stageNewUserScene() {
        stage.setScene(newUserScene);
    }

    public void stageGameScene() {
        stage.setScene(gameScene);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void clearLoginScene() {
        loginController.clear();
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public Stage getStage() {
        return stage;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public GameController getGameController() {
        return gameController;
    }

    private void initScene(String fxmlPath, Controller controller, Scene scene)
            throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent pane = loader.load();
        controller = loader.getController();
        controller.setApp(this);
        controller.setUserDao(userDao);
        scene = new Scene(pane);
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
