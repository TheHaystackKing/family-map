package response;

/**
 * The response to be returned from the event/[eventID] function
 */
public class EventResponse extends Response {
    /**
     * Associated username of event being returned
     */
    private String associatedUsername;
    /**
     * ID of event being returned
     */
    private String eventID;
    /**
     * Associated person ID of event being returned
     */
    private String personID;
    /**
     * latitude of event being returned
     */
    private Float latitude;
    /**
     * longitude of event being returned
     */
    private Float longitude;
    /**
     * country of event being returned
     */
    private String country;
    /**
     * city of event being returned
     */
    private String city;
    /**
     * event type of event being returned
     */
    private String eventType;
    /**
     * year of event being returned
     */
    private Integer year;
    /**
     * Creates new Event Response
     * Case 1: event operation was successful, event is returned
     *
     * @param eventId new response's event ID
     * @param username new response's associated username
     * @param personId new response's associated person ID
     * @param latitude new response's latitude
     * @param longitude new response's longitude
     * @param country new response's country
     * @param city new response's city
     * @param eventType new response's eventType
     * @param year new response's year
     */
    public EventResponse(String eventId, String username, String personId, float latitude, float longitude, String country, String city, String eventType, int year) {
        super(null, true);
        this.eventID = eventId;
        this.associatedUsername = username;
        this.personID = personId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Creates new Event Response
     * Case 2: event operation unsuccessful, returns error message
     * @param message error message being returned
     */
    public EventResponse(String message) {
        super(message,false);
        this.latitude = null;
        this.longitude = null;
        this.year = null;

    }

    public String getEventID() {
        return eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }
}
