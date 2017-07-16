package Version1;

/**
 * Created by 巩汝何 on 2017/7/11.
 */
import com.sun.security.ntlm.Server;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class StorageNode {
    NodeInfo info;
    ServerSocket server;
    Inet4Address Serverip;
    String Serverport;
    String ip;
    StorageNode() throws IOException {

        new Thread(new SNodeServerThread(info.getPort(),info)
        ).start();
        Serverip= (Inet4Address) Inet4Address.getByName(ip);//得到服务器的地址
        int port=GetServerHost();
        new Thread(new SNodeUDPSenderT(port,Serverip,info)).start();
        new Thread(new SNodeUDPServerT(port,Serverip)).start();

    }
    public int GetServerHost() throws IOException {//获取主机的ip和端口
        FileReader sf=new FileReader("e:\\NodeSet.txt");
        char[] c=new char[20];
        int length=sf.read(c);
        String s=new String(c);

        String port="";
        String[] temp=s.split(" ");
        ip=temp[0];
        port=temp[1];

        return Integer.parseInt(ip);
    }
    public NodeInfo getInfo() {
        return info;
    }

    public void setInfo(NodeInfo info) {
        this.info = info;
    }
}
