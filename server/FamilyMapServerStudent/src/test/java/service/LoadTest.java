package service;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import response.LoadResponse;

import static org.junit.jupiter.api.Assertions.*;

public class LoadTest {

    private Load service;

    @BeforeEach
    public void setUp() {
        new Clear().clear();
        service = new Load();
    }

    @Test
    public void fillSuccess() {
        LoadRequest request = new LoadRequest(new User[0], new Person[0], new Event[0]);
        LoadResponse response = service.load(request);
        assertTrue(response.isSuccess());
    }

    @Test void nullValue() {
        LoadRequest request = new LoadRequest(null, new Person[0], new Event[0]);
        LoadResponse response = service.load(request);
        assertEquals("Error: Invalid request data (missing values, invalid values, etc.)", response.getMessage());
    }
}
