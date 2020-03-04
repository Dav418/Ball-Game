import java.net.Socket;
import java.io.*;


public class Client implements Serializable, AutoCloseable  {
    private boolean ballHolder = false;

    boolean getBallHolder(){
        return ballHolder;
    }

    void setBallHolder(boolean b){
        ballHolder=b;
    }


    private PrintWriter out;
    private BufferedReader in;

    private Socket socket;

    private final int port = 80;

    BufferedReader getIn() {
        return in;
    }

    private int ID;
    int getID(){return ID;}

    private String fromServer;
    public Client() {

        try{
            socket = new Socket("localhost",port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));



        }catch(Exception e){
            System.out.println("hmmm looks like the socket didnt connect");
        }
    }

    void getMeMyID()throws IOException{ //assign id to each client so we know who is who
        out.println("ID_ASSIGN");
        fromServer = in.readLine();
        String[] input = fromServer.split(" ");
        ID = Integer.parseInt(input[0]);
        if(input[1].equals("true")){
            setBallHolder(true);
        }
    }


    void passBall(String toID){
        setBallHolder(false);
        out.println("PASS_BALL "+toID);
    }


    @Override
    public void close() throws Exception {
        in.close();
        out.close();
    }
}
