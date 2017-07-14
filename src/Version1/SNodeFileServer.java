package Version1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by 巩汝何 on 2017/7/14.
 */
//实现文件的操作类
public class SNodeFileServer implements Runnable{
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    SNodeFileServer(Socket socket) throws IOException {
        this.socket=socket;//建立连接以及 获得数据输出入输出流
        dis=new DataInputStream(socket.getInputStream());
        dos=new DataOutputStream(socket.getOutputStream());
    }
    public void run()
    {
        try {
            int command=dis.readInt();//读取一个命令
            switch(command)
            {
                case 0:
                    FileAccept();
                    break;
                case 1:
                    FileSend();
                    break;
                case 2:
                    FileDelete();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void FileAccept()
    {

    }
    public void FileSend()
    {

    }
    public void FileDelete()
    {

    }

}
