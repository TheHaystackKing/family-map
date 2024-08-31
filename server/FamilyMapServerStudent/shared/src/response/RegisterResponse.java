package response;

/**
 * The Response to be returned by the register function
 */
public class RegisterResponse extends Response {
    /**
     * New user's auth token
     */
    String authtoken;
    /**
     * new user's username
     */
    String username;
    /**
     * new user's person ID
     */
    String personID;

    /**
     * Creates new Register Response
     * Case 1: Operation successful, return username with new auth token and personID
     *
     * @param authToken new response's auth token
     * @param username new response's username
     * @param personId new response's person ID
     */
    public RegisterResponse(String authToken, String username, String personId) {
        super(null, true);
        this.authtoken = authToken;
        this.username = username;
        this.personID = personId;
    }

    /**
     * Creates new Register Response
     * Case 2: Operation failed, returns error message
     *
     * @param message new response's error message
     */
    public RegisterResponse(String message) {
        super(message, false);
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }
}
