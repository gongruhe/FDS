package Version1;

import java.net.DatagramSocket;

/**
 * Below project FDS
 * Created by Lee_rw on 2017/7/12.
 */
public class ServeNodesThread extends Thread {

    private DatagramSocket serverSocket =null;
    private NodeStrategy ios = null;


    public ServeNodesThread(DatagramSocket storageNode, NodeStrategy ios) {
        this.serverSocket = storageNode;
        this.ios = ios;
    }

    public boolean isIdle() {
        return serverSocket == null;
    }

    @Override
    public void run() {

    }
}
