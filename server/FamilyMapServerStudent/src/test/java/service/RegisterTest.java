package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import response.RegisterResponse;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    private Register service;

    @BeforeEach
    public void setUp() {
        new Clear().clear();
        service = new Register();
    }

    @Test
    public void RegisterPass() {
        RegisterRequest request = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f");
        RegisterResponse response = service.register(request);
        assertTrue(response.isSuccess());
    }

    @Test
    public void usernameInUse() {
        RegisterRequest request = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f");
        RegisterResponse response = service.register(request);
        assertTrue(response.isSuccess());
        response = service.register(request);
        assertEquals("Error: Username is already in use", response.getMessage());
    }
}
