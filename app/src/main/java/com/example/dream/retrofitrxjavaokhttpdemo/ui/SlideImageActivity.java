package com.example.dream.retrofitrxjavaokhttpdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.example.dream.retrofitrxjavaokhttpdemo.R;
import com.example.dream.retrofitrxjavaokhttpdemo.ui.adapter.SlideImageAdapter;

public class SlideImageActivity extends AppCompatActivity {
    private RecyclerView mRecy;
    private float mY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_layout);

        mRecy = findViewById(R.id.rv_list);

        mRecy.setLayoutManager(new LinearLayoutManager(this));
        final SlideImageAdapter myAdapter = new SlideImageAdapter(this);
        mRecy.setAdapter(myAdapter);
        mRecy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        myAdapter.move((int) (mY - motionEvent.getY()));
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }
}
