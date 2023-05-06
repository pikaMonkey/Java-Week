package com.pkh.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    // utf-8字符编码
    private static final String CHARSET_UTF_8 = "utf-8";

    // 连接管理器
    private static PoolingHttpClientConnectionManager pool;

    // 请求配置
    private static RequestConfig requestConfig;

    static {
        try {
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(createIgnoreVerifySSL());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(20);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(20);
            // 根据默认超时限制初始化requestConfig
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(Timeout.ofMilliseconds(5000))
                    .setConnectTimeout(Timeout.ofMilliseconds(5000)).build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            logger.error("HttpClientUtil.static error: {}", e.getMessage());
        }
    }
    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("TLSv1.2");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    /**
     * 发送post请求
     *
     * @param url   请求地址
     * @param param 请求参数
     * @param charset   参数字符集
     * @return
     */
    public static String doPost(String url, JSONObject param, String charset) {
        String result = null;
        try {
            ClassicHttpRequest httpPost = ClassicRequestBuilder.post(url).build();
            httpPost.setEntity(new StringEntity(param.toJSONString(), ContentType.APPLICATION_JSON));
            result =  sendHttp(httpPost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 发送get请求
     *
     * @param httpUrl 请求地址
     */
    public static String doGet(String httpUrl, Map<String, String> paramMap, Map<String, String> headMap) throws UnsupportedEncodingException {
        String param = convert2String(paramMap);
        ClassicHttpRequest httpGet = ClassicRequestBuilder.get(httpUrl + (param.length() == 0 ? "" : "?" + param))
                .build();
        for (Iterator<String> it = headMap.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = headMap.get(key);
            httpGet.setHeader(key, value);
        }
        return sendHttp(httpGet);
    }

    private static String sendHttp(ClassicHttpRequest httpRequest) {
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            CloseableHttpClient httpClient = getHttpClient();
            response = httpClient.execute(httpRequest);
            HttpEntity entity = response.getEntity();
            if (response.getCode() != HttpStatus.SC_OK) {
                throw new Exception("HTTP Request is not success, Response code is " + response.getCode() + "Reason phrase is" + response.getReasonPhrase());
            }
            responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
            EntityUtils.consume(entity);

        } catch (Exception e) {
            logger.error("HttpClientUtil sendHttpPost error ", e);
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("HttpClientUtil sendHttpPost close error ", e);
            }
        }
        return responseContent;
    }

    /**
     * 把Map转换为字符串类型
     *
     * @param parameterMap 字符串Map
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String convert2String(Map<String, String> parameterMap) throws UnsupportedEncodingException {
        List<String> paramList = new ArrayList<>();
        if (parameterMap != null) {
            Iterator<String> iterator = parameterMap.keySet().iterator();
            for (;iterator.hasNext();) {
                String key = iterator.next();
                String value;
                if (parameterMap.get(key) != null) {
                    value = parameterMap.get(key);
                } else {
                    value = "";
                }
                paramList.add(URLEncoder.encode(key, CHARSET_UTF_8) + "=" + URLEncoder.encode(value, CHARSET_UTF_8));
            }
        }
        return String.join("&", paramList);
    }

    /**
     * 获取一个客户端对象
     *
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(pool)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryStrategy(new DefaultHttpRequestRetryStrategy())
                .build();

        return httpClient;
    }

}
