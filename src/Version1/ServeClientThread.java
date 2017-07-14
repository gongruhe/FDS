package Version1;

import java.net.ServerSocket;

/**
 * Below project FDS
 * Created by Lee_rw on 2017/7/12.
 */
public class ServeClientThread extends Thread {

    private ServerSocket serverSocket = null;
    private ClientStrategy ios = null;

    public ServeClientThread(ServerSocket serverSocket, ClientStrategy ios) {
        this.serverSocket = serverSocket;
        this.ios = ios;
    }

    public boolean isIdle() {
        return serverSocket == null;
    }

    public synchronized void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        notifyAll();
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ios.service(serverSocket);
            serverSocket = null;
        }
    }
}
