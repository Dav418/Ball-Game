import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class GameFunctionsTest {

    GameFunctions g = new GameFunctions();
    Socket s;
    ServerSocket serverSocket = null;
    @org.junit.jupiter.api.Test
    void addToList() {

        try {
            serverSocket = new ServerSocket();
            s=serverSocket.accept();
            g.addToList(new Player(1,s));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @org.junit.jupiter.api.Test
    void updateClients() {
        try {
            serverSocket = new ServerSocket();
            s=serverSocket.accept();
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            g.updateClients(out,new Player(12,s) );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @org.junit.jupiter.api.Test
    void removeFromList() {
        try {
            serverSocket = new ServerSocket();
            s=serverSocket.accept();
            g.removeFromList(new Player(4,s));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @org.junit.jupiter.api.Test
    void passBall() {
        g.passBall("3");
    }
}