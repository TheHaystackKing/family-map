package response;

import model.Person;

/**
 * The Response to be returned from the person/[personID] function
 */
public class PersonResponse extends Response {
    /**
     * username of person being returned
     */
    private String associatedUsername;
    /**
     * person ID of person being returned
     */
    private String personID;
    /**
     * first name of person being returned
     */
    private String firstName;
    /**
     * last name of person being returned
     */
    private String lastName;
    /**
     * gender of person being returned
     */
    private String gender;
    /**
     * person ID of Father of person being returned
     */
    private String fatherID;
    /**
     * person ID of Mother of person being returned
     */
    private String motherID;
    /**
     * person ID of Spouse of person being returned
     */
    private String spouseID;

    /**
     * Creates new Person Response
     * Case 1: Operation Successful, returns person
     *
     * @param person the person being returned

     */
    public PersonResponse(Person person) {
        super(null, true);
        this.personID = person.getPersonID();
        this.associatedUsername = person.getAssociatedUsername();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherID = person.getFatherID();
        this.motherID = person.getMotherID();
        this.spouseID = person.getSpouseID();
    }

    /**
     * Creates new Person Response
     * Case2: Operation Failed, return error message
     * @param message new response's error message
     */
    public PersonResponse(String message) {
        super(message, false);
    }

    public String getPersonID() {
        return personID;
    }

    public String getUsername() {
        return associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }
}
