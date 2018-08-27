package com.example.dream.retrofitrxjavaokhttpdemo.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dream.retrofitrxjavaokhttpdemo.R;
import com.example.dream.retrofitrxjavaokhttpdemo.base.BaseActivity;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.GirlInfoBean;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.UserInfoBean;
import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ErrorType;
import com.example.dream.retrofitrxjavaokhttpdemo.ui.contract.TestContract;
import com.example.dream.retrofitrxjavaokhttpdemo.ui.presenter.TestPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<TestPresenter> implements TestContract {


    @BindView(R.id.btn_desc)
    Button btnDesc;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_girl)
    ImageView ivGirl;
    private int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void setUserInfo(UserInfoBean userInfo) {
        Toast.makeText(this, userInfo.getLevelName(), Toast.LENGTH_SHORT).show();
        tvName.setText(userInfo.getLevelName());
    }

    @Override
    public void setGirlInfo(List<GirlInfoBean> list) {
        if (list != null && list.size() > 0) {
            Toast.makeText(this, list.get(0).getDesc(), Toast.LENGTH_SHORT).show();
            tvName.setText(list.get(0).getDesc());
            Glide.with(this).load(list.get(0).getUrl()).into(ivGirl);
        }
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_desc)
    public void onClick() {
//        mPresenter.getUserInfo("kWg3hnwThFCThmg=");

        mPresenter.getGirlInfo(String.valueOf(pageIndex++));
    }
}
