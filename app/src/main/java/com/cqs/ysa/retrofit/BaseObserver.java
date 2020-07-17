package com.cqs.ysa.retrofit;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 针对普通对象
 * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T data) {
        onSuccess(data);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(exceptionHandler(e));
    }

    @Override
    public void onComplete(){}

    public abstract void onSuccess(T data);

    public abstract void onFailure(String error);

    /**
     * 异常处理
     * @param e
     * @return 返回错误信息
     */
    public String exceptionHandler(Throwable e){
        String errorMsg = "网络请求异常";
        if (e instanceof UnknownHostException) {
            errorMsg = "网络不可用";
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "请求网络超时";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorMsg = convertStatusCode(httpException);
        } else if (e instanceof ParseException || e instanceof JSONException || e instanceof JSONException) {
            errorMsg =  "数据解析错误";
        }
        return errorMsg;
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() >= 500 && httpException.code() < 600) {
            msg =  "服务器处理请求出错";
        } else if (httpException.code() >= 400 && httpException.code() < 500) {
            msg =  "服务器无法处理请求";
        } else if (httpException.code() >= 300 && httpException.code() < 400) {
            msg =  "请求被重定向";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
