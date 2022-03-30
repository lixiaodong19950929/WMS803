package com.wms.task.http.ssl;

import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;

/**
 * Created by qinyl on 2018/2/2.
 */
@Configuration(value = "iotSslSocketFactoryConfigure")
public class IotSslSocketFactory {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(IotSslSocketFactory.class);

    private final ResourceLoader resourceloader;

    @Autowired
    public IotSslSocketFactory(ResourceLoader resourceloader) {
        this.resourceloader = resourceloader;
    }

    @Primary
    @Bean(name = "iotSslSocketFactory")
    public SSLSocketFactory getSslSocketFactory() {
        try {logger.info("ssl initialize");
            KeyStore selfCert = KeyStore.getInstance("pkcs12");
            selfCert.load(resourceloader.getResource("classpath:outgoing.CertwithKey.pkcs12").getInputStream(), "IoM@1234".toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("sunx509");
            keyManagerFactory.init(selfCert, "IoM@1234".toCharArray());

            KeyStore caCert = KeyStore.getInstance("jks");
            caCert.load(resourceloader.getResource("classpath:ca.jks").getInputStream(), "Huawei@123".toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("sunx509");
            trustManagerFactory.init(caCert);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext, new DefaultHostnameVerifier());

            return sslSocketFactory;
        } catch (Exception e) {
            logger.error("iotSslSocketFactory: {}", e.getMessage());
            return null;
        }
    }
}
