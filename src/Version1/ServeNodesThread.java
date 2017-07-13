package Version1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Below project FDS
 * Created by Lee_rw on 2017/7/12.
 */
public class ServeNodesThread extends Thread {

    private Socket storageNode = null;

    public ServeNodesThread(Socket storageNode) {
        this.storageNode = storageNode;
    }


    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(storageNode.getInputStream());
            DataOutputStream dos = new DataOutputStream(storageNode.getOutputStream());
            while (true) {
                String fileUUID = new String();
                char c;
                while ((c = dis.readChar()) != -1) {
                    fileUUID += c;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
