package com.example.dream.retrofitrxjavaokhttpdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dream.retrofitrxjavaokhttpdemo.R;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.TagBean;
import com.example.dream.retrofitrxjavaokhttpdemo.ui.adapter.RecyclerViewAddItemOnClickListener;
import com.example.dream.retrofitrxjavaokhttpdemo.ui.adapter.FlowLayoutAdapter;
import com.example.dream.retrofitrxjavaokhttpdemo.ui.adapter.OnItemCallbackHelper;
import com.example.dream.retrofitrxjavaokhttpdemo.ui.adapter.RecyclerViewAddItemExchange;

import java.util.ArrayList;
import java.util.List;

public class SlideImageActivity extends AppCompatActivity {
    private RecyclerView mRecy;
    private float mY;
    List<TagBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_layout);
        initData();
        initView();
//        initView2();
    }

    private void initData() {
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                for (int j = 0; j < 10; j++) {
                    TagBean bean = new TagBean("选中" + j);
                    bean.setViewType(1);
                    bean.setMyTag(0);
                    list.add(bean);
                }
            }
            TagBean bean = new TagBean("wq" + i);
            bean.setMyTag(0);
            bean.setViewType(i);
            list.add(bean);
        }
        for (int i = 0; i < 10; i++) {
            TagBean bean = new TagBean("未选中" + i);
            bean.setViewType(3);
            bean.setMyTag(0);
            list.add(bean);
        }
    }

    private void initView() {
        mRecy = findViewById(R.id.rv_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getApplicationContext(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                TagBean tagBean = list.get(position);
                if (tagBean.getViewType() == 0 || tagBean.getViewType() == 2) {
                    return 4;
                } else {
                    return 1;
                }
            }
        });

        final FlowLayoutAdapter flowLayoutAdapter = new FlowLayoutAdapter(this, list);
        mRecy.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper.Callback callback = new OnItemCallbackHelper(flowLayoutAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecy);
        flowLayoutAdapter.setItemTouchHelper(itemTouchHelper);

        mRecy.setLayoutManager(gridLayoutManager);

        mRecy.addOnItemTouchListener(new RecyclerViewAddItemOnClickListener(this, mRecy,
                new RecyclerViewAddItemOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemCilck(View view, int position) {
                        Log.e("---", "点击了" + position);
                        if (flowLayoutAdapter != null && flowLayoutAdapter.getTag() == 0
                                && list.get(position).getViewType() == 1) {
                            ToastUtils.showShort("点击了" + position);
                        } else {
                            RecyclerViewAddItemExchange exchange = new RecyclerViewAddItemExchange(position,
                                    flowLayoutAdapter, list);
                        }
                    }
                }));
        mRecy.setAdapter(flowLayoutAdapter);
    }

    private void initView2() {
//        mRecy = findViewById(R.id.rv_list);
//        mRecy.setLayoutManager(new LinearLayoutManager(this));
//        final SlideImageAdapter myAdapter = new SlideImageAdapter(this);
//        mRecy.setAdapter(myAdapter);
//        mRecy.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        mY = motionEvent.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        myAdapter.move((int) (mY - motionEvent.getY()));
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        break;
//                }
//                return false;
//            }
//        });
    }

}
