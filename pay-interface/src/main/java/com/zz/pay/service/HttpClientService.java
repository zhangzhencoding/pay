package com.zz.pay.service;

import java.util.Map;

public interface HttpClientService {

    String doGet(String url);
    String doGet(String url, Map<String,String> params);
    String doPost(String url);
    String doPost(String url, Map<String,String> params);
    String doPost(String url,String json,String str);
}
