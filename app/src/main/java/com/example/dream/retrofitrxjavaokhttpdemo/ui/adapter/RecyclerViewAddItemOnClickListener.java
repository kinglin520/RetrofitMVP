package com.example.dream.retrofitrxjavaokhttpdemo.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wenlin on 2019/12/2.
 */
public class RecyclerViewAddItemOnClickListener implements RecyclerView.OnItemTouchListener{


    public interface OnItemClickListener{
        void onItemCilck(View view, int position);
    }

    private OnItemClickListener onItemClickListener;
    private GestureDetector gestureDetector;

    public RecyclerViewAddItemOnClickListener(Context context, final RecyclerView recyclerView,
                                              OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(),e.getY());
        if (view != null && onItemClickListener != null && gestureDetector.onTouchEvent(e)
                && rv.getChildPosition(view) >= 1){
            onItemClickListener.onItemCilck(view,rv.getChildPosition(view));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    //1.08表示放大倍数,可以随便改
    private void scaleUp(TextView view) {
        ViewCompat.animate(view)
                .setDuration(200)
                .scaleX(1.08f)
                .scaleY(1.08f)
                .start();
    }

    private void scaleDown(TextView view) {
        ViewCompat.animate(view)
                .setDuration(200)
                .scaleX(1f)
                .scaleY(1f)
                .start();
    }
}
