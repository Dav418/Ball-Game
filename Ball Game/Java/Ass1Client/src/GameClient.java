import java.io.BufferedReader;
import java.io.IOException;

public class GameClient {

    public static void main(String[] args) {

            Thread listenT = new Thread(() -> {
                GameFrame frame = null;
                try (Client c = new Client()){
                    c.getMeMyID();
                    frame = new GameFrame(c);
                    BufferedReader in = c.getIn();
                    String fromServer;
                    while(true) {
                        fromServer = in.readLine();
                        System.out.println("from server: " + fromServer);
                        String[] input = fromServer.split(" ");
                        String command = input[0];
                        String playerFrom = input[1];
                        switch (command.toLowerCase()) {

                            case "new_player":
                                System.out.println("new id "+playerFrom);
                                if (Integer.parseInt(playerFrom) != c.getID()) {
                                    frame.addNewClient(playerFrom);
                                    frame.addTextToConsole("New player joined with ID: "+playerFrom+"!");
                                }
                                break;

                            case "ball_has_been_passed":
                                if(playerFrom.equals("-1")){
                                    frame.addTextToConsole("Oh o something went wrong, and user "+ playerFrom+" doesnt exist!");
                                }else{
                                    if(Integer.parseInt(playerFrom) == c.getID()){
                                        c.setBallHolder(true);
                                        frame.setAllBtns(true);
                                    }else{
                                        frame.setAllBtns(false);
                                    }
                                    frame.setAllLab(Integer.parseInt(playerFrom));
                                }


                                break;

                            case "dc":
                                frame.removePlayer(playerFrom);
                                frame.addTextToConsole("Player #"+playerFrom+" disconnected!");
                                break;
                        }
                    }
                } catch(Exception e){
                    frame.addTextToConsole("Disconnected from server.");
                }
            });
            listenT.start();

    }
}