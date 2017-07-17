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
import java.util.Date;
import java.util.Properties;

public class StorageNode {
    NodeInfo info;
    ServerSocket server;
    Inet4Address Serverip;
    String Serverport;
    String Nodename;
    String Nodeip;
    long capacity;
    int port;
    long fspace;
    int filenum;
    StorageNode() throws IOException {
        port=GetProperties();
        SetProperties();
        //Serverip= (Inet4Address) Inet4Address.getByName(ip);//得到服务器的地址
        //三个线程a
        int sp=Integer.parseInt(Serverport);
        new Thread(new SNodeServerThread(port,info,Serverip,sp)
        ).start();
        new Thread(new SNodeUDPSenderT(sp,Serverip,info)).start();
        new Thread(new SNodeUDPServerT(port,Serverip)).start();

    }
   /* public int GetServerHost() throws IOException {//获取主机的ip和端口
        FileReader sf=new FileReader("e:\\NodeSet.txt");
        char[] c=new char[20];
        int length=sf.read(c);
        String s=new String(c);

        String port="";
        String[] temp=s.split(" ");
        //ip=temp[0];
        port=temp[1];

        //return Integer.parseInt(ip);
    }*/
    public NodeInfo getInfo() {
        return info;
    }

    public void setInfo(NodeInfo info) {
        this.info = info;
    }
    int GetProperties() throws IOException {

        String port;
        Properties pps=new Properties();
        pps.load(new FileInputStream("NodeSet.properties")) ;
        Serverip= (Inet4Address) Inet4Address.getByName(pps.getProperty("ServerIp"));
        Serverport=pps.getProperty("ServerPort");
        port=pps.getProperty("NodePort");
        Nodeip=pps.getProperty("NodeIp");
        capacity=Long.parseLong(pps.getProperty("Volume"));

        return Integer.parseInt(port);
    }
    int SetProperties() throws IOException {
        File root=new File("e:"+File.separator+Nodename);
        fspace=root.getFreeSpace();
        filenum=root.listFiles().length;
        info =new NodeInfo(Nodename,Nodeip,port,capacity,capacity,fspace,filenum,true);
        String v=""+root.length();

        Properties pps=new Properties();
        InputStream in=new FileInputStream("NodeSet.properties");
        pps.load(in);

        /*pps.setProperty("ServerIp","123.456.789.345");//
        pps.setProperty("ServerPort","4321");
        pps.setProperty("NodePort","1234");*/
        pps.setProperty("Volume",v);
        OutputStream os=new FileOutputStream("NodeSet.properties");
        pps.store(os, "andieguo modify" + new Date().toString());// 保存键值对到文件中
        //OutputStream out=new FileOutputStream("ClientSet.properties");
        return 0;
    }
}
