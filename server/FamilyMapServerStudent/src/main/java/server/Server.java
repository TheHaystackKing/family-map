package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        new Server().run(port);
    }

    private void run(int port) {
        System.out.println("Initializing HTTP Server");
        InetSocketAddress serverAddress = new InetSocketAddress(port);

        try {
            server = HttpServer.create(serverAddress, MAX_WAITING_CONNECTIONS);
        }
        catch(IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");
        server.createContext("/", new FileHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/fill/", new FillHandler());
        server.createContext("/person", new FamilyMembersHandler());
        server.createContext("/person/", new SinglePersonHandler());
        server.createContext("/event", new FamilyEventsHandler());
        server.createContext("/event/", new SingleEventHandler());


        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }
}
