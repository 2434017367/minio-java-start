package org.example.minio.start.exception;

/**
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022/6/19 17:34
 */
public class MinioHttpException extends RuntimeException {

    public MinioHttpException(String message) {
        super(message);
    }

}
