package service;

import dao.*;
import model.AuthToken;
import model.User;
import model.Person;
import model.Event;
import request.LoadRequest;
import response.LoadResponse;
import response.RegisterResponse;

import java.util.UUID;

/**
 * A class used to store the load function
 */
public class Load {

    /**
     * Clears all the tables in the database, and loads in new tables using the data from the request
     * @param r The request containing the data to loaded into the new tables
     * @return a response reporting the success or failure of operation
     */
    public LoadResponse load(LoadRequest r) {
        if(r.getEvents() == null || r.getPersons() == null || r.getUsers() == null) {
            return new LoadResponse("Error: Invalid request data (missing values, invalid values, etc.)", false);
        }

        Clear clear = new Clear();
        clear.clear();
        Database database = new Database();
        try {
            UserDao uDao = new UserDao(database.getConnection());
            AuthTokenDao aDao = new AuthTokenDao(database.getConnection());
            User[] users = r.getUsers();
            for(int i =0; i < users.length; ++i) {
                uDao.insert(users[i]);
                AuthToken authToken = new AuthToken(UUID.randomUUID().toString(), users[i].getUsername());
                aDao.insert(authToken);
            }

            PersonDao pDao = new PersonDao(database.getConnection());
            Person[] persons = r.getPersons();
            for(int i =0; i < persons.length; ++i) {
                pDao.insert(persons[i]);
            }

            EventDao eDao = new EventDao(database.getConnection());
            Event[] events = r.getEvents();
            for(int i =0; i < events.length; ++i) {
                eDao.insert(events[i]);
            }
            database.closeConnection(true);
            return new LoadResponse("Successfully added "+ users.length + " users, " + persons.length + " persons, and "
                    + events.length + " events to the database", true);
        }
        catch(DataAccessException e) {
            try {
                database.closeConnection(false);
            }
            catch(DataAccessException e2) {
                return new LoadResponse("Error: Internal server error", false);
            }
            return new LoadResponse("Error: Internal server error", false);
        }
    }
}
