package Version1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Below project FDS
 * Created by Lee_rw on 2017/7/13.
 */
public class ClientStrategy {

    private Socket client = null;

    public void service(ServerSocket serverSocket) {
        try {
            while (serverSocket != null) {
                client = serverSocket.accept();
            }
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            int command = dis.readInt();
            switch (command) {
                case 0:
                    String message = new String();
                    char c;
                    while ((c = dis.readChar()) != '#') {
                        message += c;
                    }
                    String[] temp = message.split(",");
                    FileServer.getUserNames().add(temp[0]);
                    String fileName = temp[1];
                    String fileLength = temp[2];
                    FileInfo info = new FileInfo(fileName, Long.parseLong(fileLength));
                    FileServer.FileNum num = new FileServer.FileNum();
                    FileServer.getFileSet().put(num, info);
                    StorageNode node[] = FileServer.findFreeNode(Long.parseLong(fileLength));
                    dos.writeChars(node[0].getInfo().getIp() + " " + node[0].getInfo().getPort() + " "
                            + node[1].getInfo().getIp() + " " + node[1].getInfo().getPort() + " " + num);
                    break;
                case 1:

                    break;
                case 2:

                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

