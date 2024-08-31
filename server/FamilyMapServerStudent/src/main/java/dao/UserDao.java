package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class used to communicate with the User table of the database
 */
public class UserDao {

    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * Creates a new UserDAO object
     *
     * @param conn the new UserDAO's connection
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new user into the database
     *
     * @param user user to be inserted
     * @throws DataAccessException if an SQL occurs
     */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO Users (username, password, email, firstName, lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }

    }

    /**
     * Searches the database for a user
     *
     * @param username the username of the to find
     * @return the user, if found
     * @throws DataAccessException if an SQL error occurs
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE username = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if(rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));
                return user;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
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

    /**
     * Clears all data from the User table of the database
     *
     * @throws DataAccessException if an SQL error occurs
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Users";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DataAccessException("Error encountered while clearing table");
        }

    }
}
