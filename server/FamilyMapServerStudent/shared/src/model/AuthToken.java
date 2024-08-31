package model;

import java.util.Objects;

/**
 * A class used model the AuthToken data type
 */
public class AuthToken {

    /**
     * A unique randomly assigned authToken
     */
    private String authToken;
    /**
     * The person ID of the person associated with this auth token
     */
    private String username;

    /**
     * Creates a new auth token object
     *
     * @param authToken new auth token's value
     * @param username new auth token's associated person ID
     */
    public AuthToken(String authToken ,String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return authToken.equals(authToken1.authToken) && username.equals(authToken1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, username);
    }
}
