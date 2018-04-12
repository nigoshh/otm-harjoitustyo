//package sudokuinsika.ui;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import sudokuinsika.dao.FakeUserDao;
//import sudokuinsika.dao.UserDao;
//
//public class LoginControllerTest {

//    UserDao fakeDao;
//    LoginController loginController;
//    MainApp app;
//
//    @Before
//    public void setUp() throws Exception {
//        app = new MainApp();
//        app.init();
//        app.start(new Stage());
//        fakeDao = new FakeUserDao();
//        loginController = app.getLoginController();
//        loginController.setUserDao(fakeDao);
//    }
//
//    @Test
//    public void existingUserCanLogIn() throws SQLException {
//        loginController.getUsername().setText("test");
//        loginController.login(new ActionEvent());
//        assertEquals(app.getGameScene(), app.getStage().getScene());
//    }
//}
