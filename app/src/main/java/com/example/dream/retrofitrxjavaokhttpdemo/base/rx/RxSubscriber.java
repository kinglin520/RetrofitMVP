package com.example.dream.retrofitrxjavaokhttpdemo.base.rx;


import android.text.TextUtils;

import com.example.dream.retrofitrxjavaokhttpdemo.base.ui.BaseActivity;
import com.example.dream.retrofitrxjavaokhttpdemo.base.ui.BaseBean;
import com.example.dream.retrofitrxjavaokhttpdemo.base.ui.BaseFragment;
import com.example.dream.retrofitrxjavaokhttpdemo.http.config.RequestConfig;
import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ApiException;
import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ErrorCode;
import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ErrorType;
import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ExceptionConverter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * des:订阅封装
 */

public abstract class RxSubscriber<R, T extends BaseBean<R>> implements Observer<R> {

    /**
     * Http 返回码401监听，返回401需要退出登录
     */
    private static GlobalErrorListener mGlobalErrorListener;

//    public BaseActivity mActivity;
//    public BaseFragment mFragment;
    private RequestConfig<R, T> mRequestConfig;
    private Disposable mDisposable;
    /**
     * onNext方法的data，成功回调
     */
    private R mOnNextData;
    /**
     * 服务器错误码的data，向_onError()方法传递
     */
    private R mErrorData;
    /**
     * JavaBean的Message字段的信息
     */
    private String mSuccessMessage;

    public void setRequestConfig(RequestConfig<R, T> requestConfig) {
        this.mRequestConfig = requestConfig;
//        mActivity = requestConfig.getPresenter().getActivity();
//        mFragment = requestConfig.getPresenter().getFragment();
    }

    /*************************************************************************************************************************/

    /**
     * 进行订阅请求网络
     *
     * @param observable
     */
    public void doSubscribe(Observable<T> observable) {
        observable.flatMap(new Function<T, ObservableSource<R>>() {
                    @Override
                    public ObservableSource<R> apply(T t) throws Exception {

                        mSuccessMessage = t.getMessage();
                        mErrorData = t.getData();
                        if (t.getStatus() == ErrorCode.CODE_SERVER_SUCCESS) {
                            return Observable.just(t.getData());
                            //成功直接返回数据

                        }
                        //返回非1000的错误码
                        else if (mRequestConfig != null &&
                                !TextUtils.isEmpty(mRequestConfig.getAsSuccessCondition())
                                && mRequestConfig.getAsSuccessCondition().contains(t.getStatus() + "")) {
                            //成功直接返回数据
                            return Observable.just(t.getData());

                        } else {
                            Throwable mThrowable = new Throwable("接口返回了错误业务码-----" + t.getStatus());

                            throw new ApiException(t.getStatus(), ErrorType.ERROR_API, t.getMessage(), mThrowable);
                        }
                    }
                }).
                subscribeOn(Schedulers.io()).
                unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                onErrorResumeNext(new Function<Throwable, ObservableSource<? extends R>>() {
                    @Override
                    public ObservableSource<? extends R> apply(Throwable throwable) throws Exception {
                        return Observable.error(ExceptionConverter.convertException(throwable));
                    }
                }).
                subscribe(this);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {


        mDisposable = d;
    }

    @Override
    public void onNext(R r) {

        mOnNextData = r;
    }

    @Override
    public void onError(Throwable e) {

        e.printStackTrace();

        doDispose();

        if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            //401重新登录
            if (exception.getCode() == ErrorCode.CODE_UNAUTHORIZED) {
//                LogUtils.e("LogOut---CODE_UNAUTHORIZED");

                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener.onReturn401Code(this, exception.getMessage());
//                    LogUtils.d("401错误----" + exception.getMessage());
                }

                _onError(exception.getErrorType(), exception.getCode(), exception.getMessage(), mErrorData);
            } else if (exception.getCode() == ErrorCode.CODE_SERVER_9105) {
//                LogUtils.e("LogOut---CODE_SERVER_9105");

                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener.onReturn9105Code(this, exception.getMessage());

//                    LogUtils.d("9105错误----" + exception.getMessage());
                }
                _onError(exception.getErrorType(), exception.getCode(), exception.getMessage(), mErrorData);
            } else if (exception.getCode() == ErrorCode.CODE_SERVER_9107) {
//                LogUtils.e("LogOut---CODE_SERVER_9107");

                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener.onReturn9107Code(this, exception.getMessage());

//                    LogUtils.d("9107错误----" + exception.getMessage());
                }
                _onError(exception.getErrorType(), exception.getCode(), "9107", mErrorData);
            } else if (exception.getCode() == ErrorCode.CODE_SERVER_9108) {
//                LogUtils.e("LogOut---CODE_SERVER_9108");

                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener.onReturn9108Code(this, exception.getMessage());

//                    LogUtils.d("9108错误----" + exception.getMessage());
                }
                _onError(exception.getErrorType(), exception.getCode(), "9108", mErrorData);
            } else if (exception.getCode() == ErrorCode.CODE_SERVER_9109) {
//                LogUtils.e("LogOut---CODE_SERVER_9109");

                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener.onReturn9109Code(this, exception.getMessage());

//                    LogUtils.d("9108错误----" + exception.getMessage());
                }
                _onError(exception.getErrorType(), exception.getCode(), exception.getMessage(), mErrorData);
            } else {  //正常错误回调

                //向错误回调传递data字段数据
                _onError(exception.getErrorType(), exception.getCode(), exception.getMessage(), mErrorData);
            }

        } else {
            _onError(ErrorType.ERROR_UNKNOWN, ErrorCode.CODE_UNKNOWN, "未知错误", mErrorData);
        }
    }

    @Override
    public void onComplete() {

        doDispose();

        _onSuccess(mOnNextData, mSuccessMessage);

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
    //401返回码监听listener,需要在Application的onCreate()方法中注册
    public interface GlobalErrorListener {

        void onReturn401Code(RxSubscriber rxSubscriber, String message);

        void onReturn9105Code(RxSubscriber rxSubscriber, String message);

        void onReturn9107Code(RxSubscriber rxSubscriber, String message);

        void onReturn9108Code(RxSubscriber rxSubscriber, String message);

        void onReturn9109Code(RxSubscriber rxSubscriber, String message);
    }

    public static void registerGlobalErrorListener(GlobalErrorListener listener) {
        mGlobalErrorListener = listener;
    }
//
//    public BaseActivity getActivity() {
//        return mActivity;
//    }
//
//
//    public void setmActivity(BaseActivity mActivity) {
//        this.mActivity = mActivity;
//    }
//
//    public BaseFragment getFragment() {
//        return mFragment;
//    }

    /**************************************************************************************************************/

    public Disposable getDisposable() {
        return mDisposable;
    }

    protected abstract void _onSuccess(R r, String successMessage);


    protected abstract void _onError(ErrorType errorType, int errorCode, String message, R data);

}
