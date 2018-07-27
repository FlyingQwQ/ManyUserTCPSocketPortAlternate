package win.simple;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class projectThread extends Thread{
    private Socket socket;
    private Socket socket2;

    public projectThread(Socket socket1) {
        socket = socket1;
        try {
            socket2 = new Socket("127.0.0.1", 1031);		//连接到项目服务端
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    public void run() {
        Socket projectClientSocket = socket;
        Socket projectServerSocket = socket2;

        new Thread() {      //接收客户端发送的信息，提交给服务器
            @Override
            public void run() {
                byte[] btx = new byte[1024];
                int i = 0;

                while(true) {
                    try {
                        if(i == -1) {
                            break;
                        }
                        i = projectClientSocket.getInputStream().read(btx,0,1024);
                        if(i > 0) {
                            projectServerSocket.getOutputStream().write(btx,0, i);
                        }
                    } catch (SocketException e) {
                        System.out.println("Client : 软件导致连接中止：套接字写入错误");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread() {      //接收服务器发送的信息，提交给客户端
            @Override
          public void run() {
                byte[] btx = new byte[1024];
                int i = 0;

                while(true) {
                    try {
                        if(i == -1) {
                            break;
                        }
                        i = projectServerSocket.getInputStream().read(btx,0,1024);
                        if(i > 0) {
                            projectClientSocket.getOutputStream().write(btx,0,i);
                        }
                    }catch (SocketException e) {
                        try {
                            projectServerSocket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        stop();
                        System.out.println("Server : 软件导致连接中止：套接字写入错误");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
