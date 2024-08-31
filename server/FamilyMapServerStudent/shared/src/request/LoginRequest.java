package request;

/**
 * Stores the data to be passed into the login function
 */
public class LoginRequest {
    /**
     * Username of user logging in
     */
    private String username;
    /**
     * password of user loggin in
     */
    private String password;

    /**
     * Creates a new login request
     *
     * @param username new request's username
     * @param password new request's password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
