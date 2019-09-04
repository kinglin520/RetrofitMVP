package com.example.dream.retrofitrxjavaokhttpdemo.bean;

import com.contrarywind.interfaces.IPickerViewData;

public class CityBean implements IPickerViewData {
    private int id;
    private String name;

    public CityBean(int id, String name) {
        this.id = id;
        this.name = name;
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


    @Override
    public String getPickerViewText() {
        return name;
    }
}