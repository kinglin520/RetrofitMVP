package com.example.dream.retrofitrxjavaokhttpdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.dream.retrofitrxjavaokhttpdemo.R;
import com.example.dream.retrofitrxjavaokhttpdemo.base.ui.BaseActivity;
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
    private GuideView guideView;

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
    public TestPresenter initPresenter() {
        return new TestPresenter(this);
    }

    @Override
    protected void initView() {
        //文字图片
        final ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.mipmap.lead_arrows_2);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv1.setLayoutParams(params1);

        //我知道啦
        final ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.mipmap.lead_icon_pose);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv2.setLayoutParams(params2);
        guideView = GuideView.Builder
                .newInstance(this)
                .setTargetView(btnDesc)
                .setOffset(0,80)
                .setShape(GuideView.MyShape.CIRCULAR)
                .setRadius(150)
                .setContain(false)
                .setBgColor(getResources().getColor(R.color.half_transparent))
                .setOnclickListener(new GuideView.OnClickCallback() {
                    @Override
                    public void onClickedGuideView() {
                        guideView.hide();
                    }
                }).build();
        guideView.show();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/test_test/MainActivity").navigation();
            }
        });
    }

    @Override
    public void setUserInfo(UserInfoBean userInfo) {
        Toast.makeText(this, userInfo.getLevelName(), Toast.LENGTH_SHORT).show();
        tvName.setText(userInfo.getLevelName());
    }

    @Override
    public void setGirlInfo(List<GirlInfoBean> list) {
        if (list != null && list.size() > 0) {
            tvName.setText(list.get(0).getDesc());
            Glide.with(this).load(list.get(0).getUrl()).into(ivGirl);

            String tt = "你好";

            int tt2 = ((int)tt.charAt(0))%4;

            Toast.makeText(this, tt+" "+tt.charAt(0)+" " +((int)tt.charAt(0))+" "+tt2, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void showErrorTip(ErrorType errorType, int errorCode, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_desc)
    public void onClick() {
//        mPresenter.getUserInfo("kWg3hnwThFCThmg=");
//        guideView.show();

        mPresenter.getGirlInfo(String.valueOf(pageIndex++));
    }
}
