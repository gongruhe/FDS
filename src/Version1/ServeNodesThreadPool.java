package Version1;

import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 * Below project FDS
 * Created by Lee_rw on 2017/7/13.
 */
public class ServeNodesThreadPool extends NodeStrategy {
    private ArrayList<ServeNodesThread> serveNodesThreads = new ArrayList<>();
    private final int INIT_THREADS = 50;
    private final int MAX_THREADS = 200;
    private DatagramSocket serverSocket = null;
    private NodeStrategy ios = null;

    public ServeNodesThreadPool(DatagramSocket serverSocket, NodeStrategy ios) {
        this.serverSocket = serverSocket;
        this.ios = ios;
        for (int i = 0; i < INIT_THREADS; i++) {
            ServeNodesThread t = new ServeNodesThread(serverSocket, ios);
            t.start();
            serveNodesThreads.add(t);
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //等待服务器中的ServeNodesThreads都运行
        }
    }

    @Override
    public void service(DatagramSocket serverSocket) {
        ServeNodesThread t = null;
        boolean found = false;
        for (int i = 0; i < serveNodesThreads.size(); i++) {
            t = serveNodesThreads.get(i);
            if (t.isIdle()) {
                found = true;
                break;
            }
            if (!found) {
                t = new ServeNodesThread(this.serverSocket, ios);
            }
        }
    }
}
