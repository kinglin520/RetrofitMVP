package com.example.dream.retrofitrxjavaokhttpdemo.bean;

public class TagBean {
    private String content;
    private int viewType = 0;
    private int myTag;

    public int getMyTag() {
        return myTag;
    }

    public void setMyTag(int myTag) {
        this.myTag = myTag;
    }

    public TagBean(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
