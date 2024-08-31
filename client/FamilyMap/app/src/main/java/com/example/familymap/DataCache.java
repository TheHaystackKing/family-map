package com.example.familymap;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import model.Event;
import model.Person;

public class DataCache {

    private static DataCache instance;

    private String authToken;
    private Person user;
    private Person currentPerson;
    private Event currentEvent;

    private Person[] people;
    private Map<String, Person> peopleMap = new HashMap<>();
    private Map<String, Person> fatherSideMale = new HashMap<>();
    private ArrayList<Event> fatherMaleEvent = new ArrayList<>();
    private Map<String, Person> motherSideMale = new HashMap<>();
    private ArrayList<Event> motherMaleEvent = new ArrayList<>();
    private Map<String, Person> fatherSideFemale = new HashMap<>();
    private ArrayList<Event> fatherFemaleEvent = new ArrayList<>();
    private Map<String, Person> motherSideFemale = new HashMap<>();
    private ArrayList<Event> motherFemaleEvent = new ArrayList<>();
    private Event[] events;
    private ArrayList<String> eventTypes = new ArrayList<>();
    private boolean displayStoryLines;
    private boolean displayFamilyLines;
    private boolean displaySpouseLines;
    private boolean displayFatherSide;
    private boolean displayMotherSide;
    private boolean displayMaleEvents;
    private boolean displayFemaleEvents;
    private boolean inEventActivity;

    public static DataCache getInstance() {
        if(instance == null) {
            instance = new DataCache();
        }
        return instance;
    }

    public void clearCache() {
        peopleMap.clear();
        fatherSideMale.clear();
        fatherSideFemale.clear();
        motherSideFemale.clear();
        motherSideMale.clear();
        motherMaleEvent.clear();
        motherFemaleEvent.clear();
        fatherMaleEvent.clear();
        fatherFemaleEvent.clear();
        peopleMap.clear();
        eventTypes.clear();
    }

    public void organizeData() {
        for(int i = 0; i < people.length; ++i) {
            peopleMap.put(people[i].getPersonID(), people[i]);
        }
    }

    public ArrayList<Event> getEvents(String PersonID) {
        ArrayList<Event> personEvents = new ArrayList<>();
        for(int i = 0; i < events.length; ++i) {
            if(events[i].getPersonID().equals(PersonID)) {
                boolean foundEventLocation = false;
                for(int j = 0; j < personEvents.size(); ++j) {
                    if(events[i].getYear() < personEvents.get(j).getYear() && !foundEventLocation) {
                        personEvents.add(j, events[i]);
                        foundEventLocation = true;
                    }
                }
                if(!foundEventLocation) {
                    personEvents.add(events[i]);
                }
            }
        }
        return personEvents;
    }

    public void partitionEvents() {
        for(int i = 0; i < events.length; ++i) {
            Event event = events[i];
            if(fatherSideMale.containsKey(event.getPersonID())) {
                fatherMaleEvent.add(event);
            }
            else if(motherSideMale.containsKey(event.getPersonID())) {
                motherMaleEvent.add(event);
            }
            else if(fatherSideFemale.containsKey(event.getPersonID())) {
                fatherFemaleEvent.add(event);
            }
            else if(motherSideFemale.containsKey(event.getPersonID())) {
               motherFemaleEvent.add(event);
            }
        }
    }

    public void partitionFamily() {
        String motherId = user.getMotherID();
        boolean onMotherSide = false;
        for(int i = 0; i < people.length; ++i) {
            Person person = people[i];
            if(person.getPersonID().equals(motherId)) {
                onMotherSide = true;
            }
            if(!onMotherSide) {
                if(person.getGender().equals("m")) {
                    fatherSideMale.put(person.getPersonID(), person);
                }
                else {
                    fatherSideFemale.put(person.getPersonID(), person);
                }
            }
            else {
                if(person.getGender().equals("m")) {
                    motherSideMale.put(person.getPersonID(), person);
                }
                else {
                    motherSideFemale.put(person.getPersonID(), person);
                }
            }
        }
    }

