package Version1;
import java.io.*;
import java.net.*;
import java.nio.file.*;
/**
 * Created by 巩汝何 on 2017/7/11.
 * 栗仁武 201592169
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
        for(int i=0;i<length;i++)
        {
            if(c[i]==' '){
                for(int j=0;j<i;j++)
                {
                    host+=c[j];
                }
                for(int j=i+1;j<length;j++)
                {
                    if(c[j]==' ')
                    {
                        for(int k=i+1;k<j;k++)
                            port+=c[k];
                        for(int k=j+1;k<length;k++)
                            UserName+=c[k];
                    }
                }
            }
        }
        return Integer.parseInt(ports);
    }
    public void main(String args[]) throws Exception {
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
    public int UpLoad(String filepath) throws Exception {
        File f=new File(filepath);
        String SendMessage="";
        //传输信息到文件服务器
        dos.writeInt(0);
        SendMessage=""+UserName+","+f.getName()+","+f.length()+"#";
        dos.writeChars(SendMessage);
        dos.flush();//强制输出缓存当中的数据
        //adsf
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

        //进行文件加密
        FileEncryptAndDecrypt.encrypt(filepath,"12345",uuid);//新建了一个加密过后的文件
        String path = f.getPath();
        int index = path.lastIndexOf("\\");
        String destFile = path.substring(0, index)+"\\"+uuid;//目标文件生成
        File fsend =new File(destFile);
        if(f!=null)//如果文件存在
        {
            int length;
            FileInputStream fin=new FileInputStream(fsend);//传输文件
            byte[] sendByte=new byte[1024];
            dos.writeUTF(uuid);//传输文件名过去？
            while((length = fin.read(sendByte, 0, sendByte.length))>0){
                dos.write(sendByte,0,length);
                dos.flush();
            }
            //然后关闭文件流和socket
            fin.close();
            dos.close();
            s.close();
            fsend.delete();//删除 加密后的文件
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
