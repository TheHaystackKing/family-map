package dao;

import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDOATest {

    private Database db;
    private AuthToken bestToken;
    private AuthTokenDao aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestToken = new AuthToken("1234567", "JohnDoe123");
        Connection conn = db.getConnection();
        db.clearTables();
        aDao = new AuthTokenDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        aDao.insert(bestToken);
        AuthToken compareTest = aDao.find(bestToken.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestToken, compareTest);
    }
    @Test
    public void insertFail() throws DataAccessException {
        aDao.insert(bestToken);
        assertThrows(DataAccessException.class, ()->aDao.insert(bestToken));
    }

    @Test
    public void findByTokenPass() throws DataAccessException {
        aDao.insert(bestToken);
        AuthToken compareTest = aDao.findAuthToken(bestToken.getAuthToken());
        assertEquals(bestToken, compareTest);
    }

    @Test
    public void findByTokenFail() throws DataAccessException {
        aDao.insert(bestToken);
        assertNull(aDao.findAuthToken("FakeAuthToken"));
    }

    @Test
    public void findByUsernamePass() throws DataAccessException {
        aDao.insert(bestToken);
        AuthToken compareTest = aDao.find(bestToken.getUsername());
        assertEquals(bestToken, compareTest);
    }

    @Test
    public void findByUsernameFail() throws DataAccessException {
        aDao.insert(bestToken);
        assertNull(aDao.findAuthToken("FakeUsername"));
    }

    @Test
    public void clearPass() throws DataAccessException {
        aDao.insert(bestToken);
        aDao.clear();
        assertNull(aDao.find(bestToken.getAuthToken()));
    }

    @Test
    public void clearEmptyTable() throws DataAccessException {
        aDao.clear();
    }
}
