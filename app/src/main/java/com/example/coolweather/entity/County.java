package com.example.coolweather.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by 彭卓荣 on 2018-02-15.
 */

public class County extends DataSupport {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    private int countyId;
    private int cityId;
    private String countyName;



}
