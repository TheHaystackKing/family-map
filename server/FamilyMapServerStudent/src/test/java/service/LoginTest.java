package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private Login service;

    @BeforeEach
    public void setUp() {
        new Clear().clear();
        service = new Login();
        Register register = new Register();
        register.register(new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f"));
    }

    @Test
    public void loginSuccessful() {
        LoginRequest request = new LoginRequest("sheila", "parker");
        LoginResponse response = service.login(request);
        assertTrue(response.isSuccess());
    }

    @Test
    public void invalidUsername() {
        LoginRequest request = new LoginRequest("shiela", "parker");
        LoginResponse response = service.login(request);
        assertEquals("Error: Request property missing or has invalid value", response.getMessage());
    }
}
