package org.example.minio.start.api;

import cn.hutool.core.io.resource.FileResource;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.io.resource.UrlResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.example.minio.start.entity.MinioFileEntity;
import org.example.minio.start.entity.MinioShareFileEntity;
import org.example.minio.start.utils.ApiUtil;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022/6/19 12:24
 */
public class MinioFileApi {

    private static String getReqUri(String uri) {
        return "files/" + uri;
    }

    /**
     * 临时文件保存路径
     */
    public static final String INTERIM_SAVE_PATH = "interim";

    /**
     * 文件上传
     * @param resource 文件资源
     * @param savePath 文件存放路径
     * @return 文件id
     */
    public static String uploadFile(Resource resource, String savePath) {
        HttpRequest httpRequest = ApiUtil.getHttpRequest(Method.POST, getReqUri("upload"));

        httpRequest.form("file", resource);
        if (StrUtil.isNotEmpty(savePath)) {
            httpRequest.form("path", savePath);
        }

        HttpResponse httpResponse = ApiUtil.executeHttpRequest(httpRequest);
        JSONObject jsonObject = ApiUtil.getResponseResult(httpResponse);
        String fileId = jsonObject.getStr("data");

        return fileId;
    }

    /**
     * 文件上传
     * @param filePath 本地要上传的文件地址，为空则就是默认地址files
     * @param savePath 要保存到minio的目录地址
     * @return 文件id
     */
    public static String uploadFile(String filePath, String savePath) {
        return uploadFile(new FileResource(filePath), savePath);
    }

    /**
     * 文件上传
     * @param fileUrl 要上传的文件链接地址，为空则就是默认地址files
     * @param filename 要上传的文件名称带后缀
     * @param savePath 要保存到minio的目录地址
     * @return 文件id
     * @throws MalformedURLException
     */
    public static String uploadFile(String fileUrl, String filename, String savePath) throws MalformedURLException {
        HttpRequest httpRequest = ApiUtil.getHttpRequest(Method.POST, getReqUri("uploadUrl"));

        httpRequest.form("path", savePath);
        httpRequest.form("filename", filename);
        httpRequest.form("fileurl", fileUrl);

        HttpResponse httpResponse = ApiUtil.executeHttpRequest(httpRequest);
        JSONObject jsonObject = ApiUtil.getResponseResult(httpResponse);
        String fileId = jsonObject.getStr("data");

        return fileId;
    }

    /**
     * 下载文件
     * @param fileId
     * @return HttpResponse
     */
    private static HttpResponse downloadFile(String fileId) {
        HttpRequest httpRequest = ApiUtil.getHttpRequest(Method.GET, getReqUri("download"));
        httpRequest.form("fileId", fileId);
        HttpResponse httpResponse = ApiUtil.executeHttpRequest(httpRequest);
        return httpResponse;
    }

    /**
     * 下载文件
     * @param fileId 文件id
     * @param targetFilePath 文件要保存的本地地址
     */
    public static void downloadFile(String fileId, String targetFilePath) {
        HttpResponse httpResponse = downloadFile(fileId);

        httpResponse.writeBody(targetFilePath);
    }

    /**
     * 下载文件
     * @param fileId 文件id
     * @param outputStream io流
     */
    public static void downloadFile(String fileId, OutputStream outputStream) {
        HttpResponse httpResponse = downloadFile(fileId);

        httpResponse.writeBody(outputStream, true, null);
    }

    /**
     * word转pdf
     * @param wordPath 本地word文件地址
     * @param pdfPath  转成pdf后要保存pdf本地地址
     */
    public static void wordToPdf(String wordPath, String pdfPath) {
        HttpRequest httpRequest = ApiUtil.getHttpRequest(Method.POST, getReqUri("wordToPdf"));

        httpRequest.form("file", new FileResource(wordPath));

        HttpResponse httpResponse = ApiUtil.executeHttpRequest(httpRequest);

        httpResponse.writeBody(pdfPath);
    }

    /**
     * 删除文件
     * @param fileId 文件id
     */
    public static void delFile(String fileId) {
        HttpRequest httpRequest = ApiUtil.getHttpRequest(Method.DELETE, getReqUri("delFile"));

        httpRequest.form("fileId", fileId);

        ApiUtil.executeHttpRequest(httpRequest);
    }

    /**
     * 获取文件信息列表
     * @param fileIds 文件id串，id通过“,”逗号分割的字符串
     * @return 文件对象信息列表
     */
    public static List<MinioFileEntity> getFileList(String fileIds) {
        HttpRequest httpRequest = ApiUtil.getHttpRequest(Method.GET, getReqUri("getListByIds"));

        httpRequest.form("ids", fileIds);

        HttpResponse httpResponse = ApiUtil.executeHttpRequest(httpRequest);

        JSONObject jsonObject = ApiUtil.getResponseResult(httpResponse);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<MinioFileEntity> entityList = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject fileJsonObject = jsonArray.getJSONObject(i);
            MinioFileEntity minioFileEntity = new MinioFileEntity();
            minioFileEntity.setFileId(fileJsonObject.getStr("id"));
            minioFileEntity.setFileName(fileJsonObject.getStr("fileName"));
            minioFileEntity.setFileSuffix(fileJsonObject.getStr("fileSuffix"));
            entityList.add(minioFileEntity);
        }

        return entityList;
    }

    /**
     * 获取文件信息列表
     * @param fileIdList 文件id列表
     * @return 文件对象信息列表
     */
    public static List<MinioFileEntity> getFileList(Collection<String> fileIdList) {
        String fileIds = fileIdList.stream().collect(Collectors.joining(","));

        return getFileList(fileIds);
    }

    /**
     * 获取文件信息列表
     * @param fileIdArr 文件id数组
     * @return 文件对象信息列表
     */
    public static List<MinioFileEntity> getFileList(String[] fileIdArr) {
        String fileIds = Arrays.stream(fileIdArr).collect(Collectors.joining(","));

        return getFileList(fileIds);
    }

    /**
     * 清除临时文件
     */
    public static void clearInterim() {
        HttpRequest httpRequest = ApiUtil.getHttpRequest(Method.GET, getReqUri("clearInterim"));

        ApiUtil.executeHttpRequest(httpRequest);
    }

    /**
     * 获取文件分享链接
     * 分享获取到的文件链接则会跳过校验，在链接后面加上isPreview=true参数则为文件预览。
     * @param fileId 文件id
     * @param second 要进行分享的秒数-1则为永久
     * @return 分享文件数据
     */
    public static MinioShareFileEntity getShareFile(String fileId, long second) {
        HttpRequest httpRequest = ApiUtil.getHttpRequest(Method.GET, getReqUri("getShareFile"));

        httpRequest.form("fileId", fileId);
        httpRequest.form("second", second);

        HttpResponse httpResponse = ApiUtil.executeHttpRequest(httpRequest);
        JSONObject jsonObject = ApiUtil.getResponseResult(httpResponse);
        JSONObject data = jsonObject.getJSONObject("data");

        MinioShareFileEntity minioShareFileEntity = new MinioShareFileEntity();
        minioShareFileEntity.setFileUrl(data.getStr("fileUrl"));
        minioShareFileEntity.setFileName(data.getStr("fileName"));

        return minioShareFileEntity;
    }

    /**
     * 获取文件分享链接 永久
     * @param fileId 文件id
     * @return 分享文件数据
     */
    public static MinioShareFileEntity getShareFile(String fileId) {
        return getShareFile(fileId, -1);
    }

}
