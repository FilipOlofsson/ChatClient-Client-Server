package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    static ServerSocket serverSocket;

    static final int PORT = 54123;
    static final int MAX_USERS = 10;
    static int CONNECTED_USERS = 0;

    static Room lobby;

    public static void main(String[] args) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initialize() throws IOException {
        lobby = new Room("Lobby", 0);
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server started. Waiting for connections...");
        while(CONNECTED_USERS < MAX_USERS) {
            CONNECTED_USERS++;
            User user = new User(serverSocket.accept(), CONNECTED_USERS);
            lobby.Users.add(user);
            new Thread(user).start();
            user.setRoom(lobby);
        }
    }

}
