import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private final static int port = 80;
    private static int ID = 0;
    private static GameFunctions Game = new GameFunctions();
    private static ServerGUI gui = new ServerGUI();

    public static void main(String[] args) {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
            gui.addToConsole("Waiting for incoming connections...");

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new serverListener(socket, ID, Game, gui)).start();
                ID++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}