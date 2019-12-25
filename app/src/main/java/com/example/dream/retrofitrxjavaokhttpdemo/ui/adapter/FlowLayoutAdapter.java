package com.example.dream.retrofitrxjavaokhttpdemo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.dream.retrofitrxjavaokhttpdemo.R;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.TagBean;

import java.util.Collections;
import java.util.List;

//import android.support.v7.widget.helper.ItemTouchHelper;

public class FlowLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerViewAddItemCallBackListener {

    private Context mContext;

    private List<TagBean> tagBeans;

    private ItemTouchHelper itemTouchHelper;

    private int tag = 0;

    public FlowLayoutAdapter(Context mContext, List<TagBean> tagBeans) {
        this.mContext = mContext;
        this.tagBeans = tagBeans;
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public int getTag() {
        return tag;
    }

    public void setEditorTag() {
        if (tag == 0) {
            this.tag = 1;
            for (int i = 0; i < tagBeans.size(); i++) {
                if (tagBeans.get(i).getViewType() == 1) {
                    tagBeans.get(i).setMyTag(1);
                }
            }
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_add_item_one, parent, false);
                viewHolder = new ViewHolderOne(view);
                break;
            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_add_item_my, parent, false);
                viewHolder = new ViewHolderMy(view);
                break;
            case 2:
                view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_add_item_tow, parent, false);
                viewHolder = new MyTitleViewHolder(view);
                break;
            case 3:
                view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_add_item_pindao, parent, false);
                viewHolder = new ViewHolderPinDao(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myViewHolder, final int position) {
        TagBean tagBean = tagBeans.get(position);
        switch (tagBean.getViewType()) {
            case 0:
                final ViewHolderOne viewHolderOne = (ViewHolderOne) myViewHolder;
                if (tag == 1) {
                    viewHolderOne.textView_one.setText("完 成");
                    viewHolderOne.textView_tuodong.setText("拖动可以排序");
                } else {
                    viewHolderOne.textView_one.setText("编 辑");
                    viewHolderOne.textView_tuodong.setText("");
                }
                viewHolderOne.textView_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tag == 0) {
                            viewHolderOne.textView_one.setText("完 成");
                            viewHolderOne.textView_tuodong.setText("拖动可以排序");
                            tag = 1;
                            Log.e("----t2", "" + tag);
                            for (int i = 0; i < tagBeans.size(); i++) {
                                if (tagBeans.get(i).getViewType() == 1) {
                                    tagBeans.get(i).setMyTag(1);
                                }
                            }
                            notifyDataSetChanged();
                        } else {
                            viewHolderOne.textView_one.setText("编 辑");
                            viewHolderOne.textView_tuodong.setText("");
                            tag = 0;
                            Log.e("----t1", "" + tag);
                            for (int i = 0; i < tagBeans.size(); i++) {
                                if (tagBeans.get(i).getViewType() == 1) {
                                    tagBeans.get(i).setMyTag(0);
                                }
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
                break;
            case 1:
                final ViewHolderMy viewHolderMy = ((ViewHolderMy) myViewHolder);

                TextView mContent = viewHolderMy.textView_my;

                // 我的频道 前2不能操作
                if (position == 1 || position == 2) {
                    mContent.setBackgroundResource(R.drawable.item_textview_bg);
                } else {
                    mContent.setBackgroundColor(mContent.getResources().getColor(R.color.baise));
                }

                mContent.setText(tagBean.getContent());

                ImageView imageView = viewHolderMy.imageView_my;

                if (tagBean.getMyTag() == 1 && position > 2) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                }
//                viewHolderMy.textView_my.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        if (position > 2 && MotionEventCompat.getActionMasked(motionEvent)
//                                == MotionEvent.ACTION_DOWN ) {
//                            if (itemTouchHelper != null) {
//                                itemTouchHelper.startDrag(viewHolderMy);
//                            }
//                        }
//                        return false;
//                    }
//                });
                viewHolderMy.textView_my.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        ToastUtils.showShort(position + "");
                        setEditorTag();
                        return false;
                    }
                });

                break;
            case 2:
                TextView textView2 = ((MyTitleViewHolder) myViewHolder).mTitle;
                textView2.setText("推荐频道");
                break;
            case 3:
                TextView mContent2 = ((ViewHolderPinDao) myViewHolder).textView_pin;

                mContent2.setText(tagBean.getContent());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        TagBean tagBean = tagBeans.get(position);
        switch (tagBean.getViewType()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
        }
        return 0;
    }

//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        //如果是title就占据设置的spanCount个单元格
//        final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
//        //Sets the source to get the number of spans occupied by each item in the adapter.
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                TagBean tagBean = tagBeans.get(position);
//                if (tagBean.getViewType() == 0 || tagBean.getViewType() == 2) {
//                    return 4;
//                } else {
//                    return 1;
//                }
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return tagBeans.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        TagBean addPinDao = tagBeans.get(fromPosition);
        TagBean addPinDao1 = tagBeans.get(toPosition);
        if (fromPosition != 1 && toPosition != 1) {
            if ((addPinDao.getViewType() == 1 && addPinDao1.getViewType() == 1) || (addPinDao.getViewType()
                    == 3 && addPinDao1.getViewType() == 3)) {
                //通知数组的移动
                Collections.swap(tagBeans, fromPosition, toPosition);
                notifyItemMoved(fromPosition, toPosition);
            }
        }
    }

    class ViewHolderMy extends RecyclerView.ViewHolder {
        private TextView textView_my;
        private ImageView imageView_my;

        public ViewHolderMy(View itemView) {
            super(itemView);
            imageView_my = (ImageView) itemView.findViewById(R.id.imageView_recyclerview_add_itme_my);
            textView_my = (TextView) itemView.findViewById(R.id.textview_recyelerview_add_item_my);
        }
    }

    class ViewHolderPinDao extends RecyclerView.ViewHolder {

        private TextView textView_pin;

        public ViewHolderPinDao(View itemView) {
            super(itemView);
            textView_pin = (TextView) itemView.findViewById(R.id.textview_recyelerview_add_item_pindao);
        }
    }

    class MyTitleViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;

        public MyTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textview_recyelerview_add_item_pindao);
        }
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        private TextView textView_tuodong;
        private TextView textView_one;

        public ViewHolderOne(View itemView) {
            super(itemView);
            textView_one = (TextView) itemView.findViewById(R.id.textview_recyclerview_add_item_one);
            textView_tuodong = (TextView) itemView.findViewById(R.id.textview_recyclerview_add_item_one_tuodong);
        }
    }

}
