package Version1;

/**
 * Below project FDS
 * Created by Lee_rw on 2017/7/12.
 */
public class FileInfo {
    private StringBuilder fileName;
    private long fileLength;

    public FileInfo(StringBuilder fileName, Long fileLength) {
        this.fileName = fileName;
        this.fileLength = fileLength;
    }

    public StringBuilder getFileName() {
        return fileName;
    }

    public void setFileName(StringBuilder fileName) {
        this.fileName = fileName;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileInfo fileInfo = (FileInfo) o;

        if (fileLength != fileInfo.fileLength) return false;
        return fileName != null ? fileName.equals(fileInfo.fileName) : fileInfo.fileName == null;
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (int) (fileLength ^ (fileLength >>> 32));
        return result;
    }
}
