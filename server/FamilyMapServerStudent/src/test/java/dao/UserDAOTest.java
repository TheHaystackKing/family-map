package dao;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private Database db;
    private User bestUser;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestUser = new User("JohnDoe123", "HelloWorld", "johndoe123@gmail.com",
                "John", "Doe", "m", "ABC-123");
        Connection conn = db.getConnection();
        db.clearTables();
        uDao = new UserDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, ()->uDao.insert(bestUser));
    }

    @Test
    public void findPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertNull(uDao.find("JaneDoe456"));
    }

    @Test
    public void clearPass() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.clear();
        assertNull(uDao.find(bestUser.getUsername()));
    }

    @Test
    public void clearEmptyTable() throws DataAccessException {
        uDao.clear();
    }
}
