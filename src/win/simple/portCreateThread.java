package win.simple;

import java.io.IOException;
import java.net.ServerSocket;

public class portCreateThread extends Thread{
    private int port = 0;

    public portCreateThread(int port1) {
        port = port1;
        start();
    }

    public void run() {
        int clientPort = port;

        try {
            ServerSocket projectServer = new ServerSocket(clientPort);
            while(true) {
                new projectThread(projectServer.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
