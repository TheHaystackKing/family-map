package service;

import dao.*;
import response.ClearResponse;

/**
 * A class used to store the clear function
 */
public class Clear {

    /**
     * Clears all the data from all tables in the database
     * @return A response reporting the success or failure of operation
     */
    public ClearResponse clear() {
        Database database = new Database();
        try {
            UserDao uDao = new UserDao(database.getConnection());
            uDao.clear();

            PersonDao pDao = new PersonDao(database.getConnection());
            pDao.clear();

            EventDao eDao = new EventDao(database.getConnection());
            eDao.clear();

            AuthTokenDao aDao = new AuthTokenDao(database.getConnection());
            aDao.clear();

            database.closeConnection(true);
            return new ClearResponse("Clear succeeded.", true);
        }
        catch(DataAccessException e) {
            try {
                database.closeConnection(false);
            }
            catch(DataAccessException e2) {
                return new ClearResponse("Error: Internal server error", false);
            }
            return new ClearResponse("Error: Internal server error", false);
        }
    }
}
