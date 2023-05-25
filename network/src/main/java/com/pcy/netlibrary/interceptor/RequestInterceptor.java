package com.pcy.netlibrary.interceptor;

import com.pcy.netlibrary.interfaces.INetworkAppInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: pangchaoyuan.
 * @CreateDate: 2020/9/4 0004.
 * @Description: 网络请求拦截器.
 * 可以添加header、公共参数
 */
public class RequestInterceptor implements Interceptor {
    private INetworkAppInfo iNetworkAppInfo;

    public RequestInterceptor(INetworkAppInfo iNetworkAppInfo) {
        this.iNetworkAppInfo = iNetworkAppInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

//        HttpUrl originalUrl = originalRequest.url();
//        Log.e("RequestInterceptor", "originalUrl:" + originalUrl);
//        HttpUrl httpUrl = originalUrl.newBuilder()
//                .addQueryParameter("access_token", "%(wafrmR&vT9brVKSVl7")
//                .build();
//        Log.e("RequestInterceptor", "httpUrl:" + httpUrl);

        Request request = originalRequest.newBuilder()
                .addHeader("clientHeaderName", "android")
                .addHeader("clientHeaderVersion", iNetworkAppInfo.getAppVersionName())
                //.addHeader("access_token", "%(wafrmR&vT9brVKSVl7")
                //.addHeader("sign", "ca1fe3f8bb1df66d3ce28f47878dc6cb")
                .build();

        return chain.proceed(request);
    }
}
