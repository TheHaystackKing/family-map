package request;

import model.Event;
import model.Person;
import model.User;

/**
 * Stores the data to be passed to the load function
 */
public class LoadRequest {
    /**
     * An array of all the users to be loaded
     */
    private User[] users;
    /**
     * An array of all the people to be loaded
     */
    private Person[] persons;
    /**
     * AN array of all the events to be loaded
     */
    private Event[] events;

    /**
     * Creates a new load request object
     *
     * @param users new load request's users
     * @param persons new load request's people
     * @param events new load request's events
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}
