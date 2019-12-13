package com.example.dream.retrofitrxjavaokhttpdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.dream.retrofitrxjavaokhttpdemo.R;
import com.example.dream.retrofitrxjavaokhttpdemo.base.ui.BaseActivity;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.CityBean;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.GirlInfoBean;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.ProvinceBean;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.UserInfoBean;
import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ErrorType;
import com.example.dream.retrofitrxjavaokhttpdemo.ui.contract.TestContract;
import com.example.dream.retrofitrxjavaokhttpdemo.ui.presenter.TestPresenter;

import java.util.ArrayList;
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
    @BindView(R.id.tree)
    Button btnTree;
    @BindView(R.id.bt_slide)
    Button btSlide;

    private int pageIndex = 1;
    private GuideView guideView;
    private OptionsPickerView pvOptions;

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<CityBean>> options2Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
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
//        Glide.with(this).load(getResources().getDrawable(R.drawable.timg)).into(ivGirl);
        initOptionPicker();
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
//        guideView = GuideView.Builder
//                .newInstance(this)
//                .setTargetView(btnDesc)
//                .setOffset(0, 80)
//                .setShape(GuideView.MyShape.CIRCULAR)
//                .setRadius(150)
//                .setContain(false)
//                .setBgColor(getResources().getColor(R.color.half_transparent))
//                .setOnclickListener(new GuideView.OnClickCallback() {
//                    @Override
//                    public void onClickedGuideView() {
//                        guideView.hide();
//                    }
//                }).build();
//        guideView.show();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/test_test/MainActivity").navigation();
            }
        });

        btSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SlideImageActivity.class));
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

            int tt2 = ((int) tt.charAt(0)) % 4;

            Toast.makeText(this, tt + " " + tt.charAt(0) + " " + ((int) tt.charAt(0)) + " " + tt2, Toast.LENGTH_SHORT).show();

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

    @OnClick(R.id.test2)
    public void onHFClick(View view) {

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        getDelegate().setLocalNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        SharedPreferences s = getSharedPreferences("mypreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.putBoolean("night", currentNightMode == Configuration.UI_MODE_NIGHT_NO);
        editor.commit();
        recreate();
//        startActivity(new Intent(this,MainActivity.class));
//        finish();

    }

    @OnClick(R.id.tree)
    public void onShowTree(View view) {
        if (pvOptions != null) {
            pvOptions.show(); //弹出条件选择器
        }
    }

    private void initOptionPicker() {
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */
        // /条件选择器初始化
        //等数据加载完毕再初始化并显示Picker,以免还未加载完数据就显示,造成APP崩溃。
        getOptionData();
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getName()
                        + options2Items.get(options1).get(options2)
                        /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/;
                btnTree.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("省", "市", "区")
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

//        pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }

    private void getOptionData() {

        //选项1
        options1Items.add(new ProvinceBean(0, "广东", "描述部分", "其他数据"));
        options1Items.add(new ProvinceBean(1, "湖南", "描述部分", "其他数据"));
        options1Items.add(new ProvinceBean(2, "广西", "描述部分", "其他数据"));

        //选项2
        ArrayList<CityBean> options2Items_01 = new ArrayList<>();
        options2Items_01.add(new CityBean(0, "广州"));
        options2Items_01.add(new CityBean(0, "佛山"));
        options2Items_01.add(new CityBean(0, "东莞"));
        options2Items_01.add(new CityBean(0, "珠海"));
        ArrayList<CityBean> options2Items_02 = new ArrayList<>();
        options2Items_02.add(new CityBean(0, "长沙"));
        options2Items_02.add(new CityBean(0, "岳阳"));
        options2Items_02.add(new CityBean(0, "株洲"));
        options2Items_02.add(new CityBean(0, "衡阳"));
        ArrayList<CityBean> options2Items_03 = new ArrayList<>();
        options2Items_03.add(new CityBean(0, "桂林"));
        options2Items_03.add(new CityBean(0, "玉林"));
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);





        /*--------数据源添加完毕---------*/
    }
}
