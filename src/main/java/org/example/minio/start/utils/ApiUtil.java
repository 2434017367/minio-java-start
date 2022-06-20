package org.example.minio.start.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.minio.start.config.MinioStartConfig;
import org.example.minio.start.exception.MinioHttpException;
import org.example.minio.start.utils.entity.EncryptEntity;

/**
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022/6/19 13:12
 */
public class ApiUtil {

    /**
     * 获取http请求实例
     * @return
     */
    public static HttpRequest getHttpRequest(Method method, String uri){
        String serveUrl = MinioStartConfig.getServeUrl();
        String appKey = MinioStartConfig.getAppKey();

        EncryptEntity encrypt = AppKeyUtil.encrypt(appKey);

        String url = serveUrl + uri;
        HttpRequest request = HttpUtil.createRequest(method, url);
        request.header("key1", encrypt.getAppKey());
        request.header("key2", encrypt.getStamp());

        return request;
    }

    /**
     * 执行http请求
     * @param httpRequest
     * @return
     */
    public static HttpResponse executeHttpRequest(HttpRequest httpRequest) {
        HttpResponse response = httpRequest.execute();
        int status = response.getStatus();
        if (status == 200) {
            return response;
        } else {
            String errMsg = null;
            if (status == 500) {
                errMsg = String.format("minio请求失败 status:%d,errorValue:%s", status, response.body());
            } else {
                errMsg = String.format("minio请求失败 status:%d", status);
            }
            throw new MinioHttpException(errMsg);
        }
    }

    /**
     * 获取请求结果中的请求数据
     * @param httpResponse
     * @return
     */
    public static JSONObject getResponseResult(HttpResponse httpResponse) {
        String body = httpResponse.body();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        return jsonObject;
    }

}
