package com.pcy.netlibrary.interfaces;

/**
 * @Author: pangchaoyuan.
 * @CreateDate: 2020/9/3 0003.
 * @Description: 网络层与app层通信接口，传递网络层所必需的App的相关信息 .
 */
public interface INetworkAppInfo {
    /**
     * 是否debug模式
     *
     * @return true:是debug模式；false:非debug模式
     */
    boolean isDebug();

    /**
     * App版本名称
     *
     * @return App版本名称
     */
    String getAppVersionName();

    /**
     * BaseUrl
     *
     * @return BaseUrl
     */
    String getBaseUrl();

    /**
     * 超时时间
     *
     * @return 超时时间，单位毫秒
     */
    long getTimeout();
}
