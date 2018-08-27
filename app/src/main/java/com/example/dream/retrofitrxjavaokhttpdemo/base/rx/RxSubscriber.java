package com.example.dream.retrofitrxjavaokhttpdemo.base.rx;


import android.text.TextUtils;

import com.example.dream.retrofitrxjavaokhttpdemo.base.BaseActivity;
import com.example.dream.retrofitrxjavaokhttpdemo.base.BaseBean;
import com.example.dream.retrofitrxjavaokhttpdemo.base.BaseFragment;
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

    //Http 返回码401监听，返回401需要退出登录
    private static GlobalErrorListener mGlobalErrorListener;

    public BaseActivity mActivity;
    public BaseFragment mFragment;
    //    private CustomDialog mCustomDialog; //用于在dialog之上显示loadingvie
    private RequestConfig<R, T> mRequestConfig;
    private Disposable mDisposable;
    private R mOnNextData;  //onNext方法的data，成功回调
    private R mErrorData;   //服务器错误码的data，向_onError()方法传递

    private String mSuccessMessage;  //JavaBean的Message字段的信息

    public void setRequestConfig(RequestConfig<R, T> requestConfig) {
        this.mRequestConfig = requestConfig;
        mActivity = requestConfig.getPresenter().getActivity();
        mFragment = requestConfig.getPresenter().getFragment();
//        mCustomDialog = requestConfig.getTargetDialog();
    }

    /*************************************************************************************************************************/

    /**
     * 进行订阅请求网络
     *
     * @param observable
     */
    public void doSubscribe(Observable<T> observable) {
        observable.
                flatMap(new Function<T, ObservableSource<R>>() {
                    @Override
                    public ObservableSource<R> apply(T t) throws Exception {

                        mSuccessMessage = t.getMessage();
                        mErrorData = t.getData();
                        if (t.getStatus() == ErrorCode.CODE_SERVER_SUCCESS) {  //接口返回1000成功码

//                            if (mRequestConfig != null && !TextUtils.isEmpty(mRequestConfig.getTag())) {
//                                LogUtils.d(mRequestConfig.getTag(), "-----JavaBean的Code为" + ErrorCode.CODE_SERVER_SUCCESS);
//                            }

                            return Observable.just(t.getData());
                            //成功直接返回数据

                        } else if (mRequestConfig != null && !TextUtils.isEmpty(mRequestConfig.getAsSuccessCondition()) && mRequestConfig.getAsSuccessCondition().contains(t.getStatus() + "")) {  //返回非1000的错误码

//                            if (mRequestConfig != null && !TextUtils.isEmpty(mRequestConfig.getTag())) {
//                                LogUtils.d(mRequestConfig.getTag(), "-----JavaBean的Code为" + t.getCode());
//                            }

                            return Observable.just(t.getData());  //成功直接返回数据

                        } else {
//                            if (mRequestConfig != null && !TextUtils.isEmpty(mRequestConfig.getTag())) {
//                                LogUtils.d(mRequestConfig.getTag(), "-----JavaBean的Code为" + t.getCode());
//                            }

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

//        if (mRequestConfig != null && !TextUtils.isEmpty(mRequestConfig.getTag())) {
//            LogUtils.d(mRequestConfig.getTag(), "-----onSubscribe()");
//        }

        mDisposable = d;
//        showLoadingViewIfNecessary();
    }

    @Override
    public void onNext(R r) {

//        if (mRequestConfig != null && !TextUtils.isEmpty(mRequestConfig.getTag())) {
//            LogUtils.d(mRequestConfig.getTag(), "-----onNext()");
//        }

        mOnNextData = r;
    }

    @Override
    public void onError(Throwable e) {

//        LogUtils.e("LogOut---onError");
//        if (mRequestConfig != null && !TextUtils.isEmpty(mRequestConfig.getTag())) {
//            LogUtils.d(mRequestConfig.getTag(), "-----onError()");
//        }

        e.printStackTrace();

//        dismissLoadingViewIfNecessary(true);

        doDispose();

        if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;

            if (exception.getCode() == ErrorCode.CODE_UNAUTHORIZED) {  //401重新登录
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
            _onError(ErrorType.ERROR_UNKNOWN, ErrorCode.CODE_UNKNOWN, "未知错误", null);
        }
    }

    @Override
    public void onComplete() {

//        if (mRequestConfig != null && !TextUtils.isEmpty(mRequestConfig.getTag())) {
//            LogUtils.d(mRequestConfig.getTag(), "-----onComplete()");
//        }

        doDispose();

//        dismissLoadingViewIfNecessary(false);

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


//    /**
//     * 显示加载进度指示符
//     */
//    private void showLoadingViewIfNecessary() {
//        if (mRequestConfig != null && mRequestConfig.isShowLoading()) {
//            switch (mRequestConfig.getRequestMode()) {
//                case SINGLE:
//                    //单网络请求显示加载进度View
//                    operateLoadingViewVisibility(true);  //单网络请求显示加载进度View
//                    break;
//
//                case CHAIN:
//
//                    switch (mRequestConfig.getChainPosition()) {
//                        case CHAIN_START:
//                            operateLoadingViewVisibility(true);
//                            //链式请求的起始请求显示加载进度
//                            break;
//
//                        case CHAIN_MIDDLE:
//                            //链式请求的中间请求不进行加载进度显示操作
//                            break;
//
//                        case CHAIN_END:
//                            //链式请求的结束请求不进行加载进度显示操作
//                            break;
//
//                        default:
//                            break;
//                    }
//            }
//        }
//    }
//
//
//    private void dismissLoadingViewIfNecessary(boolean isError) {
//        if (mRequestConfig != null && mRequestConfig.isShowLoading()) {
//            switch (mRequestConfig.getRequestMode()) {
//                case SINGLE:
//                    operateLoadingViewVisibility(false);
//                    break;
//
//                case CHAIN:
//
//                    switch (mRequestConfig.getChainPosition()) {
//                        case CHAIN_START:
//                            if (isError) {  //链式调用发生错误直接隐藏加载进度指示符
//                                operateLoadingViewVisibility(false);
//                            }
//                            break;
//
//                        case CHAIN_MIDDLE:
//                            if (isError) {  //链式调用发生错误直接隐藏加载进度指示符
//                                operateLoadingViewVisibility(false);
//                            }
//                            break;
//
//                        case CHAIN_END:
//                            //链式调用最后请求必须隐藏加载进度指示符
//                            operateLoadingViewVisibility(false);
//
//                            break;
//                        default:
//
//                    }
//            }
//        }
//    }
//
//    /**
//     * 操作加载进度View的显示和隐藏
//     *
//     * @param isShow true:显示   false：隐藏
//     */
//    private void operateLoadingViewVisibility(boolean isShow) {
//        if (isShow) {
//            if (mCustomDialog != null) {
//                mCustomDialog.showLoadingView();
//                return;
//            }
//
//            if (mActivity != null) {
//                mActivity.showLoadingView();
//            } else {
//                mFragment.showLoadingView();
//            }
//        } else {
//
//            if (mCustomDialog != null) {
//                mCustomDialog.hideLoadingView();
//                return;
//            }
//
//            if (mActivity != null) {
//                mActivity.dismissLoadingView();
//            } else {
//                mFragment.dismissLoadingView();
//            }
//        }
//
//    }

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

    public BaseActivity getActivity() {
        return mActivity;
    }


    public void setmActivity(BaseActivity mActivity) {
        this.mActivity = mActivity;
    }

    public BaseFragment getFragment() {
        return mFragment;
    }

    /**************************************************************************************************************/

    public Disposable getDisposable() {
        return mDisposable;
    }

    protected abstract void _onSuccess(R r, String successMessage);


    protected abstract void _onError(ErrorType errorType, int errorCode, String message, R data);

}
