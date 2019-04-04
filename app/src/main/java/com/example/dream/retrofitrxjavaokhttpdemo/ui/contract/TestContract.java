package com.example.dream.retrofitrxjavaokhttpdemo.ui.contract;

import com.example.dream.retrofitrxjavaokhttpdemo.base.ui.IBaseView;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.GirlInfoBean;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.UserInfoBean;

import java.util.List;

public interface TestContract extends IBaseView {
    void setUserInfo(UserInfoBean userInfo);
    void setGirlInfo(List<GirlInfoBean> list);
}
