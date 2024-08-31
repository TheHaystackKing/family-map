package service;

import dao.*;
import model.Event;
import model.Person;
import response.FillResponse;

import java.util.ArrayList;

/**
 * A class used to store the fill function
 */
public class Fill {

    /**
     * Fills in the number of given generations of the given person with people and events.
     * If ancestors already exist, they are deleted and new ones are created.
     *
     * @param username the targeted person
     * @param generations number of genrations to be filled
     * @return A response reporting the success or failure of operation
     */
    public FillResponse fill(String username, int generations) {
        if(generations < 0) {
            return new FillResponse("Invalid username or generations parameter", false);
        }

        Database database = new Database();
        try {
            UserDao uDao = new UserDao(database.getConnection());
            PersonDao pDao = new PersonDao(database.getConnection());
            EventDao eDao = new EventDao(database.getConnection());
            if(uDao.find(username) == null) {
                database.closeConnection(false);
                return new FillResponse("Error: Invalid username or generations parameter", false);
            }
            Person user = pDao.find(uDao.find(username).getPersonID());
            recursiveDelete(user, pDao, eDao);

            Generate generate = new Generate();
            int[] newPeople = { 0 };
            int[] newEvents = { 0 };
            generate.regeneratePeople(user.getPersonID(), generations + 1, newPeople, newEvents, database.getConnection());
            database.closeConnection(true);
            return new FillResponse("Successfully added " + newPeople[0] + " persons and " + newEvents[0] + " events to the database.", true);
        }
        catch(DataAccessException e) {
            try {
                database.closeConnection(false);
            }
            catch(DataAccessException e2) {
                return new FillResponse("Error: Internal server error", false);
            }
            return new FillResponse("Error: Internal server error", false);
        }
    }

    private void recursiveDelete(Person person, PersonDao pDao, EventDao eDao) {
        if(person.getFatherID() != null) {
            try {
                model.Person father = pDao.find(person.getFatherID());
                ArrayList<Event> events = eDao.findPerson(father.getPersonID());
                for(int i = 0; i < events.size(); ++i) {
                    eDao.delete(events.get(i).getEventID());
                }
                recursiveDelete(father, pDao, eDao);
                pDao.delete(father.getPersonID());
            }
            catch (DataAccessException e){
                System.out.println("Father not Found");
            }
        }
        if(person.getMotherID() != null) {
            try {
                model.Person mother = pDao.find(person.getMotherID());
                ArrayList<Event> events = eDao.findPerson(mother.getPersonID());
                for(int i = 0; i < events.size(); ++i) {
                    eDao.delete(events.get(i).getEventID());
                }
                recursiveDelete(mother, pDao, eDao);
                pDao.delete(mother.getPersonID());
            } catch (DataAccessException e) {
                System.out.println("Mother not Found");
            }
        }
    }
}
