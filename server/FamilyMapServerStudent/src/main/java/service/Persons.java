package service;

import dao.*;
import model.Person;
import model.User;
import response.EventsResponse;
import response.PersonsResponse;

import java.util.ArrayList;

public class Persons {
    /**
     * Obtains all family members of a user
     * @return A response containing an array all family members, or an error message
     */
    public PersonsResponse persons(String authToken) {
        Verify verify = new Verify();
        if(!verify.verify(authToken)) {
            return new PersonsResponse("Error: Invalid auth token");
        }

        ArrayList<Person> persons = new ArrayList<>();
        Database database = new Database();
        try {
            AuthTokenDao aDao = new AuthTokenDao(database.getConnection());
            String username = aDao.findAuthToken(authToken).getUsername();
            UserDao uDao = new UserDao(database.getConnection());
            PersonDao pDao = new PersonDao(database.getConnection());
            User tempUser = uDao.find(username);
            Person user = pDao.find(tempUser.getPersonID());
            persons.add(user);
            recursiveSearch(user,pDao, persons);
            if(user.getSpouseID() != null) {
                Person spouse = pDao.find(user.getSpouseID());
                persons.add(spouse);
                recursiveSearch(spouse, pDao, persons);
            }
            Person[] result = new Person[persons.size()];
            result = persons.toArray(result);
            database.closeConnection(true);
            return new PersonsResponse(result);
        }
        catch(DataAccessException e) {
            try {
                database.closeConnection(false);
            }
            catch(DataAccessException e2) {
                return new PersonsResponse("Error: Internal server error");
            }
            return new PersonsResponse("Error: Internal server error");
        }
    }

    private void recursiveSearch(Person person, PersonDao pDao, ArrayList<Person> persons) {
        if(person.getFatherID() != null) {
            try {
                Person father = pDao.find(person.getFatherID());
                persons.add(father);
                recursiveSearch(father, pDao, persons);
            }
            catch (DataAccessException e){
                System.out.println("Father not Found");
            }
        }
        if(person.getMotherID() != null) {
            try {
                Person mother = pDao.find(person.getMotherID());
                persons.add(mother);
                recursiveSearch(mother, pDao, persons);
            }
            catch (DataAccessException e){
                System.out.println("Mother not Found");
            }
        }
    }
}
