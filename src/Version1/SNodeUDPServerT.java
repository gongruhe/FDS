package Version1;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;

/**
 * Created by 巩汝何 on 2017/7/14.
 */
public class SNodeUDPServerT implements Runnable{
    DatagramSocket socket=null;
    DatagramPacket packet=null;
    int port;//这个port必须是服务器的port
    Inet4Address address;//服务器的ip地址
    SNodeUDPServerT(int port, Inet4Address address) throws SocketException {
        socket=new DatagramSocket(port);//这个port怎么选……都行？随便选？
        this.port=port;
        this.address=address;
    }
    @Override
    public void run() {//由这个来处理文件的删除以及完成
        while(true )
        {
            try {
                String Command;
                String Username;
                String uuid;
                socket.receive(packet);
                String message=new String (packet.getData(),0,packet.getLength());
                Command=""+message.charAt(0);
                message=message.substring(1,message.length());//去掉第一个；
                FileInfo tempInfo = new FileInfo(message);
                //处理服务器发来的消息
                //消息格式："r (FileInfo)Username uuid"
                Username = tempInfo.getUsername();
                uuid = tempInfo.getUuid();
                File fu=new File("e:"+ File.separator+Username+File.separator+uuid);
                //有没有必要检验是否存在
                if(fu.exists())
                    System.out.println("文件存在");
                fu.delete();
                message="Delete Succeed";
                packet=new DatagramPacket(message.getBytes(),message.length(),address,port);
                socket.send(packet);
            } catch (IOException e) {
            e.printStackTrace();
            }
        }
    }
    public static void ConfimUpload()
    {

    }
}
