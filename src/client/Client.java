package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static Connection connection;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server IP?");
        String IP = scanner.nextLine();
        System.out.println("Server PORT?");
        int PORT = Integer.parseInt(scanner.nextLine());
        try {
            connection = new Connection(IP, PORT);
        } catch (IOException e) {
            System.out.println("Could not connect to the server...");
            main(null);
        }
        new Thread(connection).start();
        System.out.println("Choose a nickname.");
        connection.send("setName@!" + scanner.nextLine());


        while(true) {
            connection.send(scanner.nextLine());
        }
    }

}
