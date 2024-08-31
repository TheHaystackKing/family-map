package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import response.PersonsResponse;

import static org.junit.jupiter.api.Assertions.*;

public class PersonsTest {

    private Persons service;
    private String authToken;

    @BeforeEach
    public void setUp() {
        new Clear().clear();
        service = new Persons();
        Register register = new Register();
        register.register(new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f"));
        authToken = new Login().login(new LoginRequest("sheila", "parker")).getAuthtoken();
    }

    @Test
    public void PersonsPass() {
        PersonsResponse response = service.persons(authToken);
        assertTrue(response.isSuccess());
    }

    @Test
    public void accessDenied() {
        PersonsResponse response = service.persons("FakeAuthToken");
        assertEquals("Error: Invalid auth token", response.getMessage());
    }
}
