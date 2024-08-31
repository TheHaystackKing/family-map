package dao;

import model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class used to communicate with the AuthToken table of the database
 */
public class AuthTokenDao {

    /**
     * The connection to the database
     */
    private Connection conn;

    /**
     * Creates a new AuthTokenDAO object
     *
     * @param conn the new AuthTokenDAO's connection
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds a new auth token to the database
     *
     * @param authToken auth token to be added
     * @throws DataAccessException if an SQL error occurs
     */
    public void insert(AuthToken authToken) throws DataAccessException {
        String sql = "INSERT INTO AuthTokens (authToken, username) VALUES(?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }

    }

    /**
     * Searches the database for an auth token
     *
     * @param username the auth token to be found
     * @return the auth token, if found
     * @throws DataAccessException if an SQL error occurs
     */
    public AuthToken find(String username) throws DataAccessException {
        AuthToken foundToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE username = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if(rs.next()) {
                foundToken = new AuthToken(rs.getString("authToken"), rs.getString("username"));
                return foundToken;
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
    public AuthToken findAuthToken(String authToken) throws DataAccessException {
        AuthToken foundToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE authToken = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if(rs.next()) {
                foundToken = new AuthToken(rs.getString("authToken"), rs.getString("username"));
                return foundToken;
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
     * Clears the entire auth token table in the database
     *
     * @throws DataAccessException if an SQL error occurs
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM AuthTokens";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DataAccessException("Error encountered while clearing table");
        }

    }
}
