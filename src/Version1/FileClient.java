package Version1;
import java.io.*;
import java.net.*;
import java.nio.file.*;
/**
 * Created by 巩汝何 on 2017/7/11.
 */
public class FileClient {
    Socket s;
    String host;
    int port;
    DataInputStream dis;
    DataOutputStream dos;
    String UserName;
    //BufferedReader br;
    FileWriter fw;
    FileClient()throws IOException//初始化
    {
        host="";
        port=GetHostPort();//得到主机地址和端口然后建立tcp连接
        s=new Socket(host,port);
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
        //br=new BufferedReader(dis);
    }
    int  GetHostPort() throws IOException {
        String ports="";
        FileReader setFile=new FileReader("ClientSet.txt");
        char c[]=new char[20];
        int length=setFile.read(c);
        for(int i=0;i<c.length;i++)
        {
            if(c[i]==' '){
                for(int j=0;j<i;j++)
                {
                    host+=c[j];
                }
                for(int j=i+1;j<c.length;j++)
                {
                    ports+=c[j];
                }
            }
        }
        return Integer.parseInt(ports);
    }
    public void main(String args[]) throws IOException {
        switch (args[0])
        {
            case "upload"://在这里要调用文件上传程序
                UpLoad(args[1]);
                break;
            case "remove":
                ;
                break;
            case "download":
                ;
                break;
        }
    }
    public int UpLoad(String filepath) throws IOException {
        File f=new File(filepath);
        //传输信息到文件服务器
        dos.writeInt(0);
        dos.writeChars(f.getName());//传文件名过去
        dos.writeLong(f.length());//传文件大小过去
        dos.flush();//强制输出缓存当中的数据
        //开始
        String sb="";
        String ip1="",port1="",ip2="",port2="",uuid="";
        char c;//服务器传输过来的数据格式"xxx.xxx.xxx.xxx 1234 xxx.xxx.xxx.xxx 1235 xxxxxxxq（uuid）"
        while((c=dis.readChar())!='q')
        {
            sb+=c;//获得主、次节点的端口和ip地址
        }
        int numofspace=0;//空格的数量
        for(int i=0;i<sb.length();i++)//得到主次节点的ip和端口号
        {
            if(sb.charAt(i)==' ')
                numofspace++;
            if(numofspace==1)
                ip1=sb.substring(0,i-1);
            else if(numofspace==2)
            {
                port1=sb.substring(ip1.length(),i-1);
            }
            else if(numofspace==3)
            {
                ip2=sb.substring(ip1.length()+port1.length()+1,i-1);
            }
            else if(numofspace==4)
            {
                port2=sb.substring(ip1.length()+port1.length()+ip2.length()+2,i-1);
                uuid=sb.substring(i+1,sb.length()-2);
            }
        }
        s.close();//关闭与服务器端的连接
        CreateLinkToNode(ip1,port1);//建立与主节点的链接

        if(f!=null)//如果文件存在
        {
            FileInputStream fin=new FileInputStream(f);
            byte[] sendByte=new byte[1024];
            dos.writeUTF(uuid);//传输文件名过去？
        }
        return 0;
    }
    public void CreateLinkToNode(String ip,String ports) throws IOException {
        InetAddress address=Inet4Address.getByName(ip) ;
        s = new Socket(address,Integer.parseInt(ports));
        dos=new DataOutputStream(s.getOutputStream());

        //s=new Socket()
    }
}
