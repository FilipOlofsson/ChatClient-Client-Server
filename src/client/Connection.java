package client;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Connection implements Runnable {

    Socket socket;

    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    Connection(String IP, int port) throws IOException {
        socket = new Socket(IP, port);
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void send(String message) throws IOException {
        dataOutputStream.writeUTF(message);
    }

    @Override
    public void run() {
        while(true) {
            try {
                System.out.println(dataInputStream.readUTF());
            } catch(SocketException e) {
                System.out.println("Server has stopped responding.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
