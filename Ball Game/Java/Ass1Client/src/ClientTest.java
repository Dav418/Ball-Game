import java.io.IOException;


class ClientTest {


    @org.junit.jupiter.api.Test
    void getMeMyID() {
        Client c = new Client();
        try {
            c.getMeMyID();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void passBall() {
        Client c = new Client();

        c.passBall("20");
    }
}