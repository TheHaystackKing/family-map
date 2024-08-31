package model;

import java.util.Objects;

/**
 * A class used to model the Person data type
 */
public class Person {
    /**
     * The person's first name
     */
    private String firstName;
    /**
     * The person's last name
     */
    private String lastName;
    /**
     * The person's gender, must be m or f
     */
    private String gender;
    /**
     * A unique ID randomly assigned to the user to be used to connect the user to family members in the map
     */
    private String personID;
    /**
     * The user ID of the person's spouse, can be null
     */
    private String spouseID;
    /**
     * The user ID of the person's father, can be null
     */
    private String fatherID;
    /**
     * The user ID of the person's mother, can be null
     */
    private String motherID;
    /**
     * The username associated with the person
     */
    private String associatedUsername;



    /**
     * Creates a new Person object
     *
     * @param personId new person's user ID
     * @param username new person's username
     * @param firstName new person's first name
     * @param lastName new person's last name
     * @param gender new person's gender
     * @param fatherId new person's father's user ID
     * @param motherId new person's mother's user ID
     * @param spouseId new person's spouse's user ID
     */
    public Person(String personId, String username, String firstName, String lastName, String gender, String fatherId, String motherId, String spouseId) {
        this.personID = personId;
        this.associatedUsername = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherId;
        this.motherID = motherId;
        this.spouseID = spouseId;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personID.equals(person.personID) && associatedUsername.equals(person.associatedUsername) && firstName.equals(person.firstName) && lastName.equals(person.lastName) && gender.equals(person.gender) && Objects.equals(fatherID, person.fatherID) && Objects.equals(motherID, person.motherID) && Objects.equals(spouseID, person.spouseID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
    }
}
