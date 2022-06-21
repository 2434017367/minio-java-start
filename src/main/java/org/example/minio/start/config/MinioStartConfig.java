package org.example.minio.start.config;

/**
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022/6/19 12:25
 */
public class MinioStartConfig {

    private static String APP_KEY;

    private static String SERVE_URL;

    private static Integer TIMEOUT = 15000;

    public static String getAppKey() {
        return APP_KEY;
    }

    public static void setAppKey(String appKey) {
        APP_KEY = appKey;
    }

    public static String getServeUrl() {
        return SERVE_URL;
    }

    public static void setServeUrl(String serveUrl) {
        SERVE_URL = serveUrl;
    }

    public static Integer getTimeout() {
        return TIMEOUT;
    }

    public static void setTimeout(Integer timeout) {
        MinioStartConfig.TIMEOUT = timeout;
    }
}
