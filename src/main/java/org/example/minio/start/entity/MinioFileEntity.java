package org.example.minio.start.entity;

/**
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022/6/19 19:09
 */
public class MinioFileEntity {

    /**
     * 文件id
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String fileSuffix;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

}
