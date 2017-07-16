package Version1;

/**
 * Created by 巩汝何 on 2017/7/14.
 */
import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;


public class SNodeUDPSenderT implements Runnable{
    DatagramSocket socket=null;
    DatagramPacket packet=null;
    NodeInfo ni;
    int port;
    Inet4Address address=null;
    SNodeUDPSenderT(int dport,Inet4Address daddress,NodeInfo ni) throws SocketException {
        DatagramSocket  socket = new DatagramSocket(dport,daddress);
        port=dport;
        this.ni=ni;
        address=daddress;
    }
    @Override
    public void run() {
        String ACK="";
        packet=new DatagramPacket(ni.getName().getBytes(),ni.getName().length());
        Timer t=new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },1000,2000);
    }
}
