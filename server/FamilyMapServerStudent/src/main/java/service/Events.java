package service;

import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import model.Event;
import response.EventsResponse;
import response.PersonsResponse;
import model.Person;

import java.util.ArrayList;

public class Events {

    /**
     * Obtains the entirety of the events table
     * @return A response containing an array of all events, or an error message
     */
    public EventsResponse events(String authToken) {
        Verify verify = new Verify();
        if(!verify.verify(authToken)) {
            return new EventsResponse("Error: Invalid auth token");
        }
        Persons persons = new Persons();
        PersonsResponse response = persons.persons(authToken);
        Person[] family = response.getData();
        ArrayList<Event> events = new ArrayList<>();
        Database database = new Database();
        try{
            EventDao eDao = new EventDao(database.getConnection());
            for(int i =0; i < family.length; ++i) {
                Person person = family[i];
                events.addAll(eDao.findPerson(person.getPersonID()));
            }
            database.closeConnection(true);
            Event[] result = new Event[events.size()];
            result = events.toArray(result);
            return new EventsResponse(result);
        }
        catch (DataAccessException e) {
            try {
                database.closeConnection(false);
            }
            catch(DataAccessException e2) {
                return new EventsResponse("Error: Internal server error");
            }
            return new EventsResponse("Error: Internal server error");
        }
    }
}
