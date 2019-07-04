package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    static ArrayList<String> nameOfUsers = new ArrayList<>();


    ServerHandler(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }


    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }


    void handle(Object message) throws IOException, ClassNotFoundException {
        if (nameOfUsers.contains(message))
            outputStream.writeObject(false);
        else {
            outputStream.writeObject(true);
            System.out.println(message);
            System.out.println(message.getClass().getSimpleName());
            nameOfUsers.add((String) message);
        }
    }

}
