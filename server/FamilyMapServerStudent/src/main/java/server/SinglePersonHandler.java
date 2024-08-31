package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import response.PersonResponse;
import service.Person;
import service.Verify;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class SinglePersonHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                exchange.getRequestBody().close();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    Verify verify = new Verify();
                    if(verify.verify(authToken)) {
                        String urlPath = exchange.getRequestURI().toString();
                        String personID = urlPath.replaceAll("/person/", "");
                        Person service = new Person();
                        PersonResponse response = service.person(authToken, personID);

                        if(response.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }

                        OutputStream resBody = exchange.getResponseBody();
                        Gson gson = new Gson();
                        String jsonResult = gson.toJson(response);
                        resBody.write(jsonResult.getBytes(StandardCharsets.UTF_8));
                        resBody.close();
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        OutputStream resBody = exchange.getResponseBody();
                        Gson gson = new Gson();
                        PersonResponse response = new PersonResponse("Error: Invalid auth token");
                        String jsonResult = gson.toJson(response);
                        resBody.write(jsonResult.getBytes(StandardCharsets.UTF_8));
                        resBody.close();
                    }
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                    exchange.getResponseBody().close();
                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
