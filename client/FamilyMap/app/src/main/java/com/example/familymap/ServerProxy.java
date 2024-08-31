package com.example.familymap;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import request.LoginRequest;
import request.RegisterRequest;
import response.EventsResponse;
import response.LoginResponse;
import response.PersonsResponse;
import response.RegisterResponse;

public class ServerProxy {

    private String serverHost;
    private String port;

    public ServerProxy(String serverHost, String port) {
        this.serverHost = serverHost;
        this.port = port;
    }

    public RegisterResponse register(RegisterRequest r) {
        try {
            URL url = new URL("http://" + serverHost + ":" + port + "/user/register");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();
            String reqData = "{" + "\"username\":\"" + r.getUsername() + "\"," + "\"password\":\"" + r.getPassword() + "\","
                    + "\"email\":\"" + r.getEmail() + "\"," + "\"firstName\":\"" + r.getFirstName() + "\"," + "\"lastName\":\"" + r.getLastName() + "\"," +
                    "\"gender\":\"" + r.getGender() + "\"" + "}";
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                return (RegisterResponse) gson.fromJson(respData, RegisterResponse.class);
            }
            else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                return (RegisterResponse) gson.fromJson(respData, RegisterResponse.class);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginResponse login(LoginRequest r) {
        try {
            URL url = new URL("http://" + serverHost + ":" + port + "/user/login");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();
            String reqData = "{" + "\"username\":\"" + r.getUsername() + "\"," + "\"password\":\"" + r.getPassword() + "\"" + "}";
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                return (LoginResponse) gson.fromJson(respData, LoginResponse.class);
            }
            else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                return (LoginResponse) gson.fromJson(respData, LoginResponse.class);
            }

        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PersonsResponse persons(String authToken) {
        try {
            URL url = new URL("http://" + serverHost + ":" + port + "/person");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.connect();
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                return (PersonsResponse)  gson.fromJson(respData, PersonsResponse.class);
            }
            else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                return (PersonsResponse)  gson.fromJson(respData, PersonsResponse.class);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EventsResponse events(String authToken) {
        try {
            URL url = new URL("http://" + serverHost + ":" + port + "/event");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.connect();
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                return (EventsResponse)  gson.fromJson(respData, EventsResponse.class);
            }
            else {
                InputStream respBody = http.getErrorStream();
                String respData = readString(respBody);
                Gson gson = new Gson();
                return (EventsResponse)  gson.fromJson(respData, EventsResponse.class);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
