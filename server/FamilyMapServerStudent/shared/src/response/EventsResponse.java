package response;

import model.Event;

/**
 * The response to be returned from the event function
 */
public class EventsResponse extends Response {
    /**
     * An array of all events currently in the event table
     */
    private Event[] data;

    /**
     * Creates a new Event Response
     * Case1: Operation successful, array of events returned
     *
     * @param data new response's event array
     */
    public EventsResponse(Event[] data) {
        super(null, true);
        this.data = data;
    }

    /**
     * Creates a new event response
     * Case2: Operation failed, error messages returned
     *
     * @param message new response's message
     */
    public EventsResponse(String message) {
        super(message, false);
    }

    public Event[] getData() {
        return data;
    }
}
