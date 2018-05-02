package sudokuinsika.ui;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sudokuinsika.dao.DBScoreDao;
import sudokuinsika.dao.DBUserDao;
import sudokuinsika.dao.Database;
import sudokuinsika.dao.ScoreDao;
import sudokuinsika.dao.UserDao;
import sudokuinsika.domain.Game;
import sudokuinsika.domain.UsersManagement;

public class MainApp extends Application {

    private Stage stage;
    private Scene loginScene;
    private Scene newUserScene;
    private Scene gameScene;
    private Scene scoresScene;
    private UserDao userDao;
    private ScoreDao scoreDao;
    private UsersManagement usersMgmt;

    private LoginController loginController;
    private NewUserController newUserController;
    private GameController gameController;
    private ScoresController scoresController;

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
        scoreDao = new DBScoreDao(db);
        usersMgmt = new UsersManagement(userDao, scoreDao);

//        // why doesn't this work?
//        initScene("/fxml/Login.fxml", loginController, loginScene);

        FXMLLoader loginSceneLoader
                = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent loginPane = loginSceneLoader.load();
        loginController = loginSceneLoader.getController();
        loginController.setApp(this);
        loginController.setUsersMgmt(usersMgmt);
        loginScene = new Scene(loginPane);

        FXMLLoader newUserSceneLoader
                = new FXMLLoader(getClass().getResource("/fxml/NewUser.fxml"));
        Parent newUserPane = newUserSceneLoader.load();
        newUserController = newUserSceneLoader.getController();
        newUserController.setApp(this);
        newUserController.setUsersMgmt(usersMgmt);
        newUserScene = new Scene(newUserPane);

        FXMLLoader gameSceneLoader
                = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
        Parent gamePane = gameSceneLoader.load();
        gameController = gameSceneLoader.getController();
        gameController.setApp(this);
        gameController.setUsersMgmt(usersMgmt);
        gameController.init();
        gameScene = new Scene(gamePane);

        FXMLLoader scoresSceneLoader
                = new FXMLLoader(getClass().getResource("/fxml/Scores.fxml"));
        Parent scoresPane = scoresSceneLoader.load();
        scoresController = scoresSceneLoader.getController();
        scoresController.setApp(this);
        scoresController.setUsersMgmt(usersMgmt);
        scoresController.init();
        scoresScene = new Scene(scoresPane);
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

    public void stageScoresScene() {
        stage.setScene(scoresScene);
    }

    public UsersManagement getUsersMgmt() {
        return usersMgmt;
    }

    public void clearLoginScene() {
        loginController.clear();
    }

    public void clearGameScene() {
        gameController.clear();
    }

    public void clearNewUserScene() {
        newUserController.clear();
    }

    public void clearScoresScene() throws SQLException {
        scoresController.clear();
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
        controller.setUsersMgmt(usersMgmt);
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
