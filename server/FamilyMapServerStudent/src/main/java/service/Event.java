package service;

import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import response.EventResponse;

import java.util.Objects;

/**
 * A class used to store the event/[eventID] and event functions
 */
public class Event {

    /**
     * Searches for an event with the given event ID
     * @param eventId the event to be found
     * @return A response containing the event, or an error message
     */
    public EventResponse event(String authToken, String eventId) {
        model.Event event;
        Database database = new Database();
        try {
            EventDao eDao = new EventDao(database.getConnection());
            event = eDao.find(eventId);
            if(event != null) {
                database.closeConnection(true);
                Events events = new Events();
                model.Event[] result = events.events(authToken).getData();
                for(int i = 0; i < result.length; ++i) {
                    model.Event currEvent = result[i];
                    if(Objects.equals(currEvent.getEventID(), eventId)) {
                        return new EventResponse(event.getEventID(), event.getAssociatedUsername(), event.getPersonID(), event.getLatitude(),
                                event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear());
                    }
                }
                return new EventResponse("Error: Requested event does not belong to this user");
            }
            else {
                database.closeConnection(false);
                return new EventResponse("Error: Invalid eventID parameter");
            }
        }
        catch (DataAccessException e) {
            try {
                database.closeConnection(false);
            }
            catch(DataAccessException e2) {
                return new EventResponse("Error:" + e2.getMessage());
            }
            return new EventResponse("Error:" + e.getMessage());
        }
    }


}
