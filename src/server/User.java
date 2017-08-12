package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class User implements Runnable {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    int id;
    private String name;

    Room current;

    User(Socket socket, int id) throws IOException {
        this.socket = socket;
        this.id = id;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    void setRoom(Room room) {
        this.current = room;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while(true) {
            try {
                processMessage(dataInputStream.readUTF());
            } catch(SocketException e) {
                System.out.println("Client has stopped responding.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) throws IOException {
        dataOutputStream.writeUTF(message);
    }

    private void processMessage(String message) throws IOException {
        String[] split = message.split("@!");
        if(split[0].equals("setName")) {
            setName(split[1]);
            System.out.println("User with id " + id + "'s name set to " + message.split("@!")[1]);
        }
        if(split[0].equals("groupSend")) {
            for(User user : current.Users) {
                if(user.id == this.id)
                    user.send(name + ": " + split[1]);
                else
                    user.send(split[1]);
            }
            System.out.println(name + " sent " + split[1]);
        }
    }
}
