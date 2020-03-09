package com.noc.lib_network.okhttp;

import com.noc.lib_network.okhttp.cookie.SimpleCookieJar;
import com.noc.lib_network.okhttp.https.HttpsUtils;
import com.noc.lib_network.okhttp.listener.DisposeDataHandle;
import com.noc.lib_network.okhttp.listener.DisposeDataListener;
import com.noc.lib_network.okhttp.request.CommonRequest;
import com.noc.lib_network.okhttp.request.RequestParams;
import com.noc.lib_network.okhttp.response.CommonFileCallback;
import com.noc.lib_network.okhttp.response.CommonJsonCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用来发送get, post请求的工具类，包括设置一些请求的共用参数
 */
public class CommonOkHttpClient {

    // 超时时间
    private static final int TIME_OUT = 30;
    // 单例
    private static OkHttpClient mOkHttpClient;

    // 完成对 OkHttpClient 的初始化
    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        // 对所有域名信任
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        // 为所有请求添加公共请求头，看项目需求
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request =
                        chain.request().newBuilder()
                                .addHeader("User-Agent", "Noc-Mobile") // 标明发送本次请求的客户端
                                .build();
                return chain.proceed(request);
            }
        });
        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        // 允许重定向
        okHttpClientBuilder.followRedirects(true);
        // trust all the https point
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(),
                HttpsUtils.initTrustManager());
        mOkHttpClient = okHttpClientBuilder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 通过构造好的Request,Callback去发送get请求
     */
    public static Call get(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    /**
     * 通过构造好的Request,Callback去发送post请求
     */
    public static Call post(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    /**
     * 通过构造好的Request,Callback去发送文件下载请求
     */
    public static Call downloadFile(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(handle));
        return call;
    }

}