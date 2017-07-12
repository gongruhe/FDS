package Version1;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by 巩汝何 on 2017/7/11.
 * 一个FileServer应用（暂时不考虑多个FileServer应用），提供文件存储节点StorageNode的管理功能，提供文件的管理功能。
 * FileServer应用提供文件存储节点的管理。提供存储文件的管理功能。
 * FileClient在进行文件上传和下载时，必须通过FileServer来获取文件存储在哪个节点，哪个备份节点的一些文件信息。
 * FileServer管理每个文件都需要通过文件编号来完成，在其内存中通过Map集合来管理所有文件。
 * 每个文件都需要在两个节点上进行1+1备份存储。FileServer分配存储节点需要考虑负载均衡。
 * 上传成功后，FileServer需要将文件编号信息传递给客户端，客户端以后通过该文件编号进行文件下载，文件删除。
 * 在FileServer管理所有文件信息。文件编号采用UUID, UUID.randomUUID().toString()
 * 在FileServer中需要管理所有存储节点信息。
 * FileServer在内存中需要管理后端FileStorage服务器的信息，包括名称，ip，端口，
 * 容量，实际容量，剩余容量，文件数量，是否可用等信息。
 * 文件信息包括：编号，文件原始名称，文件大小，主存储节点信息，备份节点信息，等等。
 * 这些信息建议使用集合来进行存储，同时要求支持序列化到文件中。在FileServer启动后，需要读取这些序列化信息，服务器退出时，需要
 * 保存这些序列化信息到文件中。
 */
public class FileServer {
    public  static Set<StorageNode> nodes = new HashSet<>();    //存储FileStorage服务器结点
    private static int FileServerPort = 6545;       //服务器的端口
    private ServerSocket serverSocket;
    private static Map<NodeInfo, StorageNode> nodeInfo = new HashMap<NodeInfo, StorageNode>();  //存储FileStorage服务器结点的信息
    private static Map<FileNum, FileInfo> fileSet = new HashMap<FileNum, FileInfo>();
    private static List<String> userNames = new ArrayList<String>();

    public FileServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main() throws IOException {
        ServerSocket ss  = new ServerSocket(FileServerPort);
        FileServer fs = new FileServer(ss);
        System.out.println("Server is ready to provide service");
        Socket s = ss.accept();
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        while (true) {
            int command = dis.readInt();
            switch (command) {
                case 0:
                    //上传文件
                    String message = new String();
                    char c;
                    while ((c = dis.readChar()) != '#') {
                        message += c;
                    }
                    String[] temp = message.split(",");
                    userNames.add(temp[0]);
                    String fileName = temp[1];
                    String fileLength = temp[2];
                    FileNum num = new FileNum();
                    FileInfo info = new FileInfo(fileName, Long.parseLong(fileLength));
                    fileSet.put(num, info);
                    break;
                case 1:
                    //下载文件

                    break;
            }
        }
    }

    private StorageNode findFreeNode() {
        StorageNode freeNode;
        Iterator<StorageNode> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            if ()
        }
    }


    private static class FileNum { //文件编号
        private UUID fileNum;
        public FileNum (UUID fileNum) {
            this.fileNum = fileNum;
        }

        public FileNum() {
            generateNum();
        }

        public UUID getFileNum() {
            return fileNum;
        }

        public void setFileNum(UUID fileNum) {
            this.fileNum = fileNum;
        }
        public void generateNum() {
           setFileNum(UUID.randomUUID());
        }

        @Override
        public String toString() {
            return fileNum.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FileNum fileNum1 = (FileNum) o;

            return fileNum != null ? fileNum.equals(fileNum1.fileNum) : fileNum1.fileNum == null;
        }

        @Override
        public int hashCode() {
            return fileNum != null ? fileNum.hashCode() : 0;
        }
    }
}
