package model;

import java.util.Objects;

/**
 * A class used to model the User data type
 */
public class User {
    /**
     * A username used by users to log in, each user must have a unique username
     */
    private String username;
    /**
     * A password used by users to log in
     */
    private String password;
    /**
     * An email account associated with the user
     */
    private String email;
    /**
     * The user's first name
     */
    private String firstName;
    /**
     * The user's last name
     */
    private String lastName;
    /**
     * The users gender, must be m or f
     */
    private String gender;
    /**
     * A unique ID randomly assigned to the user to be used to connect the user to family members in the map
     */
    private String personID;

    /**
     * Creates a new User object
     *
     * @param username new user's username
     * @param password new user's password
     * @param email new user's email address
     * @param firstName new user's first name
     * @param lastName new user's last name
     * @param gender new user's gender
     * @param personId new user's user ID
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && password.equals(user.password) && email.equals(user.email) && firstName.equals(user.firstName) && lastName.equals(user.lastName) && gender.equals(user.gender) && personID.equals(user.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, firstName, lastName, gender, personID);
    }
}
