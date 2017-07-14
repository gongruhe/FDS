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
        server=new ServerSocket(info.getPort());
    }
    public void NodeStart() throws IOException {
        Socket socket =server.accept();
        invoke(socket);
    }
    public void invoke(final Socket client)
    {
        new  Thread(new Runnable() {
            @Override
            public void run() {//在这里写run函数
                //do things
            }
        }).start();
    }
    public NodeInfo getInfo() {
        return info;
    }

    public void setInfo(NodeInfo info) {
        this.info = info;
    }
}
