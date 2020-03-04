import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class serverListener implements Runnable {
    private final Socket socket;

    private GameFunctions Game;
    private Player player;
    private ServerGUI gui;
    public serverListener(Socket socket, int ID, GameFunctions Game, ServerGUI gui){
        this.socket=socket;
        this.Game=Game;
        this.gui=gui;
        //create new player and add it
        player = new Player(ID, socket);
        Game.addToList(player);
    }

    @Override
    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                String[] input = inputLine.split(" ");
                String command = input[0];
                String playerID = null ;

                if(input.length > 1){
                    playerID = input[1];
                }

                switch (command.toLowerCase()) {

                    case "id_assign":
                        Game.updateClients(out,player);
                        gui.addToConsole("New player joined with id "+ player.getID());
                        Game.printAllClients(gui);
                        break;

                    case "pass_ball":
                        Game.passBall(playerID);
                        gui.addToConsole("Passing ball to " + playerID);
                        Game.printAllClients(gui);
                        break;
                }
            }
        } catch (IOException e) {
            Game.removeFromList(player);
            gui.addToConsole("Lost connection from "+player.getID());
            Game.printAllClients(gui);
        }
    }
}