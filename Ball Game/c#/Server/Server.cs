public class Server {
    
    private static int port = 80;
    
    private static int ID = 0;
    
    private static GameFunctions Game = new GameFunctions();
    
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for incoming connections...");
            while (true) {
                Socket socket = serverSocket.accept();
                (new Thread(new ServerListner(socket, ID, Game)) + start());
                ID++;
                System.println("fuck me")
            }
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
