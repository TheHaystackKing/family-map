package response;

/**
 * The response to be returned from the login function
 */
public class LoginResponse extends Response {

    /**
     * Auth token of user logging in
     */
    String authtoken;
    /**
     * username of user logging in
     */
    String username;
    /**
     * personId of person logging in
     */
    String personID;

    /**
     * Creates new Login Response
     * Case 1: Operation Successful, returns username with auth token and person ID
     * @param authToken new response's auth token
     * @param username new response's username
     * @param personId nre response's person ID
     */
    public LoginResponse(String authToken, String username, String personId) {
        super(null, true);
        this.authtoken = authToken;
        this.username = username;
        this.personID = personId;
    }

    /**
     * Creates new Login Response
     * Case 2: Operation failed, returns error message
     * @param message new response error message
     */
    public LoginResponse(String message) {
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
