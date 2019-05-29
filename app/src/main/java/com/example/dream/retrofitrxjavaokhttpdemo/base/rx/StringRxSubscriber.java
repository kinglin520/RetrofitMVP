package com.example.dream.retrofitrxjavaokhttpdemo.base.rx;

import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ApiException;
import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ErrorCode;
import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ErrorType;
import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ExceptionConverter;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 订阅封装(处理String)
 *
 * @author wenlin
 * @date 2019/4/25
 */
public abstract class StringRxSubscriber implements Observer<ResponseBody> {


    /**
     * Http 返回码401监听，返回401需要退出登录
     */
    private static GlobalErrorListener mGlobalErrorListener;

    private Disposable mDisposable;
    /**
     * onNext方法的data，成功回调
     */
    private String mOnNextData;

    /**
     * 进行订阅请求网络
     *
     * @param observable
     */
    public void doSubscribe(Observable observable) {
        observable.subscribeOn(Schedulers.io()).
                unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    @Override
                    public ObservableSource apply(Throwable throwable) {
                        return Observable.error(ExceptionConverter.convertException(throwable));
                    }
                }).
                subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(ResponseBody o) {
        try {
            mOnNextData = o.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        doDispose();

        if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            //401重新登录
            if (exception.getCode() == ErrorCode.CODE_UNAUTHORIZED) {

                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener.onReturn401Code(this, exception.getMessage());
                }

//                _onError(exception.getErrorType(), exception.getCode(), exception.getMessage());
            } else {  //正常错误回调

                //向错误回调传递data字段数据
//                _onError(exception.getErrorType(), exception.getCode(), exception.getMessage());
            }

        } else {
//            _onError(ErrorType.ERROR_UNKNOWN, ErrorCode.CODE_UNKNOWN, "未知错误");
        }
    }

    @Override
    public void onComplete() {
        doDispose();

        _onSuccess(mOnNextData);

    }

    /**
     * 解除订阅关系
     */
    private void doDispose() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    /******************************************关于重新登录的逻辑模块**********************************************/
    public interface GlobalErrorListener {

        void onReturn401Code(StringRxSubscriber rxSubscriber, String message);
    }

    public static void registerGlobalErrorListener(GlobalErrorListener listener) {
        mGlobalErrorListener = listener;
    }

    protected abstract void _onSuccess(String result);


    protected abstract void _onError(ErrorType errorType, String errorCode, String message);

}
