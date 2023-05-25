package com.pcy.netlibrary;

import android.util.Log;

import com.pcy.netlibrary.interceptor.RequestInterceptor;
import com.pcy.netlibrary.interceptor.ResponseInterceptor;
import com.pcy.netlibrary.interfaces.INetworkAppInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: pangchaoyuan.
 * @CreateDate: 2020/9/3 0003.
 * @Description: 网络层Api.
 */
public class NetApi {
    private static final String TAG = "NetApi";
    private static Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private INetworkAppInfo iNetworkAppInfo;
    private String baseUrl;
    private long defaultTimeout = 60000;

    public static NetApi getInstance() {
        return NetApiHolder.holder;
    }

    private static class NetApiHolder {
        private static NetApi holder = new NetApi();
    }

    private NetApi() {
    }

    /**
     * 初始化
     */
    public void init(INetworkAppInfo appInfo) {
        if (appInfo == null) {
            throw new NullPointerException("iNetworkAppInfo == null，请设置INetworkAppInfo");
        }
        this.iNetworkAppInfo = appInfo;
        this.baseUrl = appInfo.getBaseUrl();
        if (appInfo.getTimeout() != 0) {
            defaultTimeout = appInfo.getTimeout();
        }
        Log.e(TAG, "defaultTimeout:" + defaultTimeout);
        initOkHttpClient();
        initRetrofit();
    }

    /**
     * 初始化 OkHttpClient
     */
    private void initOkHttpClient() {
        Log.e(TAG, "isDebug:" + iNetworkAppInfo.isDebug());
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new RequestInterceptor(iNetworkAppInfo))
                .addInterceptor(new ResponseInterceptor())
                .readTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
                .connectTimeout(defaultTimeout, TimeUnit.MILLISECONDS);
        if (iNetworkAppInfo.isDebug()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        okHttpClient = builder.build();
    }

    /**
     * 初始化 Retrofit
     */
    private void initRetrofit() {
        Log.e(TAG, "baseUrl:" + baseUrl);
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 获取Api服务接口
     */
    public static <T> T getService(Class<T> service) {
        return retrofit.create(service);
    }
}
