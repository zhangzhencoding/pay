package com.zz.pay.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.zz.pay.service.HttpClientService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class HttpClientServiceImpl implements HttpClientService {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientServiceImpl.class);
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;
    @Autowired
    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    @Override
    public String doGet(String url) {
        if (StringUtils.isBlank(url)){
            logger.error("request url is empty");
            return null;
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("response close() error info {}", e.getMessage());
                }
            }
            logger.info("poolstas {}", poolingHttpClientConnectionManager.getTotalStats());
        }
        return null;
    }

    @Override
    public String doGet(String url, Map<String, String> params) {
        try {
            if (StringUtils.isBlank(url)){
                logger.error("request url is empty");
                return null;
            }
            URIBuilder uriBuilder = new URIBuilder(url);
            Set<String> keys = params.keySet();
            for (String key :
                    keys) {
                uriBuilder.addParameter(key, params.get(key));
            }
            this.doGet(uriBuilder.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String doPost(String url) {
        if (StringUtils.isBlank(url)){
            logger.error("request url is empty");
            return null;
        }
        return this.doPost(url, null);
    }

    @Override
    public String doPost(String url, Map<String, String> params) {
        if (StringUtils.isBlank(url)){
            logger.error("request url is empty");
            return null;
        }
        //post请求
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> parameters = null;
        if (null != params) {
            parameters = new ArrayList<>();
            Set<String> keys = params.keySet();
            for (String key : keys ) {
                parameters.add( new BasicNameValuePair(key, params.get(key)));
            }
        }
        CloseableHttpResponse response = null;
        //表单实体
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
            httpPost.setConfig(this.requestConfig);
            //表单设置到请求中
            httpPost.setEntity(formEntity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(),"UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("response close() error info {}", e.getMessage());
                }
            }
            logger.info("poolstas {}", poolingHttpClientConnectionManager.getTotalStats());
        }
        return null;
    }

    @Override
    public String doPost(String url, String json, String str) {
        if (StringUtils.isBlank(url)){
            logger.error("request url is empty");
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        if (json != null){
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
        }
        httpPost.setConfig(this.requestConfig);
        try {
            response = httpClient.execute(httpPost);
            logger.info("response {}", JSONObject.toJSONString(response));
            if(response.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(response.getEntity(),"UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("response close() error info {}", e.getMessage());
                }
            }
            logger.info("poolstas {}", poolingHttpClientConnectionManager.getTotalStats());
        }
        return null;
    }
}
