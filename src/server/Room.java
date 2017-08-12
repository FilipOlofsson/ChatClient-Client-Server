package server;

import java.util.LinkedList;
import java.util.List;

public class Room {
    List<User> Users = new LinkedList<>();

    String name;
    int id;

    public Room(String name, int id) {
        this.name = name;
    }

}
