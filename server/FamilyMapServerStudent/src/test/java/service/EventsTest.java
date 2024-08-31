package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import response.EventsResponse;

import static org.junit.jupiter.api.Assertions.*;

public class EventsTest {

    private Events service;
    private String authToken;

    @BeforeEach
    public void setUp() {
        new Clear().clear();
        service = new Events();
        Register register = new Register();
        register.register(new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f"));
        authToken = new Login().login(new LoginRequest("sheila", "parker")).getAuthtoken();
    }

    @Test
    public void eventsPass() {
        EventsResponse response = service.events(authToken);
        assertTrue(response.isSuccess());
    }

    @Test
    public void accessDenied() {
        EventsResponse response = service.events("FakeAuthToken");
        assertEquals("Error: Invalid auth token", response.getMessage());
    }
}
