package com.example.familymap;

import java.util.ArrayList;

import model.Event;
import model.Person;

public class AdvancedViewsGenerator {

    public ArrayList<LifeEvents> getLifeEvents(ArrayList<Event> events) {
        DataCache dataCache = DataCache.getInstance();
        ArrayList<LifeEvents> lifeEvents = new ArrayList<>();
        for(int i = 0; i < events.size(); ++i) {
            Event event = events.get(i);
            Person person = dataCache.getPerson(event.getPersonID());
            if(dataCache.isOnFatherSideFemale(person.getPersonID())) {
                if(dataCache.isDisplayFemaleEvents() && dataCache.isDisplayFatherSide()) {
                    lifeEvents.add(new LifeEvents(event.getEventType(), event.getCity(), event.getCountry(),
                            event.getYear(),person.getFirstName(), person.getLastName(), event.getEventID()));
                }
            }
            else if(dataCache.isOnFatherSideMale(person.getPersonID())) {
                if(dataCache.isDisplayFatherSide() && dataCache.isDisplayMaleEvents()) {
                    lifeEvents.add(new LifeEvents(event.getEventType(), event.getCity(), event.getCountry(),
                            event.getYear(),person.getFirstName(), person.getLastName(), event.getEventID()));
                }
            }
            else if(dataCache.isOnMotherSideFemale(person.getPersonID())) {
                if(dataCache.isDisplayMotherSide() && dataCache.isDisplayFemaleEvents()) {
                    lifeEvents.add(new LifeEvents(event.getEventType(), event.getCity(), event.getCountry(),
                            event.getYear(),person.getFirstName(), person.getLastName(), event.getEventID()));
                }
            }
            else if(dataCache.isOnMotherSideMale(person.getPersonID())) {
                if(dataCache.isDisplayMotherSide() && dataCache.isDisplayMaleEvents()) {
                    lifeEvents.add(new LifeEvents(event.getEventType(), event.getCity(), event.getCountry(),
                            event.getYear(),person.getFirstName(), person.getLastName(), event.getEventID()));
                }
            }
        }
        return lifeEvents;
    }

    public ArrayList<FamilyMember> getPeopleModel(ArrayList<Person> people) {
        ArrayList<FamilyMember> peopleList = new ArrayList<>();
        for(int i =0 ; i < people.size(); ++i) {
            Person person = people.get(i);
            peopleList.add(new FamilyMember(person.getFirstName(), person.getLastName(),
                    "Not Important", person.getGender(), person.getPersonID()));
        }
        return peopleList;
    }
}
