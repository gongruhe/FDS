package Version1;

/**
 * Created by 巩汝何 on 2017/7/11.
 */
import com.sun.security.ntlm.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class StorageNode {
    NodeInfo info;
    ServerSocket server;
    /*public StorageNode(IOStrategy ios) throws IOException {
        ServerSocket ss=new ServerSocket(info.getPort());
        System.out.println("StorageNode is Ready");
        while(true)
        {
            Socket socket=ss.accept();
            ios.service(socket);
        }
    }*/
    StorageNode() throws IOException {

        new Thread(new SNodeServerThread(info.getPort(),info)
        ).start();

    }

    public NodeInfo getInfo() {
        return info;
    }

    public void setInfo(NodeInfo info) {
        this.info = info;
    }
}
