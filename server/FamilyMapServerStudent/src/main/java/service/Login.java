package service;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import request.LoginRequest;
import response.LoginResponse;

import java.util.Objects;

/**
 * A class used to store the login fuction
 */
public class Login {

    /**
     * Checks the username and password from the request to confirm they are in the database
     * @param r the request containing the username and password
     * @return a response containg the authToken, personID, and username, or an error message
     */
    public LoginResponse login(LoginRequest r) {
        Database database = new Database();
        try {
            UserDao uDao = new UserDao(database.getConnection());
            String username = r.getUsername();
            if(uDao.find(username) != null && Objects.equals(uDao.find(username).getPassword(), r.getPassword())) {
                String personId = uDao.find(username).getPersonID();

                AuthTokenDao aDao = new AuthTokenDao(database.getConnection());
                String authToken = aDao.find(username).getAuthToken();

                database.closeConnection(true);
                return new LoginResponse(authToken, username, personId);
            }
            else {
                database.closeConnection(false);
                return new LoginResponse("Error: Request property missing or has invalid value");
            }
        }
        catch(DataAccessException e) {
            try {
                database.closeConnection(false);
            }
            catch(DataAccessException e2) {
                return new LoginResponse("Error: Internal server error");
            }
            return new LoginResponse("Error: Internal server error");
        }
    }
}
