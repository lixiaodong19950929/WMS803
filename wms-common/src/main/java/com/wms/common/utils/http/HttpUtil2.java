package com.wms.common.utils.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.http.*;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpUtil2 {
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;
    private static HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception,
                                    int executionCount, HttpContext context) {
            return false;
        }};
    static {
        try {
            builder = new SSLContextBuilder();
            // ???????????? ??????????????????
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory())
                    .register(HTTPS, sslsf)
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200);//max connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * httpClient post??????
     * @param url ??????url
     * @param header ????????????
     * @param param ???????????? form????????????
     * @param entity ???????????? json/xml????????????
     * @return ???????????? ????????????
     * @throws Exception
     *
     */
    public static String post(String  url, Map<String, String> header, Map<String, String> param, HttpEntity entity) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // ???????????????
            if (MapUtils.isNotEmpty(header)) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            // ??????????????????
            if (MapUtils.isNotEmpty(param)) {
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    //???????????????
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);
            }
            // ???????????? ????????????
            if (entity != null) {
                httpPost.setEntity(entity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                readHttpResponse(httpResponse);
            }
        } catch (Exception e) {throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }

    public static String get(String  url, Map<String, String> header) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            HttpGet httpGet = new HttpGet(url);
            // ???????????????
            if (MapUtils.isNotEmpty(header)) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                readHttpResponse(httpResponse);
            }
        } catch (Exception e) {throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }

    public static CloseableHttpClient getHttpClient() throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(cm).setRetryHandler(myRetryHandler)
                .setConnectionManagerShared(true)
                .build();
        return httpClient;
    }
    public static String readHttpResponse(HttpResponse httpResponse)
            throws ParseException, IOException {
        StringBuilder builder = new StringBuilder();
        // ????????????????????????
        HttpEntity entity = httpResponse.getEntity();
        // ????????????
        builder.append("status:" + httpResponse.getStatusLine());
        builder.append("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            builder.append("\t" + iterator.next());
        }
        // ??????????????????????????????
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            builder.append("response length:" + responseString.length());
            builder.append("response content:" + responseString.replace("\r\n", ""));
        }
        return builder.toString();
    }


    public static String httpPost(String  url, Map<String, String> header, JSONObject json, HttpEntity entity) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // ???????????????
            if (MapUtils.isNotEmpty(header)) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            // ??????????????????
            if (json != null) {
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//                Iterator iterator = json.keys();
//               while (iterator.hasNext()){
//                    //???????????????
//                   String key =  (String)iterator.next();
//                   String value = json.getString(key);
//                   formparams.add(new BasicNameValuePair(key,value));
//                }

                for(Map.Entry<String, Object> entry : json.entrySet()) {
                    //???????????????
                   formparams.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
                }
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);
            }
            // ???????????? ????????????
            if (entity != null) {
                httpPost.setEntity(entity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                readHttpResponse(httpResponse);
            }
        } catch (Exception e) {throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }


    /**
     * ????????? URL ??????POST???????????????
     * @param url ??????????????? URL
     * @param jsonObject ?????????????????????
     * @return ???????????????????????????
     */
    public static String sendPost(String url, JSONObject jsonObject) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // ??????POST??????????????????????????????
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST??????
            conn.setRequestMethod("POST");
            // ???????????????????????????
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("configapi", "eyJpZm1zLWd4LXN5c21nbXQiOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgxNzkiLCJkIjpmYWxzZX0sImlmbXMtZ3gtd29ya2Zsb3ciOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgxODAiLCJkIjpmYWxzZX0sImlmbXMtZ3gtZGF0YWh1YiI6eyJ1IjoiaHR0cDovLzE5Mi4xNjguMS4xMDI6ODE4MSIsImQiOmZhbHNlfSwiaWZtcy1neC1tZG0iOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgxODIiLCJkIjpmYWxzZX0sImlmbXMtZ3gtcGxhbiI6eyJ1IjoiaHR0cDovLzE5Mi4xNjguMS4xMDI6ODE4MyIsImQiOmZhbHNlfSwiaWZtcy1neC1wbWMiOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgxODQiLCJkIjpmYWxzZX0sImlmbXMtZ3gtY20iOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgxODUiLCJkIjpmYWxzZX0sImlmbXMtZ3gtaHIiOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgxODYiLCJkIjpmYWxzZX0sImlmbXMtZ3gtcW0iOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgxODciLCJkIjpmYWxzZX0sImlmbXMtZ3gtd2giOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgxODgiLCJkIjpmYWxzZX0sImlmbXMtZ3gtZGV2Ijp7InUiOiJodHRwOi8vMTkyLjE2OC4xLjEwMjo4MTkwIiwiZCI6ZmFsc2V9LCJpZm1zLWd4LWNvbyI6eyJ1IjoiaHR0cDovLzE5Mi4xNjguMS4xMDI6ODE5MSIsImQiOmZhbHNlfSwiaWZtcy1neC1zdWNvbyI6eyJ1IjoiaHR0cDovLzE5Mi4xNjguMS4xMDI6ODE5MiIsImQiOmZhbHNlfSwiaWZtcy1neC1kb2MiOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgxOTMiLCJkIjpmYWxzZX0sImlmbXMtZ3gtdWltb2RlbGVyIjp7InUiOiJodHRwOi8vMTkyLjE2OC4xLjEwMjo4MTU0IiwiZCI6dHJ1ZX0sImlmbXMtZ3gtYnVzaW5lc3MiOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgxOTgiLCJkIjpmYWxzZX0sImlmbXMtZ3gtdGFzayI6eyJ1IjoiaHR0cDovLzE5Mi4xNjguMS4xMDI6ODIwMSIsImQiOmZhbHNlfSwiaWZtcy1neC10ZW4iOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgyMDAiLCJkIjpmYWxzZX0sImlmbXMtZ3gtZW52aXJvbm1lbnQiOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgyMDIiLCJkIjpmYWxzZX0sImlmbXMtZ3gtZGF0YWludGVyZmFjZSI6eyJ1IjoiaHR0cDovLzE5Mi4xNjguMS4xMDI6ODE5NSIsImQiOmZhbHNlfSwiaWZtcy1neC12ZWgiOnsidSI6Imh0dHA6Ly8xOTIuMTY4LjEuMTAyOjgyMDMiLCJkIjpmYWxzZX0sImlmbXMtZ3gtbW9uaXRvciI6eyJ1IjoiaHR0cDovLzE5Mi4xNjguMS4xMDI6ODIwNCIsImQiOmZhbHNlfX0=");
            conn.connect();
            // ??????URLConnection????????????????????????
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // ??????????????????
            /*if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (param.length() > 0) {
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append(":");
                    param.append(entry.getValue());
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
                System.out.println("param:" + param.toString());
                out.write(param.toString());
            }*/
            out.write(jsonObject.toString());
            // flush??????????????????
            out.flush();
            // ??????BufferedReader??????????????????URL?????????
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //??????finally?????????????????????????????????
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();

    }

    public static void main(String [] args) throws Exception {
        HashMap<String,String> param = new HashMap();
        param.put("user","12");
        JSONObject json = new JSONObject();
        json.put("user","13");
       String res =  HttpUtil2.sendPost("http://localhost/wx/look",json);
       System.out.println(res);
    }
}
