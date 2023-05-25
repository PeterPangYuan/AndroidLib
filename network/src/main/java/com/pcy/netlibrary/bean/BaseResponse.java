package com.pcy.netlibrary.bean;

import com.google.gson.annotations.*;

/**
 * @Author: pangchaoyuan.
 * @CreateDate: 2020/9/3 0003.
 * @Description: BaseResponse，根据台接口返回数据进行抽象.
 * (key不变的基础数据)
 */
public class BaseResponse<T> {
    @SerializedName("code")
    @Expose
    public int code;

    @SerializedName("msg")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public T data;
}
