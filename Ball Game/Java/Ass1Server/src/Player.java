
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {

    private boolean ball;
    boolean hasBall(){ return ball; }
    void giveBall(){ ball=true; }
    void takeBall(){ ball=false; }

    private int ID;
    int getID() {
        return ID;
    }

    private PrintWriter out;
    PrintWriter getOut(){ return out; }

    Player(int ID, Socket socket){
        this.ID=ID;

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
