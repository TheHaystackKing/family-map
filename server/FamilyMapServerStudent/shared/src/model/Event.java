package model;

import java.util.Objects;

/**
 * A class used to model the Event data type
 */
public class Event {
    /**
     * The type of event
     */
    private String eventType;
    /**
     * The person ID of the person associated with this event
     */
    private String personID;
    /**
     * The city in which this event occurred
     */
    private String city;
    /**
     * The country in which this event occurred
     */
    private String country;
    /**
     * The latitude this event occurred at
     */
    private float latitude;
    /**
     * The longitude this event occurred at
     */
    private float longitude;
    /**
     * A unique ID randomly assigned to the event
     */
    private String eventID;
    /**
     * The username of the person associated with this event
     */
    private String associatedUsername;



    /**
     * The year this event occurred
     */
    private int year;

    /**
     * Creates a new Event object
     *
     * @param eventId new event's event ID
     * @param username new event's associated username
     * @param personId new event's associated person ID
     * @param latitude new event's latitude
     * @param longitude new event's longitude
     * @param country new event's country
     * @param city new event's city
     * @param eventType new event's event type
     * @param year new event's year
     */
    public Event(String eventId, String username, String personId, float latitude, float longitude, String country, String city, String eventType, int year) {
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

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventId) {
        this.eventID = eventId;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personId) {
        this.personID = personId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(o.getClass() != this.getClass()) {
            return false;
        }
        Event e = (Event)o;
        return Objects.equals(this.getEventID(), e.getEventID()) && Objects.equals(this.getAssociatedUsername(), e.getAssociatedUsername()) &&
                Objects.equals(this.getPersonID(), e.getPersonID()) && Objects.equals(this.getLatitude(), e.getLatitude()) &&
                Objects.equals(this.getLongitude(), e.getLongitude()) && Objects.equals(this.getCountry(), e.getCountry()) &&
                Objects.equals(this.getCity(), e.getCity()) && Objects.equals(this.getEventType(), e.getEventType()) &&
                Objects.equals(this.getYear(), e.getYear());
    }
}
