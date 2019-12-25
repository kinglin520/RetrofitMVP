package com.example.dream.retrofitrxjavaokhttpdemo.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

public class OnItemCallbackHelper extends ItemTouchHelper.Callback {
    private FlowLayoutAdapter flowLayoutAdapter;

    public OnItemCallbackHelper(FlowLayoutAdapter flowLayoutAdapter) {
        this.flowLayoutAdapter = flowLayoutAdapter;
    }

    /**
     * 是否删除
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /**
     * 是否拖住
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        int swipeFlag = 0;
        return makeMovementFlags(dragFlags, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        int touchPosition = viewHolder.getAdapterPosition();
        int targetPosition = target.getAdapterPosition();
        // 我的频道 前3条目不能操作
        if (touchPosition > 2 && targetPosition > 2 &&
                viewHolder instanceof FlowLayoutAdapter.ViewHolderMy) {
            flowLayoutAdapter.onMove(touchPosition, targetPosition);
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }


}
