package com.pcy.netlibrary.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: pangchaoyuan.
 * @CreateDate: 2020/9/4 0004.
 * @Description: 网络响应拦截器.
 * 可以校验token过期等
 */
public class ResponseInterceptor implements Interceptor {
    private static final String TAG = "ResponseInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        Request request = chain.request();
        // 原始的Response
        Response originalResponse = chain.proceed(request);
        /*----------------------------------------------------------------------------------------*/
        // 可以做token过期操作
        /*----------------------------------------------------------------------------------------*/
        long responseTimeMillis = System.currentTimeMillis();
        Log.e(TAG, "duration=" + (responseTimeMillis - requestTime) + "ms");
        return originalResponse;
    }
}
