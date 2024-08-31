package dao;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class PersonDOATest {

    private Database db;
    private Person bestPerson;
    private Person bestPerson2;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
        bestPerson = new Person("ABC-123", "JohnDoe123", "John", "Doe", "m",
                null, null, null);
        bestPerson2 = new Person("ABC-124", "JohnDoe124", "John", "Doe", "m",
                "ABD-124", "ADF-124", "ABG-124");
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDao.insert(bestPerson);
        assertThrows(DataAccessException.class, ()->pDao.insert(bestPerson));
    }

    @Test
    public void findPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        pDao.insert(bestPerson);
        assertNull(pDao.find("ABC-124"));
    }

    @Test
    public void deletePass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.delete(bestPerson.getPersonID());
        assertNull(pDao.find(bestPerson.getPersonID()));
    }

    @Test
    public void deleteOnlyRemovesRequested() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.insert(bestPerson2);
        pDao.delete(bestPerson.getPersonID());
        assertNull(pDao.find(bestPerson.getPersonID()));
        assertNotNull(pDao.find(bestPerson2.getPersonID()));
    }

    @Test
    public void updateSpouseFromNull() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.updateSpouse(bestPerson.getPersonID(), "ABG-123");
        assertEquals("ABG-123", pDao.find(bestPerson.getPersonID()).getSpouseID());
    }

    @Test
    public void updateSpouseToNull() throws DataAccessException {
        pDao.insert(bestPerson2);
        pDao.updateSpouse(bestPerson2.getPersonID(), null);
        assertNull(pDao.find(bestPerson2.getPersonID()).getSpouseID());
    }

    @Test
    public void updateParentsFromNull() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.updateParent(bestPerson.getPersonID(), "ABD-123", "ADF-123");
        assertEquals("ABD-123", pDao.find(bestPerson.getPersonID()).getFatherID());
        assertEquals("ADF-123", pDao.find(bestPerson.getPersonID()).getMotherID());
    }

    @Test
    public void updateParentsToNull() throws DataAccessException {
        pDao.insert(bestPerson2);
        pDao.updateParent(bestPerson2.getPersonID(), null, null);
        assertNull(pDao.find(bestPerson2.getPersonID()).getFatherID());
        assertNull(pDao.find(bestPerson2.getPersonID()).getMotherID());
    }

    @Test
    public void clearPass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.clear();
        assertNull(pDao.find(bestPerson.getPersonID()));
    }

    //checks that clearing an empty table will not generate errors
    @Test
    public void clearEmptyTable() throws DataAccessException {
        pDao.clear();
    }
}
