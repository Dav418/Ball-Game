import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

class clientComponentClass{

    // Class that holds all the components to be modified
    // This is so I don't end up with 4 lists, just a list of everyones component

    private int clientId;
    private JPanel clientPanel;
    private JLabel clientIdLab;
    private JButton clientGiveBallBtn;

    int getClientId(){
        return clientId;
    }
    JPanel getClientPanel(){
        return clientPanel;
    }

    JLabel getClientIdLab(){
        return clientIdLab;
    }

    JButton getClientGiveBallBtn(){
        return clientGiveBallBtn;
    }

    clientComponentClass(int i,JLabel k, JButton l){ //mainClient. Dont need to modify its JPanel
        this.clientId=i;
        this.clientIdLab=k;
        this.clientGiveBallBtn=l;
    }

    clientComponentClass(int i,JPanel j, JLabel k, JButton l){
        this.clientId=i;
        this.clientPanel =j;
        this.clientIdLab=k;
        this.clientGiveBallBtn=l;
    }

}

class GameFrame extends JFrame {

    private JFrame frame;
    private JPanel mainPanel;
    private Client client;
    private JTextArea console;

    private boolean hasBall;

    private ArrayList<clientComponentClass> componentList = new ArrayList<>();


    GameFrame(Client c){
        this.client = c;
        hasBall = client.getBallHolder();
        //JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("Ball Game ID#" + c.getID());
        frame.setPreferredSize(new Dimension(400, 500));
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,0));

        JPanel mainClient = new JPanel();
        mainClient.setLayout(new BoxLayout(mainClient, BoxLayout.Y_AXIS));

        JLabel idLab = new JLabel();
        idLab.setText("ID:"+Integer.toString(c.getID())+" (you)");
        mainClient.add(idLab);

        JButton sendBall = new JButton("Give ball");
        sendBall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.passBall(String.valueOf(client.getID()));

                hasBall = client.getBallHolder();
            }
        });

        if(hasBall){
            sendBall.setEnabled(true);
            idLab.setForeground(Color.blue);
        }else{
            sendBall.setEnabled(false);
            idLab.setForeground(Color.red);
        }

        sendBall.setPreferredSize(new Dimension(100,40));
        mainClient.add(sendBall);


        console = new JTextArea(5,20);

        JScrollPane consolePan = new JScrollPane(console,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        DefaultCaret caret = (DefaultCaret)console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        console.setEditable(false);


        mainPanel.add(mainClient);
        frame.add(consolePan, BorderLayout.SOUTH);
        frame.add(mainPanel,BorderLayout.WEST);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        console.setText("~~GUI Initialised~~\n");
        componentList.add(new clientComponentClass(c.getID(),idLab,sendBall));
    }
    void addNewClient(String newID){
        JPanel newClient = new JPanel();
        newClient.setLayout(new BoxLayout(newClient,BoxLayout.Y_AXIS));

        JLabel idLab = new JLabel();
        idLab.setText(newID);
        newClient.add(idLab);

        JButton sendBall = new JButton("Give ball");
        sendBall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.passBall(newID);
                hasBall = client.getBallHolder();

            }
        });
        sendBall.setPreferredSize(new Dimension(80,40));

        hasBall=client.getBallHolder();
        idLab.setForeground(Color.red);

        if(hasBall){
            sendBall.setEnabled(true);
        }else{
            sendBall.setEnabled(false);
        }

        newClient.add(sendBall);
        mainPanel.add(newClient);

        frame.revalidate();
        frame.repaint();
        componentList.add(new clientComponentClass(Integer.parseInt(newID),newClient,idLab,sendBall));

    }

    void addTextToConsole(String msg){
        console.append(msg+"\n");
    }

    void setAllBtns(boolean cmd){
        for(clientComponentClass i: componentList){
            i.getClientGiveBallBtn().setEnabled(cmd);
        }
    }

    void setAllLab(int ballID){
        for(clientComponentClass i: componentList){
            if(ballID == i.getClientId()){
                i.getClientIdLab().setForeground(Color.blue);
            }
            else{
                i.getClientIdLab().setForeground(Color.red);
            }
        }
        addTextToConsole("Ball has been passed to "+ballID+"!");
    }

    void removePlayer(String removedID){
        JPanel panToRemove = null;
        clientComponentClass rem = null;

        for(clientComponentClass i: componentList ){
            if(i.getClientId() == Integer.parseInt(removedID)){
                panToRemove = i.getClientPanel();
                rem=i;
            }
        }

        if(panToRemove == null){
            addTextToConsole("Somehow failed to remove disconnected player.");
        }else {
            mainPanel.remove(panToRemove);
            componentList.remove(rem);
            frame.revalidate();
            frame.repaint();
        }

    }

}
