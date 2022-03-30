package com.wms.task.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLSocketFactory;

/**
 * 南京远御网络科技有限公司
 * 功能：
 * 创建日期：2019/6/24-16:08
 * 版本   开发者     日期      描述
 * 1.0             2019/6/24
 */
@Component
public class RemoteUtils {
    private final SSLSocketFactory sslSocketFactory;

    @Autowired
    public RemoteUtils(
            @Qualifier("trustAllSocketFactory") SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    @SuppressWarnings(value = "all")
    public <T> T httpProxy(String host, Class<T> clazz) {
        return (T) (HttpProxy.INSTANCE.getApiProxy(clazz, host));
    }

    @SuppressWarnings(value = "all")
    public <T> T sslProxy(String host, Class<T> clazz) {
        return (T) (HttpProxy.INSTANCE.getApiProxy(clazz, host, sslSocketFactory));
    }
}
