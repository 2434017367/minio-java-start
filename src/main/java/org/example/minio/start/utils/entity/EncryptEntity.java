package org.example.minio.start.utils.entity;

/**
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022/6/19 13:08
 */
public /**
 * 加密后的属性
 */
class EncryptEntity {

    /**
     * appKey
     */
    private String appKey;
    /**
     * 时间戳
     */
    private String stamp;

    public EncryptEntity(String appKey, String stamp) {
        this.appKey = appKey;
        this.stamp = stamp;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getStamp() {
        return stamp;
    }
}
