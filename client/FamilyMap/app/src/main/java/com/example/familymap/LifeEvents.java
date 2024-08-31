package com.example.familymap;

public class LifeEvents {

    private String eventType;
    private String city;
    private String country;
    private int year;
    private String firstName;
    private String lastName;
    private String eventID;

    public LifeEvents(String eventType, String city, String country, int year, String firstName, String lastName, String eventID) {
        this.eventType = eventType;
        this.city = city;
        this.country = country;
        this.year = year;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eventID = eventID;
    }

    public String getEventType() {
        return eventType;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public int getYear() {
        return year;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEventID() {
        return eventID;
    }
}
