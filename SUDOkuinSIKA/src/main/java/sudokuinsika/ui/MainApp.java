package sudokuinsika.ui;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sudokuinsika.dao.DBScoreDao;
import sudokuinsika.dao.DBUserDao;
import sudokuinsika.dao.Database;
import sudokuinsika.domain.UsersManagement;

/**
 * The main class.
 */
public class MainApp extends Application {

    private UsersManagement usersMgmt;

    private Stage stage;

    private Scene loginScene;
    private Scene newUserScene;
    private Scene gameScene;
    private Scene scoresScene;
    private Scene settingsScene;

    private LoginController loginController;
    private NewUserController newUserController;
    private GameController gameController;
    private ScoresController scoresController;
    private SettingsController settingsController;

    /**
     * Initializes the app.
     *
     * @throws Exception if something unexpected happens
     */
    @Override
    public void init() throws Exception {

        boolean dbExists = (new File("userdata.db")).isFile();
        Database db = new Database("jdbc:sqlite:userdata.db");
        if (!dbExists) {
            db.init();
        }

        usersMgmt = new UsersManagement(new DBUserDao(db), new DBScoreDao(db));

        Pair login = createScene("/fxml/Login.fxml");
        loginController = (LoginController) login.controller;
        loginScene = login.scene;

        Pair newUser = createScene("/fxml/NewUser.fxml");
        newUserController = (NewUserController) newUser.controller;
        newUserScene = newUser.scene;

        Pair game = createScene("/fxml/Game.fxml");
        gameController = (GameController) game.controller;
        gameScene = game.scene;
        gameController.init();

        Pair scores = createScene("/fxml/Scores.fxml");
        scoresController = (ScoresController) scores.controller;
        scoresScene = scores.scene;
        scoresController.init();

        Pair settings = createScene("/fxml/Settings.fxml");
        settingsController = (SettingsController) settings.controller;
        settingsScene = settings.scene;
    }

    /**
     * Starts the app.
     *
     * @param stage the app's main window
     * @throws Exception if something unexpected happens
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        setCustomIcon(stage);
        stage.setTitle("SUDOkuinSIKA");
        stageLoginScene();
        stage.show();
    }

    /**
     * Sets a custom icon for the given stage.
     *
     * @param stage the stage to which we are setting a custom icon
     */
    public void setCustomIcon(Stage stage) {
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/img/board.png")));
    }

    /**
     * Stages the login scene.
     */
    public void stageLoginScene() {
        stage.setScene(loginScene);
    }

    /**
     * Stages the new user scene.
     */
    public void stageNewUserScene() {
        stage.setScene(newUserScene);
    }

    /**
     * Stages the game scene.
     */
    public void stageGameScene() {
        stage.setScene(gameScene);
    }

    /**
     * Stages the scores scene.
     */
    public void stageScoresScene() {
        stage.setScene(scoresScene);
    }

    /**
     * Stages the settings scene.
     */
    public void stageSettingsScene() {
        stage.setScene(settingsScene);
    }

    private class Pair {
        private Scene scene;
        private Controller controller;
    }

    private Pair createScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent pane = loader.load();
        Controller controller = loader.getController();
        controller.setApp(this);
        Pair ret = new Pair();
        ret.controller = controller;
        ret.scene = new Scene(pane);
        return ret;
    }

    public UsersManagement getUsersMgmt() {
        return usersMgmt;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public NewUserController getNewUserController() {
        return newUserController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public ScoresController getScoresController() {
        return scoresController;
    }

    public SettingsController getSettingsController() {
        return settingsController;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
