package service;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import response.RegisterResponse;

/**
 * This is a helper class for all functions that requires verification of an authToken. Since it is not a main service and only a helper function, I did not create tests from this class.
 */
public class Verify {

    public boolean verify(String authToken) {
        Database database = new Database();
        try {
            AuthTokenDao aDao = new AuthTokenDao(database.getConnection());
            if(aDao.findAuthToken(authToken) != null) {
                database.closeConnection(true);
                return true;
            }
            else {
                database.closeConnection(true);
                return false;
            }
        }
        catch(DataAccessException e) {
            try {
                database.closeConnection(false);
            }
            catch(DataAccessException e2) {
                 System.out.println("Error: Catastrophic Failure");
            }
            System.out.println("Something went wrong when searching the database");
        }
        return false;
    }
}
