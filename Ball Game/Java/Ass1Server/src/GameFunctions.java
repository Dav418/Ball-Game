import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class GameFunctions {
    private final List<Player> clientList;
    GameFunctions(){
        clientList = Collections.synchronizedList(new ArrayList());
    }

    // this is so the clients know whos in the game
    void updateClients(PrintWriter out, Player player){
        int newID = player.getID();
        out.println(newID + " " + player.hasBall());
        for (Player p :clientList) {
            if(p.getID() != newID){
               p.getOut().println("NEW_PLAYER "+newID);
            }
            out.println("NEW_PLAYER " + p.getID());
        }

    }

    void addToList(Player p){
        clientList.add(p);
        if(clientList.size() == 1){
            p.giveBall();
        }

    }

    void removeFromList(Player player){

        for(Player p : clientList){
            p.getOut().println("DC "+player.getID());
        }

        if(player.hasBall()){
            int randomID = passBallRandom(player.getID());
            passBall(String.valueOf(randomID));
        }

        clientList.remove(player);

    }

    private int passBallRandom(int dcID){
        boolean passed = false;
        int min = clientList.get(0).getID();
        int max = clientList.get(clientList.size()-1).getID();
        int rand = -1;
        while(!passed){

            if(max==min){ // stop infinite loop if everyone has left
                passed=true;
            }

            Random r = new Random();
            int rando = r.nextInt((max - min) + 1) + min;

            for (Player p : clientList){
                if(p.getID() == rando && rando != dcID){
                    rand=rando;
                    passed = true;
                }
            }
        }
        return rand;
    }

    void passBall(String toID){

        if(!findIfUserExists(Integer.parseInt(toID))){
            toID = "-1";
        }else{
            for (Player p:clientList) { // finds who was the ball
                if (p.hasBall()){
                    p.takeBall(); // takes the ball
                }
            }
        }

        for(Player pTo : clientList){
            //finds who the ball has been passed to
            if(pTo.getID() == Integer.parseInt(toID)){
                pTo.giveBall();
            }
            //broadcasts to everyone who now has the ball
            pTo.getOut().println("BALL_HAS_BEEN_PASSED "+toID);
        }

    }

    private boolean findIfUserExists(int ID){
        boolean exists = false;
        for(Player p: clientList){
            if(p.getID()==ID){
                exists=true;
            }
        }
        return exists;
    }



    void printAllClients(ServerGUI gui){
        gui.addToConsole("----------------------------------------");
        gui.addToConsole("Printing all clients currently connected");
        gui.addToConsole("~~");
        int ballID = -1;
        for(Player p: clientList){
            gui.addToConsole("Player ID "+p.getID());
            if(p.hasBall()){
                ballID=p.getID();
            }
        }
        gui.addToConsole("~~");
        gui.addToConsole("List printing complete");
        if(ballID == -1){
            gui.addToConsole("No one has the ball, waiting for a connection...");
        }else{
            gui.addToConsole("The player with the ball currently is "+ ballID);
        }

        gui.addToConsole("----------------------------------------");
    }

}
