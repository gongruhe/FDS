package Version1;

import javax.xml.soap.Node;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by 巩汝何 on 2017/7/14.
 */
//实现文件的操作类，文件删除操作不通过这个tcp而是通过udp
public class SNodeFileServer implements Runnable{
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    NodeInfo nodeInfo;
    SNodeFileServer(Socket socket,NodeInfo ni) throws IOException {
        this.socket=socket;//建立连接以及 获得数据输出入输出流
        dis=new DataInputStream(socket.getInputStream());
        dos=new DataOutputStream(socket.getOutputStream());
        nodeInfo=ni;
    }
    public void run()
    {
        try {
            int command=dis.readInt();//读取一个命令
            switch(command)
            {
                case 0://从客户端接收文件
                    FileAccept();
                    break;
                case 1:
                    BFileAccept();//从主节点来接收文件
                case 2:
                    FileSend();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //作为备份节点接收文件
    public void BFileAccept()
    {

    }
    //这个函数是作为主节点来接收文件的程序
    public void FileAccept() throws IOException {
        //读取从客户端发过来的信息
        char c;
        String Msg="";
        String BFip="";
        String BFport="";
        String uuid="";
        String Username="";
        long filelength=0;
        while((c=dis.readChar())!='q')//格式为"xxx.xxx.xxx 1234 Username uuidq"
        {
            Msg+=c;
        }
        int numofSpace=0;
        for(int i=0;i<Msg.length();i++)
        {
            if(Msg.charAt(i)==' ')
            {
                numofSpace++;
                if(numofSpace==1)
                {
                    BFip=Msg.substring(0,i-1);
                }
                else if(numofSpace==2)
                {
                    BFport=Msg.substring(BFip.length()+1,i-1);
                }
                else if(numofSpace==3)
                {
                    Username=Msg.substring(BFip.length()+BFport.length()+1,i-1);
                    uuid=Msg.substring(BFip.length()+BFport.length()+Username.length()+1,i-2);
                }
            }
        }
        filelength=dis.readLong();
        //判断容量是否足够存储
        if(nodeInfo.getRemainCapacity()>filelength)
        {
            dos.writeChar('o');
            dos.flush();
            if(dis.readChar()=='s')//开始传送文件
            { //执行文件接收程序
                File F=new File("e:\\"+Username+"\\"+"uuid.storage");
                FileOutputStream fos=new FileOutputStream(F);
                byte[]inputByte=new byte[1024];
                int length=0;
                while(true)
                {
                    if(dis!=null)
                    {
                        length = dis.read(inputByte,0,inputByte.length);
                    }
                    if(length==-1)
                        break;
                    fos.write(inputByte,0,length);
                    fos.flush();
                 }
                System.out.println("完成接收");
                fos.close();
                dis.close();
                socket.close();
                dos.close();
                LinkToBFNode(BFip,BFport);//接下来向备份节点传输文件请求
                dos.writeInt(1);
                dos.writeChars("");

            }
        }
        else
        {
            dos.writeChar('n');
            System.out.println("节点容量不足");
        }
    }
    public void LinkToBFNode(String ip,String port) throws IOException {
        InetAddress address= Inet4Address.getByName(ip) ;
        socket = new Socket(address,Integer.parseInt(port));
        dos=new DataOutputStream(socket.getOutputStream());
        dis=new DataInputStream(socket.getInputStream());
    }
    public void FileSend()
    {

    }


}
