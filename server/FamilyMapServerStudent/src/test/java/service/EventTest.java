package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import response.EventResponse;
import response.EventsResponse;
import response.LoginResponse;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    private Event service;
    private String authToken;
    private String eventID;

    @BeforeEach
    public void setUp() {
        new Clear().clear();
        service = new Event();
        Register register = new Register();
        register.register(new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f"));
        authToken = new Login().login(new LoginRequest("sheila", "parker")).getAuthtoken();
        Events events = new Events();
        EventsResponse response = events.events(authToken);
        eventID = response.getData()[0].getEventID();


    }

    @Test
    public void EventFound() {
        EventResponse response = service.event(authToken, eventID);
        assertTrue(response.isSuccess());
    }

    @Test
    public void EventNotFound() {
        EventResponse response = service.event(authToken, "FakeEventID");
        assertEquals("Error: Invalid eventID parameter", response.getMessage());
    }
}
