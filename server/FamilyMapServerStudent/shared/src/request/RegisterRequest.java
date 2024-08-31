package request;

/**
 * Stores the data to be passed to the register function
 */
public class RegisterRequest {

    /**
     * username of registering user
     */
    private String username;
    /**
     * password of registering user
     */
    private String password;
    /**
     * email of registering user
     */
    private String email;
    /**
     * first name of registering user
     */
    private String firstName;
    /**
     * last name of registering user
     */
    private String lastName;
    /**
     * gender of registering user
     */
    private String gender;

    /**
     * Creates new Register Request
     *
     * @param username new request's username
     * @param password new request's password
     * @param email new request's email
     * @param firstName new request's first name
     * @param lastName new request's last name
     * @param gender new request's gender
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
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
}
