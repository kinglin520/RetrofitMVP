package com.example.dream.retrofitrxjavaokhttpdemo.base.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

public interface IBasePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

//    void onViewDestory(Object tag);

    //    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
//    void onAny(LifecycleOwner owner);
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    void onCreate(LifecycleOwner owner);
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    void onStart(LifecycleOwner owner);
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    void onStop(LifecycleOwner owner);
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    void onResume(LifecycleOwner owner);
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    void onPause(LifecycleOwner owner);
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    void onDestory(LifecycleOwner owner);
}
