package service;

import dao.DataAccessException;
import dao.EventDao;
import dao.PersonDao;
import model.Person;
import model.Event;
import server.JsonData;

import java.sql.Connection;
import java.util.Objects;
import java.util.UUID;

/**
 * This is helper class for the register and fill services, used generate family trees. Since this is just a helper class and needs values from fill and register to function,
 * I did not create independent tests for this class.
 */
public class Generate {

    public void generatePeople(String username, String firstName, String lastName,  String gender, String personId, int generations, Connection conn) {
        JsonData data = new JsonData();
        int[] newPeople = new int[1];
        int[] newEvents = new int[1];
        Person user = generatePerson(gender, generations, 5,  conn, data, newPeople, newEvents, 2000);
        user.setAssociatedUsername(username);
        user.setPersonID(personId);
        user.setGender(gender);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        PersonDao pDao = new PersonDao(conn);
        EventDao eDao = new EventDao(conn);
        try {
            pDao.insert(user);
            JsonData.Location.LocationData location1 = data.randomLocation();
            Event birth = new Event(UUID.randomUUID().toString(), username, personId , location1.latitude, location1.longitude,
                    location1.city, location1.country, "Birth", 2000);
            eDao.insert(birth);
            newEvents[0]++;
        }
        catch(DataAccessException e) {
            System.out.println("Something went wrong");
        }

    }
    public void regeneratePeople(String personId, int generations, int[] newPeople, int[] newEvents, Connection conn) {
        JsonData data = new JsonData();
        Person newParentId = generatePerson("", generations, generations, conn, data, newPeople, newEvents, 2000);
        PersonDao pDao = new PersonDao(conn);
        try {
            pDao.updateParent(personId, newParentId.getFatherID(), newParentId.getMotherID());
            newPeople[0]++;
        }
        catch(DataAccessException e) {
            System.out.println("Something went wrong");
        }
    }

    public Person generatePerson(String gender, int generations, int intialGen,  Connection conn, JsonData data, int[] newPeople, int[] newEvents, int year) {
        Person father = null;
        Person mother = null;

        if(generations > 1) {
            mother = generatePerson("f", generations - 1, generations,  conn, data, newPeople, newEvents, year - 20);
            father = generatePerson("m", generations - 1, generations,  conn, data, newPeople, newEvents, year - 20);

            PersonDao pDao = new PersonDao(conn);
            try {
                pDao.updateSpouse(mother.getPersonID(), father.getPersonID());
                pDao.updateSpouse(father.getPersonID(), mother.getPersonID());
            }
            catch(DataAccessException e) {
                System.out.println("Updated Failed");
            }
            JsonData.Location.LocationData location = data.randomLocation();
            Event fatherMarriage = new Event(UUID.randomUUID().toString(), null, father.getPersonID(), location.latitude,
                    location.longitude, location.city, location.country, "Marriage", year);
            Event motherMarriage = new Event(UUID.randomUUID().toString(), null, mother.getPersonID(), location.latitude,
                    location.longitude, location.city, location.country, "Marriage", year);
            try {
                EventDao eDao = new EventDao(conn);
                eDao.insert(motherMarriage);
                newEvents[0]++;
                eDao.insert(fatherMarriage);
                newEvents[0]++;
            }
            catch(DataAccessException e) {
                System.out.println("Something went wrong");
            }
        }
        Person newPerson = null;
        String fatherID = null;
        String motherID = null;
        if(father != null) {
            fatherID = father.getPersonID();
        }
        if(mother != null) {
            motherID = mother.getPersonID();
        }
        if(generations != intialGen) {
            String firstName = null;
            if(Objects.equals(gender, "m")) {
                firstName = data.randomMaleName();
            }
            else if(Objects.equals(gender, "f")) {
                firstName = data.randomFemaleName();
            }
            String lastName = data.randomSurname();
            String personId = UUID.randomUUID().toString();
            newPerson = new Person(personId, null, firstName, lastName, gender, fatherID, motherID, null);
            PersonDao pDao = new PersonDao(conn);
            EventDao eDao = new EventDao(conn);
            try {
                pDao.insert(newPerson);
                newPeople[0]++;
                JsonData.Location.LocationData location1 = data.randomLocation();
                Event birth = new Event(UUID.randomUUID().toString(), null, newPerson.getPersonID() , location1.latitude, location1.longitude,
                        location1.city, location1.country, "Birth", year + 2);
                JsonData.Location.LocationData location2 = data.randomLocation();
                Event death = new Event(UUID.randomUUID().toString(), null, newPerson.getPersonID() , location2.latitude, location2.longitude,
                        location2.city, location2.country, "Death", year + 60);
                eDao.insert(birth);
                newEvents[0]++;
                eDao.insert(death);
                newEvents[0]++;
            }
            catch(DataAccessException e) {
                System.out.println("Something went wrong");
            }
        }
        else {
            newPerson = new Person(null, null, null, null, null, fatherID, motherID, null);
        }
        return newPerson;
    }
}
