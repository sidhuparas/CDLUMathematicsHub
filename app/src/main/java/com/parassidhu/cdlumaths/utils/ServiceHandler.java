package com.parassidhu.cdlumaths.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ServiceHandler {
    public static final int GET = 1;
    public static final int POST = 2;
    static String response;

    static {
        response = null;
    }

    public String makeServiceCall(String url, int method) {
        return makeServiceCall(url, method, null);
    }

    public String makeServiceCall(String url, int method, List<NameValuePair> params) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = null;
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
                httpResponse = httpClient.execute(httpPost);
            } else if (method == GET) {
                if (params != null) {
                    url = url + "?" + URLEncodedUtils.format(params, "utf-8");
                }
                httpResponse = httpClient.execute(new HttpGet(url));
            }
            response = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
        }
        return response;
    }
}
