package sudokuinsika.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    public void initCreatesUserAndScoreTablesCorrectly() throws IOException, ClassNotFoundException, SQLException {
        boolean dbExists = new File("test.db").isFile();
        if (dbExists) {
            Files.delete(Paths.get("test.db"));
        }
        Database db = new Database("jdbc:sqlite:test.db");
        db.init();
        Connection conn = db.getConnection();
        String query = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, "user");
        ResultSet rs = stmt.executeQuery();
        assertTrue(rs.next());
        rs.close();
        stmt.close();
        stmt = conn.prepareStatement(query);
        stmt.setString(1, "score");
        rs = stmt.executeQuery();
        assertTrue(rs.next());
        rs.close();
        stmt.close();
        conn.close();
    }
}
