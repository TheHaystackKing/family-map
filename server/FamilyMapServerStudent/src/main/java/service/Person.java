package service;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import response.PersonResponse;
import response.PersonsResponse;

import java.util.Objects;

/**
 * A class use to store the person and person/[personId] functions
 */
public class Person {

    /**
     * Searches for a person with a given ID
     * @param personId the person to be found
     * @return A response containing the event, or an error message
     */
    public PersonResponse person(String authToken,String personId) {
        Database database = new Database();
        try {
            PersonDao pDao = new PersonDao(database.getConnection());
            if(pDao.find(personId) == null) {
                database.closeConnection(false);
                return new PersonResponse("Error: Invalid personID parameter");
            }
            database.closeConnection(true);
        }
        catch(DataAccessException e) {
            try {
                database.closeConnection(false);
            }
            catch(DataAccessException e2) {
                return new PersonResponse("Error: Internal server error");
            }
            return new PersonResponse("Error: Internal server error");
        }

        Persons persons = new Persons();
        PersonsResponse response = persons.persons(authToken);
        if(!response.isSuccess()) {
            return new PersonResponse("Error: Internal server error");
        }
        model.Person[] family = response.getData();
        for(int i =0; i < family.length; ++i) {
            if(Objects.equals(family[i].getPersonID(), personId)) {
                return new PersonResponse(family[i]);
            }
        }
        return new PersonResponse("Error: Requested person does not belong to this user");
    }
}
