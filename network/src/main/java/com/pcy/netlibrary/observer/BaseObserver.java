package com.pcy.netlibrary.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author: pangchaoyuan.
 * @CreateDate: 2020/9/6.
 * @Description: 观察者基类BaseObserver.
 */
public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 失败
     */
    public void onFailure(Throwable e) {
        //失败业务处理
    }

    /**
     * 成功
     */
    public abstract void onSuccess(T t);
}
