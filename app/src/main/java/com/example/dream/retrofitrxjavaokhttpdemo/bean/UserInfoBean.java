package com.example.dream.retrofitrxjavaokhttpdemo.bean;

public class UserInfoBean {
    //add new user info
    private String level;           // 用户等级
    private String levelName;       // 等级名称
    private String passport;        // 用户绑定护照
    private String passportGender;  // 用户护照性别
    private String passportName;    // 用户护照名称
    private int commentNumber;      // 用户的总评论数
    private int footprintNumber;    // 用户的总足迹个数

    private String inviteName;      // 邀请人姓名
    private String inviteUserId;    // 邀请人uid
    private String asset;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPassportGender() {
        return passportGender;
    }

    public void setPassportGender(String passportGender) {
        this.passportGender = passportGender;
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public int getFootprintNumber() {
        return footprintNumber;
    }

    public void setFootprintNumber(int footprintNumber) {
        this.footprintNumber = footprintNumber;
    }

    public String getInviteName() {
        return inviteName;
    }

    public void setInviteName(String inviteName) {
        this.inviteName = inviteName;
    }

    public String getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(String inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }
}
