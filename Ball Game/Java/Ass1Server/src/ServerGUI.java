import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class ServerGUI extends JFrame {

    private JTextArea console;

    ServerGUI(){
        JFrame server = new JFrame("Server");
        server.setPreferredSize(new Dimension(400,500));


        console = new JTextArea(20,20);

        JScrollPane consolePan = new JScrollPane(console,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        DefaultCaret caret = (DefaultCaret)console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        console.setEditable(false);

        server.add(consolePan);

        server.pack();
        server.setVisible(true);
        server.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    void addToConsole(String msg){
        console.append(msg + "\n");
    }
}
