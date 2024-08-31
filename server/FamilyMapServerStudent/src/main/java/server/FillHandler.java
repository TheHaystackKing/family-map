package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.RegisterRequest;
import response.FillResponse;
import response.PersonResponse;
import response.RegisterResponse;
import service.Fill;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class FillHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String urlPath = exchange.getRequestURI().toString();
                String values = urlPath.replaceAll("/fill/", "");
                String[] valueArray = values.split("/");
                String username = valueArray[0];
                int generations = 4;
                if(valueArray.length > 1) {
                   generations = Integer.parseInt(valueArray[1]);
                }

                Fill service = new Fill();
                FillResponse response = service.fill(username, generations);

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
