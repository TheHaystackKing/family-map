package service;

import dao.*;
import model.*;
import model.Event;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import response.ClearResponse;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {

    private Clear service;
    private Register register;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new Clear();
        register = new Register();
        RegisterRequest r = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f");
        register.register(r);
    }

    @Test
    public void clearPass() throws DataAccessException {
        ClearResponse response = service.clear();
        assertTrue(response.isSuccess());
    }

    @Test
    public void clearEmptyDatabase() throws DataAccessException {
        service.clear();
        ClearResponse response = service.clear();
        assertTrue(response.isSuccess());
    }
}
