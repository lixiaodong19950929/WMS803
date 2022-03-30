package com.wms.task.http;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.LoggerFactory;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.SSLSocketFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class HttpProxy {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpProxy.class);

    public static final HttpProxy INSTANCE = new HttpProxy();
    private static Map<Class<?>, Object> HTTP_PROXIES_MAP = new HashMap<>();
    private static Map<Class<?>, Object> HTTPS_PROXIES_MAP = new HashMap<>();

    /**
     * @param clazz
     * @param host
     * @param <T>
     * @return
     */
    public synchronized <T> Object getApiProxy(Class<T> clazz, String host) {
        if (HTTP_PROXIES_MAP.containsKey(clazz)) {
            return HTTP_PROXIES_MAP.get(clazz);
        }

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor((String s) -> {
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(logInterceptor).build();
        Retrofit retrofitUser = new Retrofit.Builder()
                .baseUrl(host)
                .client(okHttpClient)
                .addConverterFactory(new EmptyBodyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Object apiProxy = retrofitUser.create(clazz);
        HTTP_PROXIES_MAP.put(clazz, apiProxy);
        return apiProxy;
    }

    /**
     * @param clazz
     * @param host
     * @param sslSocketFactory
     * @param <T>
     * @return
     */
    public synchronized <T> Object getApiProxy(
            Class<T> clazz,
            String host,
            SSLSocketFactory sslSocketFactory) {
        if (HTTPS_PROXIES_MAP.containsKey(clazz)) {
            return HTTPS_PROXIES_MAP.get(clazz);
        }

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor((String s) -> {
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .sslSocketFactory(sslSocketFactory, Platform.get().trustManager(sslSocketFactory))
                .hostnameVerifier((String, SSLSession) -> {
                    logger.debug("ssl hostname verifier with host: {}", host);
                    return true;
                }).build();

        Retrofit retrofitUser = new Retrofit.Builder()
                .baseUrl(host)
                .client(okHttpClient)
                .addConverterFactory(new EmptyBodyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Object apiProxy = retrofitUser.create(clazz);
        HTTPS_PROXIES_MAP.put(clazz, apiProxy);
        return apiProxy;
    }

    class EmptyBodyConverterFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return (ResponseBody body) -> {
                if (body.contentLength() == 0) return null;
                return retrofit.nextResponseBodyConverter(this, type, annotations).convert(body);
            };
        }
    }
}
