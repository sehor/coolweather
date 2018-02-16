package com.example.coolweather.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by 彭卓荣 on 2018-02-15.
 */

public class County extends DataSupport {

    private int id;
    private int cityId;
    private int weatherId;
    private String countyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
