package com.example.familymap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import request.LoginRequest;
import request.RegisterRequest;
import response.EventsResponse;
import response.LoginResponse;
import response.PersonsResponse;
import response.RegisterResponse;

public class ServerProxyTest {

    private ServerProxy serverProxy;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String authToken;

    @Before
    public void setUp() {
        serverProxy = new ServerProxy("10.0.2.2", "8080");

    }

    @Test
    public void register() {
        username = UUID.randomUUID().toString();
        password = "password";
        email = "email";
        firstName = "firstName";
        lastName = "lastName";
        gender = "m";
        RegisterRequest request = new RegisterRequest(username, password, email, firstName, lastName,gender);
        RegisterResponse response = serverProxy.register(request);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void userAlreadyRegistered() {
        username = "username";
        password = "password";
        email = "email";
        firstName = "firstName";
        lastName = "lastName";
        gender = "m";
        RegisterRequest request = new RegisterRequest(username, password, email, firstName, lastName,gender);
        RegisterResponse response = serverProxy.register(request);
        Assert.assertFalse(response.isSuccess());
    }

    @Test
    public void loginPass() {
        username = "username";
        password = "password";
        LoginRequest request = new LoginRequest(username, password);
        LoginResponse response = serverProxy.login(request);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void loginFail() {
        username = "notAUser";
        password = "password";
        LoginRequest request = new LoginRequest(username, password);
        LoginResponse response = serverProxy.login(request);
        Assert.assertFalse(response.isSuccess());
    }

    @Test
    public void getPeoplePass() {
        username = "username";
        password = "password";
        LoginRequest request = new LoginRequest(username, password);
        LoginResponse response = serverProxy.login(request);
        authToken = response.getAuthtoken();
        PersonsResponse response2 = serverProxy.persons(authToken);
        Assert.assertTrue(response2.isSuccess());
    }

    @Test
    public void getPeopleInvalidAuthToken() {
        authToken = "NotARealAuthToken";
        PersonsResponse response = serverProxy.persons(authToken);
        Assert.assertFalse(response.isSuccess());
    }

    @Test
    public void getEventsPass() {
        username = "username";
        password = "password";
        LoginRequest request = new LoginRequest(username, password);
        LoginResponse response = serverProxy.login(request);
        authToken = response.getAuthtoken();
        EventsResponse respons2 = serverProxy.events(authToken);
        Assert.assertTrue(respons2.isSuccess());
    }

    @Test
    public void getEventsInvalidAuthToken() {
        authToken = "NotARealAuthToken";
        EventsResponse response = serverProxy.events(authToken);
        Assert.assertFalse(response.isSuccess());
    }
}
