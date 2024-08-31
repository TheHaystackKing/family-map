package dao;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class used to communicate with the Person table of the database
 */
public class PersonDao {

    /**
     * The connection to the database
     */
    private Connection conn;

    /**
     * Creates a new PersonDAO object
     *
     * @param conn the new PersonDAO's connection
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new person into the database
     *
     * @param person the person to be added
     * @throws DataAccessException if an SQL error occurs
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Persons (personId, username, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }

    }

    /**
     * Searches the database for a person
     *
     * @param personID the person to be found
     * @return the person, if found
     * @throws DataAccessException if an SQL error occurs
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE personId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if(rs.next()) {
                person = new Person(rs.getString("personId"), rs.getString("username"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"), rs.getString("fatherId"),
                        rs.getString("motherId"), rs.getString("spouseId"));
                return person;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void updateSpouse(String personId, String spouseId) throws DataAccessException {
        String sql = "UPDATE Persons SET spouseId = ? WHERE personId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, spouseId);
            stmt.setString(2, personId);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while updating person");
        }
    }

    public void updateParent(String personId, String fatherId, String motherId) throws DataAccessException {
        String sql = "UPDATE Persons SET fatherId = ?, motherId = ? WHERE personId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fatherId);
            stmt.setString(2, motherId);
            stmt.setString(3, personId);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while updating person");
        }
    }

    /**
     * Searches the database for a person and deletes them
     * @param personId the person to be deleted
     * @throws DataAccessException if an SQL error occurs
     */
    public void delete(String personId) throws DataAccessException {
        String sql = "DELETE FROM Persons WHERE personId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,personId);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered when attempting to delete person");
        }
    }

    /**
     * Clears the entire Person table in the database
     *
     * @throws DataAccessException if an SQL error occurs
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Persons";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DataAccessException("Error encountered while clearing table");
        }
    }
}
