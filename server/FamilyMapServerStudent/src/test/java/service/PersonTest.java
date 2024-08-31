package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import response.PersonResponse;
import response.PersonsResponse;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person service;
    private String authToken;
    private String personID;

    @BeforeEach
    public void setUp() {
        new Clear().clear();
        service = new Person();
        Register register = new Register();
        register.register(new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f"));
        authToken = new Login().login(new LoginRequest("sheila", "parker")).getAuthtoken();
        Persons persons = new Persons();
        PersonsResponse response = persons.persons(authToken);
        personID = response.getData()[0].getPersonID();
    }

    @Test
    public void personFound() {
        PersonResponse response = service.person(authToken, personID);
        assertTrue(response.isSuccess());
    }

    @Test
    public void personNotFound() {
        PersonResponse response = service.person(authToken, "FakePersonID");
        assertEquals("Error: Invalid personID parameter", response.getMessage());
    }
}
