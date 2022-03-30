package com.wms.task.http.ssl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
public class SslConnectionProvider {
    @Bean(name = "trustAllSocketFactory")
    private static SSLSocketFactory trustAllHosts() {
        SSLSocketFactory sslSocketFactory = null;
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }

                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }
                }
        };
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init((null), trustAllCerts, new SecureRandom());
            sslSocketFactory = sc.getSocketFactory();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }
}