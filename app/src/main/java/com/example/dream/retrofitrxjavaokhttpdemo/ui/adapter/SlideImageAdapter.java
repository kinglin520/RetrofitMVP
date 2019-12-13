package com.example.dream.retrofitrxjavaokhttpdemo.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dream.retrofitrxjavaokhttpdemo.R;

import io.reactivex.annotations.NonNull;

public class SlideImageAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public SlideImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 1) {
            ImageView imageView = new ImageView(mContext);
            return new ViewHolder1(imageView);
        } else {
            TextView textView = new TextView(mContext);
            return new ViewHolder2(textView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 6) return 1;
        return 2;
    }

    public void move(int y) {
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.girl);
        if (y > 0 && y < bmp.getHeight() - 400) {
            Bitmap bitmap = Bitmap.createBitmap(bmp, 0, y, bmp.getWidth(), 400);
            if (iv != null) {
                iv.setImageBitmap(bitmap);
            }
        }

    }

    ImageView iv;

    class ViewHolder1 extends RecyclerView.ViewHolder {


        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            iv = (ImageView) itemView;
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
            iv.setLayoutParams(lp);
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.girl);
            Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), 400);
            iv.setImageBitmap(bitmap);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            TextView tv = (TextView) itemView;
            tv.setText("hello world!");
            tv.setGravity(Gravity.CENTER);
            tv.setMinHeight(200);
        }
    }


}
