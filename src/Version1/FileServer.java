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

    public static List<StorageNode> nodes = new ArrayList<>();    //存储FileStorage服务器结点
    private static int clientPort = 6000;       //服务器接收客户端的端口
    private static int storageNodePort = 6001;
    private ServerSocket serveClientSocket;
    private ServerSocket serveNodeSocket;
    private static Map<NodeInfo, StorageNode> nodeInfo = new HashMap<>();  //存储FileStorage服务器结点的信息
    private static Map<FileNum, FileInfo> fileSet = new HashMap<>();
    private static List<String> userNames = new ArrayList<>();

    public FileServer(ServerSocket serverSocket, ServerSocket serveNodeSocket) {
        this.serveClientSocket = serverSocket;
        this.serveNodeSocket = serveNodeSocket;
    }

    public static void main(String[] args) throws IOException {
        FileServer fs = new FileServer(new ServerSocket(clientPort), new ServerSocket(storageNodePort));
        System.out.println("Server is ready to provide service");



    }


    /**
     * @param fileLength 需要上传的文件大小
     * @return null    没有找到
     * freeNode    第一个满足大小的结点
     */
    public static StorageNode[] findFreeNode(long fileLength) {
        StorageNode[] freeNode = null;
        Collections.sort(nodes, new Comparator<StorageNode>() {
            @Override
            public int compare(StorageNode o1, StorageNode o2) {
                if (o1.getInfo().getRemainCapacity() > o2.getInfo().getRemainCapacity()) {
                    return 1;
                }
                if (o1.getInfo().getRemainCapacity() < o2.getInfo().getRemainCapacity()) {
                    return -1;
                }
                return 0;
            }
        });
        for (StorageNode node :
                nodes) {
            if (node.getInfo().getRemainCapacity() > fileLength) {
                freeNode[0] = node;
                break;
            }
        }
        freeNode[1] = nodes.get(nodes.size() - 1);
        return freeNode;
    }

    public static class FileNum { //文件编号
        private UUID fileNum;

        public FileNum(UUID fileNum) {
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


    public static List<StorageNode> getNodes() {
        return nodes;
    }

    public static void setNodes(List<StorageNode> nodes) {
        FileServer.nodes = nodes;
    }

    public static Map<NodeInfo, StorageNode> getNodeInfo() {
        return nodeInfo;
    }

    public static void setNodeInfo(Map<NodeInfo, StorageNode> nodeInfo) {
        FileServer.nodeInfo = nodeInfo;
    }

    public static Map<FileNum, FileInfo> getFileSet() {
        return fileSet;
    }

    public static void setFileSet(Map<FileNum, FileInfo> fileSet) {
        FileServer.fileSet = fileSet;
    }

    public static List<String> getUserNames() {
        return userNames;
    }

    public static void setUserNames(List<String> userNames) {
        FileServer.userNames = userNames;
    }

    public ServerSocket getServeNodeSocket() {
        return serveNodeSocket;
    }

    public void setServeNodeSocket(ServerSocket serveNodeSocket) {
        this.serveNodeSocket = serveNodeSocket;
    }

    public ServerSocket getServeClientSocket() {
        return serveClientSocket;
    }

}
