package com.example.dream.retrofitrxjavaokhttpdemo.ui.adapter;

import com.example.dream.retrofitrxjavaokhttpdemo.bean.TagBean;

import java.util.List;

public class RecyclerViewAddItemExchange {
    private int position;
    private FlowLayoutAdapter mFlowLayoutAdapter;
    private List<TagBean> addPinDaos;

    public RecyclerViewAddItemExchange(int position,
                                       FlowLayoutAdapter flowLayoutAdapter,
                                       List<TagBean> addPinDaos) {
        this.position = position;
        this.mFlowLayoutAdapter = flowLayoutAdapter;
        this.addPinDaos = addPinDaos;
        itemExchange();
    }

    private void itemExchange() {
        TagBean tagBean = addPinDaos.get(position);
        int toposition = position;
        if (tagBean.getViewType() == 1) {
            for (int i = 2; i < addPinDaos.size(); i++) {
                if (addPinDaos.get(i).getViewType() == 3) {
                    toposition = i;
                    break;
                }
            }
            tagBean.setViewType(3);
            tagBean.setMyTag(0);
            addPinDaos.remove(position);
            mFlowLayoutAdapter.notifyItemRemoved(position);
            addPinDaos.add(toposition - 1, tagBean);
            mFlowLayoutAdapter.notifyItemInserted(toposition - 1);
        } else if (tagBean.getViewType() == 3) {
            addPinDaos.get(position).setViewType(1);
            for (int i = 2; i < addPinDaos.size(); i++) {
                if (addPinDaos.get(i).getViewType() == 2) {
                    toposition = i;
                    break;
                }
            }
            tagBean.setViewType(1);
            if (mFlowLayoutAdapter.getTag() == 1) {
                tagBean.setMyTag(1);
            }
            addPinDaos.remove(position);
            mFlowLayoutAdapter.notifyItemRemoved(position);
            addPinDaos.add(toposition, tagBean);
            mFlowLayoutAdapter.notifyItemInserted(toposition);
        }
    }
}
