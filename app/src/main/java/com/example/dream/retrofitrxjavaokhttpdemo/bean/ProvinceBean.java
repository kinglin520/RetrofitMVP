package com.example.dream.retrofitrxjavaokhttpdemo.bean;

import com.contrarywind.interfaces.IPickerViewData;

public class ProvinceBean implements IPickerViewData {
    private int id;
    private String name;
    private String desc;
    private String remark;

    public ProvinceBean(int id, String name, String desc, String remark) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
