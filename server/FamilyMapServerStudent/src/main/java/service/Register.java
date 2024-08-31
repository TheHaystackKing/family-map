package service;

import dao.*;
import model.AuthToken;
import model.User;
import request.RegisterRequest;
import response.RegisterResponse;

import java.util.Objects;
import java.util.UUID;

/**
 * A class used to store the register function
 */
public class Register {

    /**
     * Checks to see if the username is available, if it is, the user is added to the user list.
     * In addition, a unique auth key, person ID, and 4 generations of ancestors are generated.
     * @param r A request containing the new user information
     * @return a response containing the new user's auth key, person ID, and username, or an error message.
     */
    public RegisterResponse register(RegisterRequest r) {
        if(r.getFirstName() == null || r.getLastName() == null || r.getGender() == null || r.getUsername() == null || r.getPassword() == null || r.getEmail() == null) {
            return new RegisterResponse("Error: Request property missing or has invalid value");
        }
        if(!Objects.equals(r.getGender(), "m") && !Objects.equals(r.getGender(), "f")) {
            return new RegisterResponse("Error: Request property missing or has invalid value");
        }

        Database database = new Database();
        try {
            UserDao uDao = new UserDao(database.getConnection());
            String username = r.getUsername();
            if(uDao.find(username) == null) {
                String personId = UUID.randomUUID().toString();
                User newUser = new User(username, r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(), personId);
                uDao.insert(newUser);

                PersonDao pDao = new PersonDao(database.getConnection());
                Generate generateTree = new Generate();
                generateTree.generatePeople(username, r.getFirstName(), r.getLastName(), r.getGender(), newUser.getPersonID(), 5, database.getConnection());

                String authToken = UUID.randomUUID().toString();
                AuthToken newToken = new AuthToken(authToken, username);
                AuthTokenDao aDao = new AuthTokenDao(database.getConnection());
                aDao.insert(newToken);



                database.closeConnection(true);
                return new RegisterResponse(authToken, username, personId);
            }
            else {
                database.closeConnection(false);
                return new RegisterResponse("Error: Username is already in use");
            }

        }
        catch(DataAccessException e) {
            try {
                database.closeConnection(false);
            }
            catch(DataAccessException e2) {
                return new RegisterResponse("Error: Internal Server Error");
            }
            return new RegisterResponse("Error: Internal Server Error");
        }
    }
}
