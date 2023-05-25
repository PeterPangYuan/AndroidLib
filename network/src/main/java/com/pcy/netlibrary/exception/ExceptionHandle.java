package com.pcy.netlibrary.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.annotations.Nullable;
import retrofit2.HttpException;

/**
 * @Author: pangchaoyuan.
 * @CreateDate: 2020/9/6.
 * @Description: 异常错误处理.
 */
public class ExceptionHandle {
    /**
     * 未经过认证的错误
     */
    private static final int UNAUTHORIZED = 401;
    /**
     * 禁止访问错误
     */
    private static final int FORBIDDEN = 403;
    /**
     * 没有找到错误
     */
    private static final int NOT_FOUND = 404;
    /**
     * 请求超时
     */
    private static final int REQUEST_TIMEOUT = 408;
    /**
     * 内部服务器错误
     */
    private static final int INTERNAL_SERVER_ERROR = 500;
    /**
     * 错误网关；无效网关
     */
    private static final int BAD_GATEWAY = 502;
    /**
     * 服务不可用；找不到服务器
     */
    private static final int SERVICE_UNAVAILABLE = 503;
    /**
     * 网关超时
     */
    private static final int GATEWAY_TIMEOUT = 504;

/*---------------------------------自定义错误--------------------------------------------start------*/
    /**
     * 未知错误
     */
    private static final int UNKNOW = 1000;
    /**
     * 解析错误
     */
    private static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    private static final int NETWORK_ERROR = 1002;
    /**
     * 协议错误
     */
    private static final int HTTP_ERROR = 1003;
    /**
     * 证书错误
     */
    private static final int SSL_ERROR = 1005;
    /**
     * 连接超时
     */
    private static final int TIMEOUT_ERROR = 1006;
/*---------------------------------自定义错误----------------------------------------------end------*/

    public static ResponseException handlerException(Throwable e) {
        ResponseException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseException(e, HTTP_ERROR);
            ex.message = httpException.message();
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    ex.message = "未经过认证的错误";
                    break;
                case FORBIDDEN:
                    ex.message = "禁止访问错误";
                    break;
                case NOT_FOUND:
                    ex.message = "没有找到错误";
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = "请求超时";
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.message = "内部服务器错误";
                    break;
                case BAD_GATEWAY:
                    ex.message = "无效网关";
                    break;
                case SERVICE_UNAVAILABLE:
                    ex.message = "服务不可用";
                    break;
                case GATEWAY_TIMEOUT:
                    ex.message = "网关超时";
                    break;
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseException(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseException(e, PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseException(e, NETWORK_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof SSLHandshakeException) {
            ex = new ResponseException(e, SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponseException(e, TIMEOUT_ERROR);
            ex.message = "服务器请求超时";
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ResponseException(e, TIMEOUT_ERROR);
            ex.message = "服务器响应超时";
            return ex;
        } else {
            ex = new ResponseException(e, UNKNOW);
            ex.message = "未知错误";
            return ex;
        }
    }

    public static class ResponseException extends Exception {
        public int code;
        public String message;

        public ResponseException(Throwable throwable, int code) {
            super();
            this.code = code;
        }

        @Nullable
        @Override
        public String getMessage() {
            return message;
        }
    }

    /**
     * 自定义运行时异常
     */
    public static class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}