    public void resetSettings() {
        displayFamilyLines = true;
        displayFatherSide = true;
        displayFemaleEvents = true;
        displayMaleEvents = true;
        displayMotherSide = true;
        displaySpouseLines = true;
        displayStoryLines = true;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Person[] getPeople() {
        return people;
    }

    public void setPeople(Person[] people) {
        this.people = people;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public boolean isDisplayStoryLines() {
        return displayStoryLines;
    }

    public void setDisplayStoryLines(boolean displayStoryLines) {
        this.displayStoryLines = displayStoryLines;
    }

    public boolean isDisplayFamilyLines() {
        return displayFamilyLines;
    }

    public void setDisplayFamilyLines(boolean displayFamilyLines) {
        this.displayFamilyLines = displayFamilyLines;
    }

    public boolean isDisplaySpouseLines() {
        return displaySpouseLines;
    }

    public void setDisplaySpouseLines(boolean displaySpouseLines) {
        this.displaySpouseLines = displaySpouseLines;
    }

    public boolean isDisplayFatherSide() {
        return displayFatherSide;
    }

    public void setDisplayFatherSide(boolean displayFatherSide) {
        this.displayFatherSide = displayFatherSide;
    }

    public boolean isDisplayMotherSide() {
        return displayMotherSide;
    }

    public void setDisplayMotherSide(boolean displayMotherSide) {
        this.displayMotherSide = displayMotherSide;
    }

    public boolean isDisplayMaleEvents() {
        return displayMaleEvents;
    }

    public void setDisplayMaleEvents(boolean displayMaleEvents) {
        this.displayMaleEvents = displayMaleEvents;
    }

    public boolean isDisplayFemaleEvents() {
        return displayFemaleEvents;
    }

    public void setDisplayFemaleEvents(boolean displayFemaleEvents) {
        this.displayFemaleEvents = displayFemaleEvents;
    }

    public ArrayList<Event> getFatherMaleEvent() {
        return fatherMaleEvent;
    }

    public ArrayList<Event> getMotherMaleEvent() {
        return motherMaleEvent;
    }

    public ArrayList<Event> getFatherFemaleEvent() {
        return fatherFemaleEvent;
    }

    public ArrayList<Event> getMotherFemaleEvent() {
        return motherFemaleEvent;
    }

    public boolean isOnFatherSideMale(String personId) {
        return fatherSideMale.containsKey(personId);
    }

    public boolean isOnFatherSideFemale(String personId) {
        return fatherSideFemale.containsKey(personId);
    }

    public boolean isOnMotherSideMale(String personID) {
        return motherSideMale.containsKey(personID);
    }

    public boolean isOnMotherSideFemale(String personID) {
        return motherSideFemale.containsKey(personID);
    }

    public Person getPerson(String personID) {
        return peopleMap.get(personID);
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

    public boolean isInEventActivity() {
        return inEventActivity;
    }

    public void setInEventActivity(boolean inEventActivity) {
        this.inEventActivity = inEventActivity;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public Event findEvent(String eventID) {
        for(int i = 0; i < events.length; ++i) {
            if(events[i].getEventID().equals(eventID)) {
                return events[i];
            }
        }
        return null;
    }

    public ArrayList<Event> searchEvents(String searchString) {
        String lowerCaseString = searchString.toLowerCase();
        ArrayList<Event> eventList = new ArrayList<>();
        if(displayMaleEvents && displayFatherSide) {
            eventList = searchEventHelper(eventList, fatherMaleEvent, searchString);
        }
        if(displayFemaleEvents && displayFatherSide) {
            eventList = searchEventHelper(eventList, fatherFemaleEvent, searchString);
        }
        if(displayMaleEvents && displayMotherSide) {
            eventList = searchEventHelper(eventList, motherMaleEvent, searchString);
        }
        if(displayFemaleEvents && displayMotherSide) {
            eventList = searchEventHelper(eventList, motherFemaleEvent, searchString);
        }
        return eventList;
    }

    public ArrayList<Event> searchEventHelper(ArrayList<Event> eventList, ArrayList<Event> searchList, String searchString) {
        for(int i = 0; i < searchList.size(); ++i) {
            if(searchList.get(i).getEventType().toLowerCase().contains(searchString)) {
                eventList.add(searchList.get(i));
            }
            else if(String.valueOf(searchList.get(i).getYear()).toLowerCase().contains(searchString)) {
                eventList.add((searchList.get(i)));
            }
            else if(searchList.get(i).getCity().toLowerCase().contains(searchString)) {
                eventList.add((searchList.get(i)));
            }
            else if(searchList.get(i).getCountry().toLowerCase().contains(searchString)) {
                eventList.add((searchList.get(i)));
            }
        }
        return eventList;
    }

    public ArrayList<Person> searchPeople(String searchString) {
        String lowerCaseString = searchString.toLowerCase();
        ArrayList<Person> peopleList = new ArrayList<>();
        for(int i = 0; i < people.length; ++i) {
            if(people[i].getFirstName().toLowerCase().contains(lowerCaseString)) {
                peopleList.add(people[i]);
            }
            else if (people[i].getLastName().toLowerCase().contains(lowerCaseString)){
                peopleList.add(people[i]);
            }
        }
        return peopleList;
    }

    public int checkEventType(String eventType) {
        for(int i = 0; i < eventTypes.size(); ++i) {
            if(eventType.equals(eventTypes.get(i))) {
                return i;
            }
        }
        eventTypes.add(eventType);
        return eventTypes.size() - 1;
    }

    public float getEventColor(int index) {
        if(index == 0) {
            return BitmapDescriptorFactory.HUE_AZURE;
        }
        else if(index == 1) {
            return BitmapDescriptorFactory.HUE_YELLOW;
        }
        else if(index == 2) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        else if(index == 3) {
            return BitmapDescriptorFactory.HUE_MAGENTA;
        }
        else if(index == 4) {
            return BitmapDescriptorFactory.HUE_GREEN;
        }
        else if(index == 5) {
            return BitmapDescriptorFactory.HUE_CYAN;
        }
        else if(index == 6) {
            return BitmapDescriptorFactory.HUE_ORANGE;
        }
        else if(index == 7) {
            return BitmapDescriptorFactory.HUE_BLUE;
        }
        else if(index == 8) {
            return BitmapDescriptorFactory.HUE_VIOLET;
        }
        else if(index == 9) {
            return BitmapDescriptorFactory.HUE_ROSE;
        }
        else {
            return getEventColor(index - 9);
        }
    }

    private DataCache() {
    }
}
