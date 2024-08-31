package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import response.FillResponse;

import static org.junit.jupiter.api.Assertions.*;

public class FillTest {

    private Fill service;

    @BeforeEach
    public void setUp() {
        new Clear().clear();
        service = new Fill();
        Register register = new Register();
        register.register(new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f"));
    }

    @Test
    public void fillPass() {
        FillResponse response = service.fill("sheila", 4);
        assertTrue(response.isSuccess());
    }

    @Test
    public void negativeGeneration() {
        FillResponse response = service.fill("sheila", -4);
        assertEquals("Invalid username or generations parameter", response.getMessage());
    }
}
